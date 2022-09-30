import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import order.OrderClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class TestCreateOrder extends OrderClient {
    static OrderClient orderClient;

    @Before
    public void setup() {
        orderClient = new OrderClient();
    }

    private final String json;
    private Integer track;

    public TestCreateOrder(String json) {
        this.json = json;
    }

    @Parameterized.Parameters
    public static Object[][] getJson() {
        return new Object[][]{{"[\"BLACK\"]"}, {"[\"BLACK\",\"GREY\"]"}, {"[]"}};
    }

    @DisplayName("createOrder")
    @Test
    public void createOrder() {
        Response response = create(json).statusCode(201).extract().response();
        response.then().assertThat().body("track", notNullValue());

        track = response.jsonPath().getInt("track");
    }

    @After
    public void testDown() {orderClient.deleteOrder(track);}

}

