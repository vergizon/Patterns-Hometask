package ru.netology.test;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import ru.netology.utils.DataGenerator;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static java.time.Duration.ofSeconds;

class DateOfTheMeetingPlanningTest {


    @BeforeAll
   static void setUpAll() {}

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
        $(By.cssSelector("[data-test-id = 'date'] input")).sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME));
        $(By.cssSelector("[data-test-id = 'date'] input")).sendKeys(Keys.DELETE);
    }
    @AfterEach
    void teardown() {
        closeWebDriver();
    }

    String successPlanCssSelector = "[data-test-id = 'success-notification'] .notification__content";
    String replanCssSelector = "[data-test-id = 'replan-notification'] .notification__content";
    String textOfReplanNotification = "У вас уже запланирована встреча на другую дату. Перепланировать?";

    @Test
    void shouldSuccessfulPlanAndReplanMeeting() {

        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 3;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 4;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);
        $("[data-test-id = 'city'] input").setValue(validUser.getCity());
        $("[data-test-id = 'date'] input").setValue(firstMeetingDate);
        $("[data-test-id = 'name'] input").setValue(validUser.getName());
        $("[data-test-id = 'phone'] input").setValue(validUser.getPhone());
        $("[data-test-id = 'agreement'] span").click();
        $(".button").click();
        $(By.cssSelector(successPlanCssSelector)).shouldHave(text(firstMeetingDate), ofSeconds(10));

        open("http://localhost:9999/");
        $("[data-test-id = 'city'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME));
        $("[data-test-id = 'city'] input").sendKeys(Keys.DELETE);
        $(By.cssSelector("[data-test-id = 'date'] input")).sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME));
        $(By.cssSelector("[data-test-id = 'date'] input")).sendKeys(Keys.DELETE);
        $("[data-test-id = 'name'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME));
        $("[data-test-id = 'name'] input").sendKeys(Keys.DELETE);
        $("[data-test-id = 'phone'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME));
        $("[data-test-id = 'name'] input").sendKeys(Keys.DELETE);

        $("[data-test-id = 'city'] input").setValue(validUser.getCity());
        $("[data-test-id = 'date'] input").setValue(secondMeetingDate);
        $("[data-test-id = 'name'] input").setValue(validUser.getName());
        $("[data-test-id = 'phone'] input").setValue(validUser.getPhone());
        $("[data-test-id = 'agreement'] span").click();
        $(".button").click();
        $(By.cssSelector(replanCssSelector)).shouldHave(text(textOfReplanNotification), ofSeconds(10));
        $("[data-test-id = 'replan-notification'] button").click();
        $(By.cssSelector(successPlanCssSelector)).shouldHave(text(secondMeetingDate), ofSeconds(10));

    }
}
        // TODO: добавить логику теста в рамках которого будет выполнено планирование и перепланирование встречи.
        // Для заполнения полей формы можно использовать пользователя validUser и строки с датами в переменных
        // firstMeetingDate и secondMeetingDate. Можно также вызывать методы generateCity(locale),
        // generateName(locale), generatePhone(locale) для генерации и получения в тесте соответственно города,
        // имени и номера телефона без создания пользователя в методе generateUser(String locale) в датагенераторе



