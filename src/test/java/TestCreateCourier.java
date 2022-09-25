import courier.Courier;
import courier.CourierClient;
import courier.CourierCredentials;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
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
        boolean isOk = courierClient.create(courier)
                .extract().path("ok");

    CourierCredentials creds = CourierCredentials.from(courier);
    courierId = courierClient.logIn(creds, 200)
            .extract().path("id");

        assertTrue(isOk);
        assertNotEquals(0, courierId);
        }

    @DisplayName("Создание курьера с пустым паролем")
    @Test
    public void createEmptyPassword() {
        courier = Courier.getWithoutPassword();

        String errorMessage = courierClient.createFailed(courier)
                .extract().path("message");

        assertEquals(errorMessage, "Недостаточно данных для создания учетной записи");
        }

    @DisplayName("Повторное создание курьера")
    @Test
    public void checkDoubleCreateCourier() {
        boolean isOk = courierClient.create(courier)
                .extract().path("ok");
        CourierCredentials creds = CourierCredentials.from(courier);
        String reCreation = courierClient.reCreation(creds)
                .extract().path("message");

        assertTrue(isOk);
        assertEquals(reCreation, "Этот логин уже используется. Попробуйте другой.");

    }
}
