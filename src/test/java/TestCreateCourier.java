import courier.Courier;
import courier.CourierClient;
import courier.CourierCredentials;
import io.qameta.allure.junit4.DisplayName;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestCreateCourier {

    Courier courier;
    static CourierClient courierClient;
    private static int courierId;

    @Before
    public void setup() {
        courier = Courier.getRandomCourier();
        courierClient = new CourierClient();
    }

    @AfterClass
    public static void teardown() {
        courierClient.delete(courierId);
    }

    @DisplayName("Регистрация курьера и логин на сайте")
    @Test
    public void registerCourier() {
        assertTrue(courierClient.create(courier).statusCode(201).extract().path("ok"));
        CourierCredentials creds = CourierCredentials.from(courier);
        courierId = courierClient.logIn(creds, 200).extract().path("id");

        assertNotEquals(0, courierId);
    }

    @DisplayName("Создание курьера с пустым паролем")
    @Test
    public void createEmptyPassword() {
        courier = Courier.getWithoutPassword();

        assertEquals (courierClient.createFailed(courier).statusCode(400).extract().path("message"), "Недостаточно данных для создания учетной записи");
    }

    @DisplayName("Повторное создание курьера")
    @Test
    public void checkDoubleCreateCourier() {
        CourierCredentials creds = CourierCredentials.from(courier);

        assertTrue(courierClient.create(courier).extract().path("ok"));
        assertEquals( courierClient.reCreation(creds).statusCode(409).extract().path("message"), "Этот логин уже используется. Попробуйте другой.");

    }
}
