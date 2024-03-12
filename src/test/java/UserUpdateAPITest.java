import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;

public class UserUpdateAPITest extends BaseTest {
    private String accessToken; // переменная для хранения токена
    private User testUser; // переменная для хранения юзера

    @Before
    public void setUp() {
        super.setUp();
        // Создаем нового пользователя для теста
        testUser = new User(existingUserName, existingUserEmail, existingUserPassword);
        userClient.createUser(testUser);
        // Получение токена авторизации
        accessToken = userClient.loginUserAndGetToken(testUser);
    }

    @Test
    @DisplayName("Обновление имени пользователя с авторизацией")
    @Description("Тест проверяет возможность обновления имени пользователя с использованием токена авторизации")
    public void shouldUpdateUserNameWithAuth() {
        // Создание объекта пользователя с обновленным именем
        String uniqueSuffix = Long.toString(System.currentTimeMillis());
        User updatedUser = new User("UpdatedName" + uniqueSuffix, testUser.getEmail(), testUser.getPassword());
        // Обновление пользователя с токеном авторизации
        userClient.updateUserWithAuth(accessToken, updatedUser);
    }

    @Test
    @DisplayName("Обновление пароля пользователя с авторизацией")
    @Description("Тест проверяет возможность обновления пароля пользователя с использованием токена авторизации")
    public void shouldUpdateUserPasswordWithAuth() {
        // Создание объекта пользователя с обновленным паролем
        String newUniquePassword = "new" + System.currentTimeMillis();
        User updatedUser = new User(testUser.getName(), testUser.getEmail(), newUniquePassword);
        // Обновление пользователя с токеном авторизации
        userClient.updateUserWithAuth(accessToken, updatedUser);
    }

    @Test
    @DisplayName("Попытка обновления данных пользователя без авторизации")
    @Description("Тест проверяет, что попытка обновления данных пользователя без токена авторизации возвращает ошибку")
    public void shouldNotUpdateUserWithoutAuth() {
        // Создание объекта пользователя с новыми данными для обновления
        String newUniqueName = "NoAuthUpdate" + System.currentTimeMillis();
        User updatedUser = new User(newUniqueName, testUser.getEmail(), testUser.getPassword());
        // Попытка обновления пользователя без токена авторизации
        userClient.updateUserWithoutAuth(updatedUser);
    }
}
