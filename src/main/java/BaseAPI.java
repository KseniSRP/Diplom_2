import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class BaseAPI {

    @Step("Отправка HTTP запроса")
    protected Response sendRequest(String method, String endpoint, Object body, String accessToken, int expectedStatusCode) {
        RequestSpecification request = RestAssured.given().contentType("application/json");

        if (body != null) {
            request.body(body);
        }

        if (accessToken != null && !accessToken.isEmpty()) {
            request.header("Authorization", accessToken);
        }

        Response response = request.request(method, endpoint);
        response.then().statusCode(expectedStatusCode);

        return response;
    }
}
