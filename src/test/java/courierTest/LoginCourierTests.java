package courierTest;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.couriers.*;

public class LoginCourierTests {
    private final CourierRandom courierRandom = new CourierRandom();
    CourierRegistration courierRegistration;
    int courierID;
    private CourierAuthorization courierAuthorization;
    private CourierActions courierActions;
    private CreatingCourier creatingCourier;

    @Before
    @Step("Создание тестовых данных для логина курьера")
    public void setUp() {
        courierActions = new CourierActions();
        creatingCourier = courierRandom.createNewRandomCourier();
        courierActions.createCourier(creatingCourier);
        courierAuthorization = CourierAuthorization.from(creatingCourier);
        courierRegistration = new CourierRegistration();
    }

    @Test
    @DisplayName("Логин курьера успешен")
    @Description("Проверяем, что курьер может войти в систему с валидными данными")
    public void courierLoginOkValidData() {
        ValidatableResponse responseLoginCourier = courierActions.loginCourier(courierAuthorization);
        courierRegistration.loginCourierOk(responseLoginCourier);
        courierID = responseLoginCourier.extract().path("id");
    }

    @Test
    @DisplayName("Логин курьера с пустым полем логина")
    @Description("Проверяем, что курьер не может войти в систему с пустым полем логина")
    public void courierLoginErrorEmptyLogin() {
        CourierAuthorization courierAuthorizationWithoutLogin = new CourierAuthorization("", creatingCourier.getPassword()); // c null тесты виснут
        ValidatableResponse responseLoginErrorMessage = courierActions.loginCourier(courierAuthorizationWithoutLogin);
        courierRegistration.loginCourierError(responseLoginErrorMessage);

    }

    @Test
    @DisplayName("Логин курьера с пустым полем пароля")
    @Description("Проверяем, что курьер не может войти в систему с пустым полем пароля")
    public void courierLoginErrorEmptyPassword() {
        CourierAuthorization courierAuthorizationWithoutPass = new CourierAuthorization(creatingCourier.getLogin(), "");
        ValidatableResponse responseLoginErrorMessage = courierActions.loginCourier(courierAuthorizationWithoutPass);
        courierRegistration.loginCourierError(responseLoginErrorMessage);
    }

    @Test
    @DisplayName("Логин курьера с пустым полями логина и пароля")
    @Description("Проверяем, что курьер не может войти в систему с пустыми полями логина и пароля")
    public void courierLoginErrorEmptyLoginAndPassword() {
        CourierAuthorization courierAuthorizationWithoutLoginAndPassword = new CourierAuthorization("", "");
        ValidatableResponse responseLoginErrorMessage = courierActions.loginCourier(courierAuthorizationWithoutLoginAndPassword);
        courierRegistration.loginCourierError(responseLoginErrorMessage);
    }

    @Test
    @DisplayName("Логин курьера c невалидным логином")
    @Description("Проверяем, что курьер не может войти в систему с ранее не зарегистрированным логином")
    public void courierLoginErrorAccountNotFound() {
        CourierAuthorization courierAuthorizationErrorAccountNotFound = new CourierAuthorization(CourierRandom.NEW_LOGIN_FAKED, creatingCourier.getPassword());
        ValidatableResponse responseLoginErrorMessage = courierActions.loginCourier(courierAuthorizationErrorAccountNotFound);
        courierRegistration.loginCourierErrorAccountNotFound(responseLoginErrorMessage);
    }

    @Test
    @DisplayName("Логин курьера с неверным паролем, но верным логином")
    @Description("Проверяем, что курьер не может войти в систему с верным логином, но неверным паролем")
    public void courierLoginErrorIncorrectPassword() {
        CourierAuthorization courierAuthorizationIncorrectPassword = new CourierAuthorization(
                creatingCourier.getLogin(),
                "incorrect_password"
        );
        ValidatableResponse responseLoginErrorMessage = courierActions.loginCourier(courierAuthorizationIncorrectPassword);
        courierRegistration.loginCourierErrorIncorrectPassword(responseLoginErrorMessage);
    }

    @After
    @Step("Удаление курьера")
    public void deleteCourier() {
        if (courierID != 0) {
            courierActions.deleteCourier(courierID);
        }
    }
}