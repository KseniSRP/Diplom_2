import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;

public class BaseTest {
    protected UserClient userClient;
    protected String existingUserEmail;
    protected String existingUserPassword = "qwerty";
    protected String existingUserName = "UserName";
    protected String accessToken;

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
        userClient = new UserClient();
        existingUserEmail = "test" + System.currentTimeMillis() + "@yandex.ru";
    }

    @After
    public void tearDown() {
        // Удаление тестового пользователя после каждого теста, если токен доступа получен
        if (accessToken != null && !accessToken.isEmpty()) {
            userClient.deleteUser(accessToken);
        }
    }
}
