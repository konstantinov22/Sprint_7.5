package courierTest;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.couriers.*;

public class CreateCourierTest {
    protected final CourierRandom courierRandom = new CourierRandom();
    int courierId;
    private CourierActions courierActions;
    private CreatingCourier creatingCourier;
    private CourierRegistration courierRegistration;

    @Before
    @Step("Созданем тестовые данные курьера")
    public void setUp() {
        courierActions = new CourierActions();
        creatingCourier = courierRandom.createNewRandomCourier();
        courierRegistration = new CourierRegistration();
    }

    @After
    @Step("Удаление тестовых данных")
    public void deleteCourier() {
        if (courierId != 0) {
            courierActions.deleteCourier(courierId);
        }
    }

    @Test
    @DisplayName("Создание нового курьера")
    @Description("Проверяем, что курьера можно создать")
    public void courierCanBeCreated() {
        ValidatableResponse responseCreateCourier = courierActions.createCourier(creatingCourier);
        CourierAuthorization courierAuthorization = CourierAuthorization.from(creatingCourier);
        courierId = courierActions.loginCourier(courierAuthorization).extract().path("id");
        courierRegistration.createCourierOk(responseCreateCourier);
    }

    @Test
    @DisplayName("Создание курьера с пустым полем логина")
    @Description("Проверяем, что курьера нельзя создать без логина")
    public void courierCanNotBeCreatedWithoutLogin() {
        creatingCourier.setLogin(null);
        ValidatableResponse responseNullLogin = courierActions.createCourier(creatingCourier);
        courierRegistration.createCourierError(responseNullLogin);
    }

    @Test
    @DisplayName("Создание курьера с пустым полем пароля")
    @Description("Проверяем, что курьера нельзя создать без пароля")
    public void courierCanNotBeCreatedWithoutPassword() {
        creatingCourier.setPassword(null);
        ValidatableResponse responseNullPassword = courierActions.createCourier(creatingCourier);
        courierRegistration.createCourierError(responseNullPassword);
    }

    @Test
    @DisplayName("Создание курьера с пустым полем логина и пароля")
    @Description("Проверяем, что курьера нельзя создать без ввода логина и  пароля")
    public void courierCanNotBeCreatedWithoutLoginAndPassword() {
        creatingCourier.setLogin(null);
        creatingCourier.setPassword(null);
        ValidatableResponse responseNullFields = courierActions.createCourier(creatingCourier);
        courierRegistration.createCourierError(responseNullFields);
    }

    @Test
    @DisplayName("Создание курьера с ранее зарегистрированным логином")
    @Description("Проверяем, что курьера нельзя создать с ранее зарегистрированным логином")
    public void courierCanNotBeCreatedWithSameLogin() {
        courierActions.createCourier(creatingCourier);
        ValidatableResponse responseCreateCourier = courierActions.createCourier(creatingCourier);
        courierRegistration.createCourierSameLoginError(responseCreateCourier);
    }
}