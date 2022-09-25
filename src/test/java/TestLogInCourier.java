import courier.Courier;
import courier.CourierClient;
import courier.CourierCredentials;
import io.qameta.allure.junit4.DisplayName;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestLogInCourier {

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

    @DisplayName("Успешный логин курьра в системе")
    @Test
    public void logInCourier() {
        boolean isOk = courierClient.create(courier)
                .extract().path("ok");

        CourierCredentials creds = CourierCredentials.from(courier);
        courierId = courierClient.logIn(creds, 200)
                .extract().path("id");

        assertTrue(isOk);
        assertNotEquals(0, courierId);
    }

    @DisplayName("Логин курьера без обязательного параметра (без пароля)")
    @Test
    public void logInCourierWithoutPassword() {
        CourierCredentials creds = CourierCredentials.emptyPassword(courier);
        String logInWithoutPassword = courierClient.logIn(creds, 400)
                .extract().path("message");

        assertEquals(logInWithoutPassword, "Недостаточно данных для входа");
    }

    @DisplayName("Логин курьера с несуществующими кредами")
    @Test
    public void logInCourierNotFound() {
        CourierCredentials creds = CourierCredentials.credsNotFound(courier);
        String logInWithoutPassword = courierClient.logIn(creds, 404)
                .extract().path("message");

        assertEquals(logInWithoutPassword, "Учетная запись не найдена");

    }
}
