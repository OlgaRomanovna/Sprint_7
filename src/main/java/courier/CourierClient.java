package courier;

import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

public class CourierClient extends BaseClient {

    private final String ROOT = "/courier";
    private final String COURIER = "/courier/{courierId}";
    private final String LOGIN = ROOT + "/logIn";

    public ValidatableResponse create(Courier courier) {
        return getSpec()
                .body(courier)
                .when()
                .post(ROOT)
                .then().log().all();
    }

    public ValidatableResponse createFailed(Courier courier) {
        return getSpec()
                .body(courier)
                .when()
                .post(ROOT)
                .then().log().all();
    }

    public ValidatableResponse logIn(CourierCredentials creds, int statusCode) {
        return getSpec()
                .body(creds)
                .when()
                .post(LOGIN)
                .then().log().all()
                .statusCode(statusCode);
    }

    public ValidatableResponse reCreation(CourierCredentials creds) {
        return getSpec()
                .body(creds)
                .when()
                .post(ROOT)
                .then().log().all();
    }

    public void delete(int courierId) {
        getSpec()
                .pathParam("courierId", courierId)
                .when()
                .delete(COURIER)
                .then().log().all()
                .statusCode(200);
    }
}
