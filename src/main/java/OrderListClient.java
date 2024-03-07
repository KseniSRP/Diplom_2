import io.qameta.allure.Step;
import io.restassured.RestAssured;

import org.hamcrest.CoreMatchers;

    public class OrderListClient {

        @Step("Получение списка заказов пользователя с авторизацией")
        public void getUserOrdersWithAuth(String accessToken) {
            RestAssured.given()
                    .header("Authorization", accessToken)
                    .header("Content-Type", "application/json")
                    .when()
                    .get("/api/orders")
                    .then()
                    .statusCode(200) // Проверяем, что статус код ответа 200
                    .body("success", CoreMatchers.equalTo(true))
                    .body("total", CoreMatchers.notNullValue()) // Проверяем, что поле total присутствует и не null
                    .body("orders", CoreMatchers.notNullValue()); // Аналогично проверяем наличие списка заказов
        }

        @Step("Попытка получения списка заказов пользователя без авторизации")
        public void getUserOrdersWithoutAuth() {
            RestAssured.given()
                    .header("Content-Type", "application/json")
                    .when()
                    .get("/api/orders")
                    .then()
                    .statusCode(401) // Проверяем, что возвращается статус 401 для неавторизованного запроса
                    .body("success", CoreMatchers.equalTo(false))
                    .body("message", CoreMatchers.equalTo("You should be authorised"));
        }
    }


