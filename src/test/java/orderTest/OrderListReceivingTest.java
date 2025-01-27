package orderTest;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import ru.yandex.orders.CreateOrder;

import static org.hamcrest.CoreMatchers.notNullValue;

public class OrderListReceivingTest {
    @Test
    @DisplayName("Получение списка заказов")
    @Description("Проверяем, что список заказов успешно получен")
    public void getOrderList() {
        CreateOrder createOrder = new CreateOrder();
        ValidatableResponse responseOrderList = createOrder.getOrderList();
        responseOrderList.assertThat()
                .statusCode(200)
                .body("ru/yandex/orders", notNullValue());
    }
}
