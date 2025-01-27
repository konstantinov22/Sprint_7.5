package orderTest;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex.orders.CreateOrder;
import ru.yandex.orders.OrderDetails;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class OrderParamTest {
    private final List<String> colour;
    int track;
    private CreateOrder createOrder;

    public OrderParamTest(List<String> colour) {
        this.colour = colour;
    }

    @Parameterized.Parameters(name = "Цвет самоката: {0}")
    public static Object[][] getScooterColour() {
        return new Object[][]{
                {List.of("GRAY")},
                {List.of("GRAY, BLACK")},
                {List.of("GRAY")},
                {List.of()}
        };
    }

    @Before
    public void setUp() {
        createOrder = new CreateOrder();
    }

    @After
    @Step("Cancel test order")
    public void CancelTestOrder() {
        createOrder.cancelOrder(track);
    }

    @Test
    @DisplayName("Размещение заказа с самокатами разных цветов")
    @Description("Проверяем корректность размещения заказа с самокатами разных цветов")
    public void OrderingWithScootersInDifferentColors() {
        OrderDetails orderDetails = new OrderDetails(colour);
        ValidatableResponse responseCreateOrder = createOrder.createNewOrder(orderDetails);
        track = responseCreateOrder.extract().path("track");
        responseCreateOrder.assertThat()
                .statusCode(201)
                .body("track", is(notNullValue()));
    }
}