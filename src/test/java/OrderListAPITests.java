import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
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
    @DisplayName("Проверка получения списка заказов с авторизацией")
    @Description("Проверяем что юзер может получить список своих заказов с авторизацикй")
    public void testGetUserOrdersWithAuth() {
        orderListClient.getUserOrdersWithAuth(accessToken);
    }

    @Test
    @DisplayName("Получение списка заказов без авторизации")
    @Description("Проверяем что юзер не может получить список своих заказов без авторизации")
    public void testGetUserOrdersWithoutAuth() {
        orderListClient.getUserOrdersWithoutAuth();
    }
}
