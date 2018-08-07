package zhanj;

import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/{id}")
    public User getUserById(@PathVariable int id) {
        return userService.findUser(id);
    }

    @PostMapping("/")
    public User addUser(User user) {
        userService.add(user);
        return user;
    }

    @PutMapping("/{id}")
    public String updateUser(@PathVariable int id, User user) {
        user.setId((long) id);
        userService.update(user);
        return "更新成功";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable int id) {
        userService.delete(id);
        return "删除成功";
    }
}
