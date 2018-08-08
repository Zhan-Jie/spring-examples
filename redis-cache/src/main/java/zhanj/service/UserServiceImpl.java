package zhanj.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import zhanj.entity.User;
import zhanj.repo.UserRepository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private UserRepository userRepository;

    private ValueOperations<String, Object> ops;

    private String getUserKey(int id) {
        return "redis-cache:user:id:" + id;
    }

    @PostConstruct
    public void init() {
        ops = redisTemplate.opsForValue();
    }

    @Override
    public User findById(int id) {
        log.info("call findById( id = {} )", id);
        Object obj = ops.get(getUserKey(id));
        User user;
        if (obj == null) {
            log.info(" [ X ] cache miss.");
            user = userRepository.findById(id).get();
            ops.set(getUserKey(id), user, 2, TimeUnit.MINUTES);
        } else {
            log.info(" -> cache hit.");
            user = (User) obj;
        }
        return user;
    }

    @Override
    public List<User> findAll() {
        Iterable<User> result = userRepository.findAll();
        List<User> users = new ArrayList<>();
        result.forEach(users::add);
        return users;
    }

    @Override
    public int add(User user) {
        // update database
        user = userRepository.save(user);
        // update cache
        ops.set(getUserKey(user.getId()), user, 2, TimeUnit.MINUTES);
        return user.getId();
    }

    @Override
    public void removeById(int id) {
        // update database
        userRepository.deleteById(id);
        // update cache
        redisTemplate.delete(getUserKey(id));
    }

    @Override
    public User update(int id, User user) {
        user.setId(id);
        // update database
        user = userRepository.save(user);
        // update cache
        ops.set(getUserKey(id), user, 2, TimeUnit.MINUTES);
        return user;
    }
}
