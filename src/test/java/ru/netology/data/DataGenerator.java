package ru.netology.data;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.Value;

import java.util.Locale;

import static io.restassured.RestAssured.given;


public class DataGenerator {
    private DataGenerator() {

    }

    private static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    private static Faker faker = new Faker(new Locale("en"));

    private static void sendRequest(RegistrationUser user) {
        given()
                .spec(requestSpec)
                .body(user)
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }

    public static String generateLogin() {
        String login = new Faker().name().username();
        return login;
    }

    public static String generatePassword() {
        String password = new Faker().internet().password();
        return password;
    }

    public static RegistrationUser getUser(String status) {     //создаём пользователя
        RegistrationUser user = new RegistrationUser(generateLogin(), generatePassword(), status);
        return user;
    }

    public static RegistrationUser getRegUser(String status) {   //регестрируем пользователя
        RegistrationUser user = getUser(status);
        sendRequest(user);
        return user;
    }

    @Value
    public static class RegistrationUser {
        String login;
        String password;
        String status;
    }


}
