import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import order.OrderClient;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;

public class TestListOrder extends OrderClient {
    static OrderClient orderClient;

    public void setup() {
        orderClient = new OrderClient();
    }

    @DisplayName("Получение списка заказов")
    @Test
    public void checkListOrder() {
        Response response = listOrders().statusCode(200).extract().response();
        response.then().assertThat().body("orders", notNullValue());
    }
}


