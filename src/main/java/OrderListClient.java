import io.qameta.allure.Step;
import io.restassured.RestAssured;

import org.hamcrest.CoreMatchers;

    public class OrderListClient extends BaseAPI  {
        private static final String ORDERS_ENDPOINT = "/api/orders";


        @Step("Получение списка заказов пользователя с авторизацией")
        public void getUserOrdersWithAuth(String accessToken) {
            sendRequest("GET", ORDERS_ENDPOINT, null, accessToken, 200).then()
                    .body("success", CoreMatchers.equalTo(true))
                    .body("total", CoreMatchers.notNullValue())
                    .body("orders", CoreMatchers.notNullValue());
        }

        @Step("Попытка получения списка заказов пользователя без авторизации")
        public void getUserOrdersWithoutAuth() {
            sendRequest("GET", ORDERS_ENDPOINT, null, null, 401).then()
                    .body("success", CoreMatchers.equalTo(false))
                    .body("message", CoreMatchers.equalTo("You should be authorised"));
        }
    }


