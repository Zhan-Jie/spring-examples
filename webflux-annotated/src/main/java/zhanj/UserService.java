package zhanj;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserService {
    private final Map<String, User> data = new ConcurrentHashMap<>();

    public Flux<User> list() {
        return Flux.fromIterable(data.values());
    }

    public Flux<User> findById(final Flux<String> ids) {
        return ids.flatMap(id -> Mono.justOrEmpty(data.get(id)));
    }

    public Mono<User> findById(final String id) {
        return Mono.justOrEmpty(data.get(id)).switchIfEmpty(Mono.error(new ResourceNotFoundException()));
    }

    public Mono<User> createOrUpdate(final User user) {
        System.out.println(user);
        data.put(user.getId(), user);
        return Mono.just(user);
    }

    public Mono<User> delete(final String id) {
        return Mono.justOrEmpty(data.remove(id));
    }
}
