package ru.yandex.couriers;

import com.github.javafaker.Faker;
import io.qameta.allure.Step;


public class CourierRandom {
    static Faker faker = new Faker();

    public static final String NEW_LOGIN_FAKED = faker.name().username();

    @Step("Создание нового курьера с рандомными данными")
    public CreatingCourier createNewRandomCourier() {
        return new CreatingCourier(
                faker.name().username(),
                faker.internet().password(),
                faker.name().firstName());
    }
}