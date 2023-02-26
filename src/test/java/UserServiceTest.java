import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.verification.NoInteractions;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.glebova.mockito.user.User;
import ru.glebova.mockito.user.UserNonUniqueException;
import ru.glebova.mockito.user.UserRepository;
import ru.glebova.mockito.user.UserService;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

public class UserServiceTest {
    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    User unicorn;


    @BeforeEach
    public void setUp() {
        unicorn = new User("Unicorn", "123");
    }

    @Test
    void getAllLogins() {
        when(userRepository.getAllUsers()).thenReturn(List.of(new User("Unicorn", "123")));
        userRepository.addUser(unicorn);
        Assertions.assertEquals(List.of("Unicorn"), userService.getAllLogins());
    }

    @Test
    void ifLoginIsNullThenServiceThrowsException() {
        assertThatThrownBy(() -> userService.createNewUser("", "123"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Поля Логин и пароль должны быть заполнены");
        verify(userRepository, new NoInteractions()).getAllUsers();
        verify(userRepository, new NoInteractions()).addUser(any());
    }

    @Test
    void whenCorrectUserIsAddedThenAddUserIsCalledFromRepo() {
        when(userRepository.getAllUsers()).thenReturn(List.of());
        userService.createNewUser("Unicorn", "123");
        verify(userRepository)
                .addUser(any());
    }

    @Test
    void whenPasswordIsNullThenServiceThrowsException() {
        assertThatThrownBy(() -> userService.createNewUser("Unicorn", ""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Поля Логин и пароль должны быть заполнены");
        verify(userRepository, new NoInteractions()).getAllUsers();
        verify(userRepository, new NoInteractions()).addUser(any());
    }

    @Test
    void whenExistingUserIsPassedThenServiceThrowsException() {
        when(userRepository.getAllUsers()).thenReturn(List.of(new User("test", "1")));
        assertThatThrownBy(() -> userService.createNewUser("test", "1"))
                .isInstanceOf(UserNonUniqueException.class)
                .hasMessage("Пользователь уже существует");
    }

    @Test
    void whenLoginAndPasswordMatchAutentificationIsPassed() {
        when(userRepository.getAllUsers()).thenReturn(List.of(unicorn));
        userService.userAutentificationByLoginAndPassword("Unicorn", "123");
        Assertions.assertTrue(userService.userAutentificationByLoginAndPassword("Unicorn", "123"));
    }
}
