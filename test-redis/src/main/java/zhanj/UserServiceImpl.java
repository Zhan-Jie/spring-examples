package zhanj;

import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public User getUserById(int id) {
        User u = new User("zhanjie", 24);
        u.setId(id);
        return u;
    }
}
