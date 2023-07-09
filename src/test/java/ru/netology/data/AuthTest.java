package ru.netology.data;

import com.codeborne.selenide.selector.ByTagAndText;
import org.junit.jupiter.api.Test;


import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.data.DataGenerator.Registration.getRegisteredUser;
import static ru.netology.data.DataGenerator.Registration.getUser;
import static ru.netology.data.DataGenerator.getRandomLogin;
import static ru.netology.data.DataGenerator.getRandomPassword;

class AuthTest {

    @Test
    void shouldSuccessfulLoginToAccount() {
        open("http://localhost:9999/");
        var registeredUser = getRegisteredUser("active");
        $("[data-test-id=login] input").setValue(registeredUser.getLogin());
        $("[data-test-id=password] input").setValue(registeredUser.getPassword());
        $("[data-test-id=action-login]").click();
        $(new ByTagAndText("h2", "Личный кабинет")).shouldBe(visible);
    }

    @Test
    void wrongLogin() {
        open("http://localhost:9999/");
        var user = getUser("active");
        String login = getRandomLogin();
        $("[data-test-id=login] input").setValue(login);
        $("[data-test-id=password] input").setValue(user.getPassword());
        $("[data-test-id=action-login]").click();
        $("[data-test-id=error-notification]")
                .shouldHave(text("Неверно указан логин или пароль"))
                .shouldBe(visible);
    }

    @Test
    void wrongPassword() {
        open("http://localhost:9999/");
        var user = getUser("active");
        String password = getRandomPassword();
        $("[data-test-id=login] input").setValue(user.getLogin());
        $("[data-test-id=password] input").setValue(password);
        $("[data-test-id=action-login]").click();
        $("[data-test-id=error-notification]")
                .shouldHave(text("Неверно указан логин или пароль"))
                .shouldBe(visible);
    }

    @Test
    void userBlocked() {
        open("http://localhost:9999/");
        var registeredUser = getRegisteredUser("blocked");
        $("[data-test-id=login] input").setValue(registeredUser.getLogin());
        $("[data-test-id=password] input").setValue(registeredUser.getPassword());
        $("[data-test-id=action-login]").click();
        $("[data-test-id=error-notification]")
                .shouldHave(text("Пользователь заблокирован"))
                .shouldBe(visible);
    }

    @Test
    void notRegisteredUser() {
        open("http://localhost:9999/");
        var user = getUser("active");
        $("[data-test-id=login] input").setValue(user.getLogin());
        $("[data-test-id=password] input").setValue(user.getPassword());
        $("[data-test-id=action-login]").click();
        $("[data-test-id=error-notification]")
                .shouldHave(text("Неверно указан логин или пароль"))
                .shouldBe(visible);
    }
}
