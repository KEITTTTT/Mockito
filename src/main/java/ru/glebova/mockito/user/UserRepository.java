package ru.glebova.mockito.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserRepository {
    private List<User> users = new ArrayList<User>();

    public Collection<User> getAllUsers() {
        return users;
    }

    public User findUserByLogin(String login) {
        return users.stream().filter(user -> user.getLogin().equals(login)).findFirst().orElse(null);
    }
    public User findUserByLoginAndPassword(String login, String password) {
        return users.stream().filter(user -> user.getLogin().equals(login) &&
                user.getPassword().equals(password)).findFirst().orElse(null);
    }
    public void addUser(User user) {
        users.add(user);
    }
}
