package service.userService;

import model.User;

import java.util.List;

public interface IUser {
    List<User> findAll();
    User findById(int id);
    boolean create(User user);
    boolean update(User user);
    List<User> findAllExceptId(int id);
}
