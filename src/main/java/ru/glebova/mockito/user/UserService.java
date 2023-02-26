package ru.glebova.mockito.user;

import java.util.List;
import java.util.stream.Collectors;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public List<String> getAllLogins() {
        List<String> logins = userRepository.getAllUsers().stream().map(User::getLogin).collect(Collectors.toList());
        return logins;
    }
    public void createNewUser(String login, String password) {
        User user = new User(login, password);
        if (login == null || login.isBlank() || password == null || password.isBlank()) {
            throw new IllegalArgumentException("Поля Логин и пароль должны быть заполнены");
        }
        boolean userExists = this.userRepository
                .getAllUsers()
                .stream()
                .anyMatch(u -> u.equals(user));
        if (userExists) {
            throw new UserNonUniqueException("Пользователь уже существует");
        }
        this.userRepository.addUser(user);
    }
    public boolean userAutentificationByLoginAndPassword(String login, String password) {
        if (userRepository.getAllUsers().stream().anyMatch(user -> user.getLogin().equals(login) &&
                user.getPassword().equals(password))) {
            return true;
        }
        return false;
    }
}
