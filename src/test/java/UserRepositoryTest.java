import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.glebova.mockito.user.User;
import ru.glebova.mockito.user.UserRepository;

import java.util.Collections;
import java.util.List;

public class UserRepositoryTest {
    UserRepository userRepository = new UserRepository();
    User unicorn;
    User ilon;
    User kate;

    @BeforeEach
    public void setUp() {
        unicorn = new User("Unicorn", "123");
        ilon = new User("Ilon", "123");
        kate = new User("Kate", "456");
    }
    @Test
    public void getEmptyUsersList() {
        Assertions.assertEquals(userRepository.getAllUsers(), Collections.EMPTY_LIST);
    }
    @Test
    public void getNotEmptyUsersList() {
        userRepository.addUser(unicorn);
        Assertions.assertEquals(List.of(unicorn), userRepository.getAllUsers());
    }
    @Test
    public void findUserByLoginIfSuchUserExists() {
        userRepository.addUser(unicorn);
        Assertions.assertEquals(userRepository.findUserByLogin("Unicorn"), unicorn);
    }
    @Test
    public void findUserByLoginIfSuchUserDoesNotExist() {
        userRepository.addUser(unicorn);
        Assertions.assertNull(userRepository.findUserByLogin("horse"));
    }
    @Test
    public void findUserByLoginAndPasswordIfSuchUserExists() {
        userRepository.addUser(unicorn);
        Assertions.assertEquals(userRepository.findUserByLoginAndPassword("Unicorn", "123"), unicorn);
    }
    @Test
    public void findUserByLoginAndPasswordIfLoginMatchesWithOneExistingAndPasswordNotMatches() {
        userRepository.addUser(unicorn);
        userRepository.addUser(kate);
        Assertions.assertNotEquals(userRepository.findUserByLoginAndPassword("Unicorn", "123"), kate);
    }
    @Test
    public void findUserByLoginAndPasswordIfPasswordMatchesWithOneExistingAndLoginNotMatches() {
        userRepository.addUser(unicorn);
        userRepository.addUser(ilon);
        Assertions.assertNotEquals(userRepository.findUserByLoginAndPassword("Unicorn", "123"), ilon);
    }
}
