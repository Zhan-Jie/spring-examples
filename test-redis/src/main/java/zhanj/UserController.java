package zhanj;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @Cacheable(value = "testCache")
    @GetMapping("/{id}")
    public String getUserById(@PathVariable Integer id) {
        Gson gson = new Gson();
        return gson.toJson(userService.getUserById(id));
    }
}
