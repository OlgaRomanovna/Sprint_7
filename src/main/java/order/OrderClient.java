package order;

import courier.BaseClient;
import io.restassured.response.ValidatableResponse;


public class OrderClient extends BaseClient {
    private final String ORDER = "orders";
    private final String CANCEL = "orders/cancel";
    private final String LISTORDERS = "orders";

    public ValidatableResponse create(String json) {
        return getSpec().body("{\"firstName\": \"Naruto\",\"lastName\": \"Uchiha\",\"address\": \"Konoha, 142\",\"metroStation\": 4,\"phone\": \"+7 800 355 35 35\",\"rentTime\": 5,\"deliveryDate\": \"2020-06-06\",\"comment\": \"Saske, come back to Konoha\",\"color\":" + json + "}")
                .when()
                .post(ORDER)
                .then().log().all();
    }

    public void deleteOrder(int track) {
        getSpec()
                .and()
                .put(CANCEL + "?track=" + track)
                .then().log().all()
                .statusCode(200);
    }

    public ValidatableResponse listOrders() {
        return getSpec()
                .and()
                .get(LISTORDERS)
                .then().log().all();
    }
}

