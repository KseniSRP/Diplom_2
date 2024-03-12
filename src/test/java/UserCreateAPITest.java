import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;

public class UserCreateAPITest extends BaseTest {

    @Test
    @DisplayName("Успешное создание пользователя")
    @Description("Проверка возможности успешного создания учетной записи пользователя")
    public void testCreateUserSuccess() {
        User newUser = new User(existingUserName, existingUserEmail, existingUserPassword);
        userClient.createUser(newUser);
        // Получение токена для вновь созданного пользователя для возможности его удаления в tearDown
        accessToken = userClient.loginUserAndGetToken(newUser);
    }

    @Test
    @DisplayName("Создание пользователя, который уже зарегистрирован")
    @Description("Попытка создать пользователя с уже существующим email")
    public void testCreateExistingUser() {
        User newUser = new User(existingUserName, existingUserEmail, existingUserPassword);
        userClient.createUser(newUser);
        // Получение токена для первого пользователя, чтобы удостовериться в его удалении после теста
        accessToken = userClient.loginUserAndGetToken(newUser);
        userClient.createUserWithExistingEmail(newUser);
    }

    @Test
    @DisplayName("Создание пользователя без обязательного поля")
    @Description("Попытка создать пользователя без указания email")
    public void testCreateUserWithoutEmail() {
        User newUserWithoutEmail = new User(existingUserName, null, existingUserPassword);
        userClient.createUserWithMissingField(newUserWithoutEmail);
    }
}