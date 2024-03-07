import org.junit.Before;
import org.junit.Test;

public class OrderListAPITests extends BaseTest {

    private OrderListClient orderListClient;
    private String accessToken;

    @Before
    public void setUp() {
        super.setUp(); // Вызов метода setUp базового класса
        orderListClient = new OrderListClient();

        // Создаём пользователя и получаем токен для авторизации
        User testUser = new User(existingUserName, existingUserEmail, existingUserPassword);
        userClient.createUser(testUser); // Создание пользователя, если необходимо
        accessToken = userClient.loginUserAndGetToken(testUser); // Получение токена
    }

    @Test
    public void testGetUserOrdersWithAuth() {
        // Тест на получение списка заказов с авторизацией
        orderListClient.getUserOrdersWithAuth(accessToken);
    }

    @Test
    public void testGetUserOrdersWithoutAuth() {
        // Тест на попытку получения списка заказов без авторизации
        orderListClient.getUserOrdersWithoutAuth();
    }
}
