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
public class TestCreateOrder extends OrderClient{
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
            return new Object[][] {{ "{\"firstName\": \"Naruto\",\"lastName\": \"Uchiha\",\"address\": \"Konoha, 142\",\"metroStation\": 4,\"phone\": \"+7 800 355 35 35\",\"rentTime\": 5,\"deliveryDate\": \"2020-06-06\",\"comment\": \"Saske, come back to Konoha\",\"color\": [\"BLACK\"]}"},
            { "{\"firstName\": \"Naruto\",\"lastName\": \"Uchiha\",\"address\": \"Konoha, 142\",\"metroStation\": 4,\"phone\": \"+7 800 355 35 35\",\"rentTime\": 5,\"deliveryDate\": \"2020-06-06\",\"comment\": \"Saske, come back to Konoha\",\"color\": [\"BLACK\",\"GREY\"]}"},
            { "{\"firstName\": \"Naruto\",\"lastName\": \"Uchiha\",\"address\": \"Konoha, 142\",\"metroStation\": 4,\"phone\": \"+7 800 355 35 35\",\"rentTime\": 5,\"deliveryDate\": \"2020-06-06\",\"comment\": \"Saske, come back to Konoha\"}"}
        };
        }

        @DisplayName("createOrder")
        @Test
        public void createOrder() {
            Response response = create(json)
                    .extract().response();
            response.then().assertThat().body("track", notNullValue());

            track =  getTrackOrder(response);

        }

        @After
        public void testDown() {
            orderClient.deleteOrder(track);
        }

    }

