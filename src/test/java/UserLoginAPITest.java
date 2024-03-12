import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;

public class UserLoginAPITest extends BaseTest {

    @Test
    @DisplayName("Логин существующего пользователя")
    @Description("Проверка возможности успешного входа в систему для существующего пользователя")
    public void testLoginExistingUser() {
        User testUser = new User(existingUserName, existingUserEmail, existingUserPassword);
        userClient.createUser(testUser); // Создаем юзера
        userClient.loginUser(testUser); // Логинимся в созданного юзера
    }

    @Test
    @DisplayName("Логин с неверными учетными данными")
    @Description("Проверка попытки логина с невалидным паролем")
    public void testLoginWithInvalidCredentials() {
        User newUser = new User(existingUserName, existingUserEmail, existingUserPassword);
        userClient.createUser(newUser); // Создаем пользователя
        // Попытка логина с измененным паролем
        User userWithInvalidCredentials = new User(existingUserName, existingUserEmail, existingUserPassword + "1");
        userClient.loginWithInvalidCredentials(userWithInvalidCredentials);
    }

    @Test
    @DisplayName("Логин несуществующего пользователя")
    @Description("Проверка попытки логина с данными пользователя, который не был создан")
    public void testLoginNonExistingUser() {
        User nonExistingUser = new User("NonExistingUser", "nonexisting" + System.currentTimeMillis() + "@example.com", "password123");
        // Попытка логина без предварительного создания пользователя
        userClient.loginWithInvalidCredentials(nonExistingUser);
    }
}
