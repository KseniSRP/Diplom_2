import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.hamcrest.CoreMatchers;


public class UserClient extends BaseAPI  {

    private static final String REGISTER_ENDPOINT = "/api/auth/register";
    private static final String LOGIN_ENDPOINT = "/api/auth/login";
    private static final String USER_ENDPOINT = "/api/auth/user";


    @Step("Создание пользователя")
    public User createUser(User user) {
        sendRequest("POST", REGISTER_ENDPOINT, user, null, 200).then()
                .body("success", CoreMatchers.equalTo(true));
        return user;
    }

    @Step("Попытка создания пользователя с существующим email")
    public void createUserWithExistingEmail(User user) {
        sendRequest("POST", REGISTER_ENDPOINT, user, null, 403).then()
                .body("success", CoreMatchers.equalTo(false))
                .body("message", CoreMatchers.equalTo("User already exists"));
    }

    @Step("Попытка создания пользователя без одного из обязательных полей")
    public void createUserWithMissingField(User user) {
        sendRequest("POST", REGISTER_ENDPOINT, user, null, 403).then()
                .body("success", CoreMatchers.equalTo(false))
                .body("message", CoreMatchers.equalTo("Email, password and name are required fields"));
    }

    @Step("Логин пользователя")
    public void loginUser(User user) {
        sendRequest("POST", LOGIN_ENDPOINT, user, null, 200).then()
                .body("success", CoreMatchers.equalTo(true));
    }

    @Step("Попытка логина пользователя с неверными учетными данными")
    public void loginWithInvalidCredentials(User user) {
        sendRequest("POST", LOGIN_ENDPOINT, user, null, 401).then()
                .body("success", CoreMatchers.equalTo(false))
                .body("message", CoreMatchers.equalTo("email or password are incorrect"));
    }

    @Step("Получение данных о пользователе с авторизацией")
    public void getUserWithAuth(String accessToken) {
        sendRequest("GET", USER_ENDPOINT, null, accessToken, 200).then()
                .body("success", CoreMatchers.equalTo(true));
    }


    @Step("Обновление данных пользователя с авторизацией")
    public void updateUserWithAuth(String accessToken, User user) {
        sendRequest("PATCH", USER_ENDPOINT, user, accessToken, 200).then()
                .body("success", CoreMatchers.equalTo(true));
    }


    @Step("Попытка обновления данных пользователя без авторизации")
    public void updateUserWithoutAuth(User user) {
        sendRequest("PATCH", USER_ENDPOINT, user, null, 401).then()
                .body("success", CoreMatchers.equalTo(false))
                .body("message", CoreMatchers.equalTo("You should be authorised"));
    }

    @Step("Попытка обновления данных пользователя с использованием занятого email")
    public void updateUserWithExistingEmail(String accessToken, User user) {
        sendRequest("PATCH", USER_ENDPOINT, user, accessToken, 403).then()
                .body("success", CoreMatchers.equalTo(false))
                .body("message", CoreMatchers.equalTo("User with such email already exists"));
    }

    @Step("Логин пользователя и получение токена")
    public String loginUserAndGetToken(User user) {
        Response response = sendRequest("POST", LOGIN_ENDPOINT, user, null, 200);
        response.then().body("success", CoreMatchers.equalTo(true));
        return response.path("accessToken");
    }

    @Step("Удаление пользователя")
    public void deleteUser(String accessToken) {
        sendRequest("DELETE", USER_ENDPOINT, null, accessToken, 202).then()
                .body("success", CoreMatchers.equalTo(true));
    }
}