import org.junit.Before;
import org.junit.Test;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import java.util.*;

public class OrderCreationAPITests extends BaseTest {
    // Объявляем клиента для работы с заказами
    private OrderClient orderClient;
    // Объявляем клиента для работы с пользователями
    private UserClient userClient;
    // Переменная для хранения токена доступа пользователя
    private String accessToken;
    // Список для хранения идентификаторов всех ингредиентов
    private List<String> allIngredientIds;

    @Before
    public void setUp() {
        // инициализирую окружение
        super.setUp();
        orderClient = new OrderClient();
        userClient = new UserClient();
        // уникальный суффикс для имени и email пользователя
        String uniqueSuffix = Long.toString(System.currentTimeMillis());
        // создаю нового пользователя с уникальными именем и email
        User testUser = new User("testUser" + uniqueSuffix, "testUser" + uniqueSuffix + "@yandex.com", "password");
        // регистрируем юзера
        userClient.createUser(testUser);
        // ивлекаем токен зарегистрированного юзера
        accessToken = userClient.loginUserAndGetToken(testUser);

        // получаю список всех ингридиентов
        List<Map<String, Object>> allIngredients = orderClient.getAllIngredients();
        // список для хранения ингридиентов
        allIngredientIds = new ArrayList<>();
        // проходим по списку и извлекаем id ингридиентов
        for (Map<String, Object> ingredient : allIngredients) {
            allIngredientIds.add(ingredient.get("_id").toString());
        }
    }

    @Test
    @DisplayName("Создание заказа с авторизацией")
    @Description("Тест проверяет создание заказа с авторизацией и всеми доступными ингредиентами")
    public void testCreateOrderWithAuth() {
        // заказ с авторизацией и списком всех доступных ингридиентов
        orderClient.createOrderWithAuth(accessToken, allIngredientIds);
    }

    @Test
    @DisplayName("Создание заказа без авторизации")
    @Description("Тест проверяет создание заказа без авторизации и всеми доступными ингредиентами")
    public void testCreateOrderWithoutAuth() {
        // заказ без авторизаци и списком всех доступных ингридиентов
        orderClient.createOrderWithoutAuth(allIngredientIds);
    }

    @Test
    @DisplayName("Создание заказа без ингредиентов и с авторизацией")
    @Description("Тест проверяет отклонение создания заказа без указания ингредиентов и с авторизацией")
    public void testCreateOrderWithAuthNoIngredients() {
        // заказ с авторизацией но без ингридиентов
        orderClient.createOrderWithAuthNoIngredients(accessToken);
    }

    @Test
    @DisplayName("Создание заказа без ингредиентов и без авторизации")
    @Description("Тест проверяет отклонение создания заказа без указания ингредиентов и без авторизации")
    public void testCreateOrderWithoutAuthNoIngredients() {
        // заказ без авторизации и без ингридиентов
        orderClient.createOrderWithoutAuthNoIngredients();
    }

    @Test
    @DisplayName("Создание заказа с неверным хешем ингредиентов и с авторизацией")
    @Description("Тест проверяет отклонение создания заказа с неверным хешем ингредиентов и с авторизацией")
    public void testCreateOrderWithInvalidIngredients() {
        // создание списка с неверным идентификатором ингредиента
        List<String> invalidIngredients = Arrays.asList("notAValidIngredientId");
        // создание заказ с авторизацией и неверным идентификатором ингредиента
        orderClient.createOrderWithInvalidIngredients(accessToken, invalidIngredients);
    }
}