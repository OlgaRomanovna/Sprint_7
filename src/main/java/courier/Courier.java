package courier;

import com.github.javafaker.Faker;
import lombok.Data;

@Data
public class Courier {
    private String email;
    private String login;
    private String password;
    private String firstName;

    public Courier(String email, String login, String password, String firstName) {
        this.email = email;
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }
    static Faker faker = new Faker();
    public static Courier getRandomCourier() {
        return new Courier(
                faker.internet().safeEmailAddress(),
                faker.name().username(),
                faker.internet().password(),
                faker.name().firstName()
        );
    }

    public static Courier getWithoutPassword() {
        return new Courier(
                faker.internet().safeEmailAddress(),
                faker.name().username(),
                "",
                faker.name().firstName()
        );
    }

}