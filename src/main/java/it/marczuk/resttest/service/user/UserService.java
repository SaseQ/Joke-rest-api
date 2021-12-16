package it.marczuk.resttest.service.user;

import it.marczuk.resttest.model.user.User;

import java.util.List;

public interface UserService {

    User saveUser(User user);

    void addRoleToUser(String email, String roleName);

    User getUser(String email);

    List<User> getUsers();
}
