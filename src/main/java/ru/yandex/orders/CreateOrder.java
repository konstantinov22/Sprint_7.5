package ru.yandex.orders;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;
import static ru.yandex.constant.constantForScooter.BASE_URL;

public class CreateOrder {

    public static RequestSpecification requestSpec() {
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URL);
    }

    @Step("Создание заказа")
    public ValidatableResponse createNewOrder(OrderDetails orderDetails) {
        return requestSpec()
                .body(orderDetails)
                .when()
                .post("/api/v1/orders")
                .then();
    }

    @Step("Получение списка заказов")
    public ValidatableResponse getOrderList() {
        return requestSpec()
                .when()
                .get("/v1/orders")
                .then();
    }

    @Step("Отмена заказа")
    public ValidatableResponse cancelOrder(int track) {
        return requestSpec()
                .body(track)
                .when()
                .put("/api/v1/orders/cancel")
                .then();
    }
}