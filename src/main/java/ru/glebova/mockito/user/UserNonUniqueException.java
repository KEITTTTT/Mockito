package ru.glebova.mockito.user;

public class UserNonUniqueException extends RuntimeException{
    public UserNonUniqueException(String message) {
        super(message);
    }
}
