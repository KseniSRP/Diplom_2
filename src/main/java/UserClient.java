import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.CoreMatchers;

public class UserClient {

    @Step("Создание пользователя")
    public User createUser(User user) {
        RestAssured.given()
                .header("Content-Type", "application/json")
                .body(user)
                .when()
                .post("/api/auth/register")
                .then()
                .statusCode(200)
                .body("success", CoreMatchers.equalTo(true));
        return user;
    }

    @Step("Попытка создания пользователя с существующим email")
    public void createUserWithExistingEmail(User user) {
        RestAssured.given()
                .header("Content-Type", "application/json")
                .body(user)
                .when()
                .post("/api/auth/register")
                .then()
                .statusCode(403)
                .body("success", CoreMatchers.equalTo(false))
                .body("message", CoreMatchers.equalTo("User already exists"));
    }

    @Step("Попытка создания пользователя без одного из обязательных полей")
    public void createUserWithMissingField(User user) {
        RestAssured.given()
                .header("Content-Type", "application/json")
                .body(user)
                .when()
                .post("/api/auth/register")
                .then()
                .statusCode(403)
                .body("success", CoreMatchers.equalTo(false))
                .body("message", CoreMatchers.equalTo("Email, password and name are required fields"));
    }

    @Step("Логин пользователя")
    public void loginUser(User user) {
        RestAssured.given()
                .header("Content-Type", "application/json")
                .body(user)
                .when()
                .post("/api/auth/login")
                .then()
                .statusCode(200)
                .body("success", CoreMatchers.equalTo(true));
    }

    @Step("Попытка логина пользователя с неверными учетными данными")
    public void loginWithInvalidCredentials(User user) {
        RestAssured.given()
                .header("Content-Type", "application/json")
                .body(user)
                .when()
                .post("/api/auth/login")
                .then()
                .statusCode(401)
                .body("success", CoreMatchers.equalTo(false))
                .body("message", CoreMatchers.equalTo("email or password are incorrect"));
    }

    @Step("Получение данных о пользователе с авторизацией")
    public void getUserWithAuth(String accessToken) {
        RestAssured.given()
                .header("Authorization", accessToken)
                .when()
                .get("/api/auth/user")
                .then()
                .statusCode(200)
                .body("success", CoreMatchers.equalTo(true));
    }

    @Step("Обновление данных пользователя с авторизацией")
    public void updateUserWithAuth(String accessToken, User user) {
        RestAssured.given()
                .header("Authorization", accessToken)
                .contentType("application/json")
                .body(user)
                .when()
                .patch("/api/auth/user")
                .then()
                .statusCode(200)
                .body("success", CoreMatchers.equalTo(true));
    }

    @Step("Попытка обновления данных пользователя без авторизации")
    public void updateUserWithoutAuth(User user) {
        RestAssured.given()
                .contentType("application/json")
                .body(user)
                .when()
                .patch("/api/auth/user")
                .then()
                .statusCode(401) // Unauthorized
                .body("success", CoreMatchers.equalTo(false))
                .body("message", CoreMatchers.equalTo("You should be authorised"));
    }

    @Step("Попытка обновления данных пользователя с использованием занятого email")
    public void updateUserWithExistingEmail(String accessToken, User user) {
        RestAssured.given()
                .header("Authorization", accessToken)
                .contentType("application/json")
                .body(user)
                .when()
                .patch("/api/auth/user")
                .then()
                .statusCode(403) // Forbidden
                .body("success", CoreMatchers.equalTo(false))
                .body("message", CoreMatchers.equalTo("User with such email already exists"));
    }

    @Step("Логин пользователя и получение токена")
    public String loginUserAndGetToken(User user) {
        Response response = RestAssured.given()
                .header("Content-Type", "application/json")
                .body(user)
                .when()
                .post("/api/auth/login")
                .then()
                .statusCode(200)
                .body("success", CoreMatchers.equalTo(true))
                .extract().response(); // для извлечения токена из ответа

        // извлекаем токена из ответа
        String accessToken = response.path("accessToken");
        return accessToken;

    }
    @Step("Удаление пользователя")
    public void deleteUser(String accessToken) {
        RestAssured.given()
                .header("Authorization", accessToken)
                .when()
                .delete("/api/auth/user")
                .then()
                .statusCode(202)
                .body("success", CoreMatchers.equalTo(true));
    }
}
