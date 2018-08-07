package zhanj;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    UserRepository userRepository;

    @CachePut(value = "user", key = "#root.caches[0].name+':'+#user.id")
    public User add(User user) {
        return userRepository.save(user);
    }

    @CachePut(value = "user", key = "#root.caches[0].name+':'+#user.id")
    public User update(User user) {
        return userRepository.save(user);
    }

    @CacheEvict(value = "user")
    public int delete(long id) {
        userRepository.deleteById(id);
        return 1;
    }

    @Cacheable(value = "user", unless = "#result==null")
    public User findUser(long id) {
        log.info("call findUser(id = {})", id);
        Optional<User> result = userRepository.findById(id);
        return result.orElse(null);
    }

    public List<User> findUsers() {
        Iterable<User> users = userRepository.findAll();
        List<User> result = new ArrayList<>();
        users.forEach(result::add);
        return result;
    }
}
