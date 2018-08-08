package zhanj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import zhanj.entity.User;
import zhanj.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/find")
    public User find(int id) {
        return userService.findById(id);
    }

    @GetMapping("/all")
    public List<User> all() {
        return userService.findAll();
    }

    @PostMapping("/add")
    public int add(User user) {
        return userService.add(user);
    }

    @DeleteMapping("/delete")
    public String delete(int id) {
        userService.removeById(id);
        return "ok";
    }

    @PostMapping("/update")
    public User update(int id, User user) {
        return userService.update(id, user);
    }
}
