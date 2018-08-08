package zhanj.service;

import zhanj.entity.User;

import java.util.List;

public interface UserService {
    User findById(int id);
    List<User> findAll();
    int add(User user);
    void removeById(int id);
    User update(int id, User user);
}
