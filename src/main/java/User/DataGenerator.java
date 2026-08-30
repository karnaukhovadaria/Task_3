package User;
import com.github.javafaker.Faker;

public class DataGenerator {

    static Faker faker = new Faker();
    public static User randomUser() {
        final String email = faker.internet().emailAddress();
        final String password = faker.internet().password(6,10);
        final String name = faker.name().firstName();
        return new User(email,password,name);
    }

    public static User userWithShortPassword() {
        final String email = faker.internet().emailAddress();
        final String password = faker.internet().password(1,5);
        final String name = faker.name().firstName();
        return new User(email,password,name);
    }

}
