package zhanj;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class MainController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping(path = "/add")
    public String addUser(@RequestParam String name, @RequestParam String email) {
        User u;
        u = userRepository.findFirstByNameOrEmail(name, email);
        if (u != null) {
            if (name.equals(u.getName())) {
                return String.format("user with the name '%s' already exists", name);
            } else {
                return String.format("user with the email '%s' already exists", email);
            }
        }
        u = new User();
        u.setName(name);
        u.setEmail(email);
        u = userRepository.save(u);
        return "generated user id: " + u.getId();
    }

    @GetMapping(path = "/all")
    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }
}
