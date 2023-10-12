package ru.netology.test;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.data.DataGenerator.*;


public class AuthTest {

    @BeforeEach
    public void setUpAll() {
        open("http://localhost:9999/");
    }

    @Test
    public void shouldLoginRegisteredUser() {
        DataGenerator.RegistrationUser regUser = getRegUser("active");

        $("[data-test-id='login'] input").setValue(regUser.getLogin());
        $("[data-test-id='password'] input").setValue(regUser.getPassword());
        $("button.button").click();

        $("h2").shouldHave(Condition.exactText("  Личный кабинет")).shouldBe(Condition.visible, Duration.ofSeconds(15));

    }

    @Test
    public void shouldNotLoginNotRegisteredUser() {
        DataGenerator.RegistrationUser notRegUser = getUser("active");

        $("[data-test-id='login'] input").setValue(notRegUser.getLogin());
        $("[data-test-id='password'] input").setValue(notRegUser.getPassword());
        $("button.button").click();

        $("[data-test-id='error-notification'] .notification__content")
                .shouldHave(Condition.exactText("Ошибка! " + "Неверно указан логин или пароль"))
                .shouldBe(Condition.visible, Duration.ofSeconds(15));

    }

    @Test
    public void shouldNotLoginBlockedUser() {
        DataGenerator.RegistrationUser regUser = getRegUser("blocked");

        $("[data-test-id='login'] input").setValue(regUser.getLogin());
        $("[data-test-id='password'] input").setValue(regUser.getPassword());
        $("button.button").click();

        $("[data-test-id='error-notification'] .notification__content")
                .shouldHave(Condition.exactText("Ошибка! " + "Пользователь заблокирован"))
                .shouldBe(Condition.visible, Duration.ofSeconds(15));

    }

    @Test
    public void shouldNotLoginRegisteredUserWithIncorrectLogin() {
        DataGenerator.RegistrationUser regUser = getRegUser("active");
        String wrongLogin = generateLogin();

        $("[data-test-id='login'] input").setValue(wrongLogin);
        $("[data-test-id='password'] input").setValue(regUser.getPassword());
        $("button.button").click();

        $("[data-test-id='error-notification'] .notification__content")
                .shouldHave(Condition.exactText("Ошибка! " + "Неверно указан логин или пароль"))
                .shouldBe(Condition.visible, Duration.ofSeconds(15));

    }

    @Test
    public void shouldNotLoginRegisteredUserWithIncorrectPassword() {
        DataGenerator.RegistrationUser regUser = getRegUser("active");
        String wrongPassword = generatePassword();

        $("[data-test-id='login'] input").setValue(regUser.getLogin());
        $("[data-test-id='password'] input").setValue(wrongPassword);
        $("button.button").click();

        $("[data-test-id='error-notification'] .notification__content")
                .shouldHave(Condition.exactText("Ошибка! " + "Неверно указан логин или пароль"))
                .shouldBe(Condition.visible, Duration.ofSeconds(15));

    }

}
