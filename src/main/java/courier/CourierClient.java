package courier;
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
                .then().log().all()
                .statusCode(201);
    }

    public ValidatableResponse createFailed(Courier courier) {
        return getSpec()
                .body(courier)
                .when()
                .post(ROOT)
                .then().log().all()
                .statusCode(400);
    }

    public ValidatableResponse logIn(CourierCredentials creds, int statusCode) {
        return getSpec()
                .body(creds)
                .when()
                .post(LOGIN)
                .then().log().all()
                .assertThat()
                .statusCode(statusCode);
    }

    public ValidatableResponse reCreation(CourierCredentials creds) {
        return getSpec()
                .body(creds)
                .when()
                .post(ROOT)
                .then().log().all()
                .assertThat()
                .statusCode(409);
    }

    public void delete(int courierId) {
        getSpec()
                .pathParam("courierId", courierId)
                .when()
                .delete(COURIER)
                .then().log().all()
                .assertThat()
                .statusCode(200);
    }
}
