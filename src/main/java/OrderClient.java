import io.qameta.allure.Step;
import io.restassured.RestAssured;

import io.restassured.response.Response;
import org.hamcrest.CoreMatchers;
import java.util.*;

public class OrderClient {

    @Step("Получение списка ингредиентов")
    public List<Map<String, Object>> getAllIngredients() {
        Response response = RestAssured.get("/api/ingredients");
        return response.jsonPath().getList("data");
    }

    @Step("Создание заказа с авторизацией")
    public void createOrderWithAuth(String accessToken, List<String> ingredients) {
        RestAssured.given()
                .header("Authorization", accessToken)
                .header("Content-Type", "application/json")
                .body(new Order(ingredients))
                .when()
                .post("/api/orders")
                .then()
                .statusCode(200)
                .body("success", CoreMatchers.equalTo(true));
    }

    @Step("Создание заказа без авторизации")
    public void createOrderWithoutAuth(List<String> ingredients) {
        RestAssured.given()
                .header("Content-Type", "application/json")
                .body(new Order(ingredients))
                .when()
                .post("/api/orders")
                .then()
                .statusCode(200)
                .body("success", CoreMatchers.equalTo(true));
    }

    @Step("Попытка создания заказа без ингредиентов с авторизацией")
    public void createOrderWithAuthNoIngredients(String accessToken) {
        RestAssured.given()
                .header("Authorization", accessToken)
                .header("Content-Type", "application/json")
                .body(new Order(List.of()))
                .when()
                .post("/api/orders")
                .then()
                .statusCode(400)
                .body("success", CoreMatchers.equalTo(false));
    }

    @Step("Попытка создания заказа без ингредиентов без авторизации")
    public void createOrderWithoutAuthNoIngredients() {
        RestAssured.given()
                .header("Content-Type", "application/json")
                .body(new Order(List.of()))
                .when()
                .post("/api/orders")
                .then()
                .statusCode(400) // В зависимости от спецификации API
                .body("success", CoreMatchers.equalTo(false));
    }

    @Step("Попытка создания заказа с невалидным хешем ингредиентов")
    public void createOrderWithInvalidIngredients(String accessToken, List<String> invalidIngredients) {
        RestAssured.given()
                .header("Authorization", accessToken)
                .header("Content-Type", "application/json")
                .body(new Order(invalidIngredients))
                .when()
                .post("/api/orders")
                .then()
                .statusCode(500);
    }

}
