package zhanj;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "requested resource is not found")
    @ExceptionHandler(ResourceNotFoundException.class)
    public void notFound() {
    }

    @GetMapping("/")
    public Flux<User> list() {
        return userService.list();
    }

    @GetMapping("/{id}")
    public Mono<User> findById(@PathVariable String id) {
        return userService.findById(id);
    }

    @PostMapping(value = "/")
    public Mono<User> create(@RequestBody User user) {
        return userService.createOrUpdate(user);
    }

    @PutMapping(value = "/{id}")
    public Mono<User> update(@PathVariable String id, @RequestBody User user) {
        Objects.requireNonNull(user).setId(id);
        return userService.createOrUpdate(user);
    }

    @DeleteMapping(value = "/{id}")
    public Mono<User> delete(@PathVariable String id) {
        return userService.delete(id);
    }
}
