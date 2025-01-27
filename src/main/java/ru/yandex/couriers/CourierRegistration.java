package ru.yandex.couriers;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;

public class CourierRegistration {
    @Step("Регитрация курьера с валидными данными")
    public void createCourierOk(ValidatableResponse response) {
        response.assertThat()
                .statusCode(201)
                .body("ok", is(true));
    }

    @Step("Проверка если одного из полей нет, запрос возвращает ошибку;")
    public void createCourierError(ValidatableResponse response) {
        response.assertThat()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Step("Проверка, что если создать пользователя с логином, который уже есть, возвращается ошибка")
    public void createCourierSameLoginError(ValidatableResponse response) {
        response.assertThat()
                .statusCode(409)
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    @Step("Проверка, что при вводе валидных  данных запрос возвращает id курьера")
    public int loginCourierOk(ValidatableResponse response) {
        return response.assertThat()
                .statusCode(200)
                .body("id", greaterThan(0))
                .extract()
                .path("id");
    }

    @Step("Проверка ответа сервера при запросе без логина или пароля")
    public void loginCourierError(ValidatableResponse response) {
        response.assertThat()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Step("Проверка ответа сервера при запросе с несуществующими логином")
    public void loginCourierErrorAccountNotFound(ValidatableResponse response) {
        response.assertThat()
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Step("Проверка ответа сервера при запросе с несуществующими паролем")
    public void loginCourierErrorIncorrectPassword(ValidatableResponse response) {
        response.assertThat()
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));
    }
}