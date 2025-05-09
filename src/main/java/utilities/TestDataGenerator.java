package utilities;

import net.datafaker.Faker;

public class TestDataGenerator {

    private static final Faker faker = new Faker();

    public static String getRandomUsername() {
        return faker.internet().username();
    }

    public static String getRandomPassword() {
        return faker.internet().password(8, 16, true, true);
    }

    public static String getRandomEmail() {
        return faker.internet().emailAddress();
    }

    public static String getRandomPhoneNumber() {
        return faker.phoneNumber().cellPhone();
    }

    public static String getRandomFullName() {
        return faker.name().fullName();
    }
}
