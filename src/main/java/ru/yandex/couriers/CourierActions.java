package ru.yandex.couriers;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;
import static ru.yandex.constant.constantForScooter.BASE_URL;

public class CourierActions {

    public static RequestSpecification requestSpec() {
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URL);
    }

    @Step("Регистрация нового курьера")
    public ValidatableResponse createCourier(CreatingCourier creatingCourier) {
        return requestSpec()
                .body(creatingCourier)
                .when()
                .post("/api/v1/courier")
                .then();
    }

    @Step("Авторизация курьера")
    public ValidatableResponse loginCourier(CourierAuthorization courierАuthorization) {
        return requestSpec()
                .body(courierАuthorization)
                .when()
                .post("/api/v1/courier/login")
                .then();
    }

    @Step("Удаление курьера")
    public ValidatableResponse deleteCourier(int courierId) {
        return requestSpec()
                .when()
                .delete("/api/v1/courier/:id" + courierId)
                .then();
    }
}