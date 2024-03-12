import io.qameta.allure.Step;
import io.restassured.RestAssured;

import io.restassured.response.Response;
import org.hamcrest.CoreMatchers;
import java.util.*;

public class OrderClient extends BaseAPI {

    private static final String INGREDIENTS_ENDPOINT = "/api/ingredients";
    private static final String ORDERS_ENDPOINT = "/api/orders";

    @Step("Получение списка ингредиентов")
    public List<Map<String, Object>> getAllIngredients() {
        Response response = sendRequest("GET", INGREDIENTS_ENDPOINT, null, null, 200);
        return response.jsonPath().getList("data");
    }

    @Step("Создание заказа с авторизацией")
    public void createOrderWithAuth(String accessToken, List<String> ingredients) {
        sendRequest("POST", ORDERS_ENDPOINT, new Order(ingredients), accessToken, 200).then()
                .body("success", CoreMatchers.equalTo(true));
    }

    @Step("Создание заказа без авторизации")
    public void createOrderWithoutAuth(List<String> ingredients) {
        sendRequest("POST", ORDERS_ENDPOINT, new Order(ingredients), null, 200).then()
                .body("success", CoreMatchers.equalTo(true));
    }

    @Step("Попытка создания заказа без ингредиентов с авторизацией")
    public void createOrderWithAuthNoIngredients(String accessToken) {
        sendRequest("POST", ORDERS_ENDPOINT, new Order(List.of()), accessToken, 400).then()
                .body("success", CoreMatchers.equalTo(false));
    }

    @Step("Попытка создания заказа без ингредиентов без авторизации")
    public void createOrderWithoutAuthNoIngredients() {
        sendRequest("POST", ORDERS_ENDPOINT, new Order(List.of()), null, 400).then()
                .body("success", CoreMatchers.equalTo(false));
    }

    @Step("Попытка создания заказа с невалидным хешем ингредиентов")
    public void createOrderWithInvalidIngredients(String accessToken, List<String> invalidIngredients) {
        sendRequest("POST", ORDERS_ENDPOINT, new Order(invalidIngredients), accessToken, 500);
    }

}
