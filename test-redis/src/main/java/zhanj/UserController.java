package zhanj;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping(value = "/")
    public List<User> getUsers() {
        return userService.findUsers();
    }

    @Cacheable(value = "user")
    @GetMapping("/{id}")
    public User getUserById(@PathVariable int id) {
        return userService.findUser(id);
    }

    @CachePut(value = "user", key = "#root.caches[0].name+':'+#user.id")
    @PostMapping("/")
    public long addUser(User user) {
        userService.add(user);
        //返回增加后的id
        return user.getId();
    }

    @CachePut(value = "user", key = "#root.caches[0].name+':'+#user.id")
    @PutMapping("/{id}")
    public String updateUser(@PathVariable int id, User user) {
        user.setId((long) id);
        int result = userService.update(user);
        return result == 1 ? "更新成功" : "更新失败";
    }

    @CacheEvict(value = "user")
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable int id) {
        int result = userService.delete(id);
        return result == 1 ? "删除成功" : "删除失败";
    }
}
