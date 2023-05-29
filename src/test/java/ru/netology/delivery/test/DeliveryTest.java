package ru.netology.delivery.test;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import data.DataGenerator;
import ru.netology.data.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

class DeliveryTest {

    @BeforeEach
    void setup() {
        open("http://localhost:7777");
    }

    @Test
    @DisplayName("Should successful plan and replan meeting")
    void shouldSuccessfulPlanAndReplanMeeting() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);
        class DeliveryCardTest {

            @BeforeEach
            void setup() {
                open("http://localhost:7777");
            }

            @Test
            @DisplayName("Should successful plan and changePlan meeting")
            void shouldSuccessfulPlanAndChangePlanMeeting() {
                DataGenerator DataGenerator;
                var validUser = DataGenerator.Registration.generateUser("ru");
                var daysToAddForFirstMeeting = 4;
                var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
                var daysToAddForSecondMeeting = 7;
                var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);

                // первичная дата

                $("[data-test-id=city] input").setValue(validUser.getCity());
                $(".calendar-input input").doubleClick().sendKeys(Keys.BACK_SPACE);
                $(".calendar-input input").setValue(firstMeetingDate);
                $("[data-test-id=name] input").setValue(validUser.getName());
                $("[data-test-id=phone] input").setValue(validUser.getPhone());
                $("[data-test-id='agreement']").click();
                $(".button").click();
                $(withText("Успешно!")).shouldBe(Condition.visible, Duration.ofSeconds(30));
                $("[data-test-id=success-notification] .notification__content")
                        .shouldHave(Condition.text("Встреча успешно запланирована на " + firstMeetingDate));

                //перепланирование

                $(".calendar-input input").doubleClick().sendKeys(Keys.BACK_SPACE);
                $(".calendar-input input").setValue(secondMeetingDate);
                $(".button").click();
                $(withText("Успешно!")).shouldBe(Condition.visible, Duration.ofSeconds(30));
                $("[data-test-id= changePlan-notification]")
                        .shouldHave(Condition.text("У вас уже запланирована встреча на другую дату. Перепланировать?"));
                $(withText("Перепланировать")).click();
                $("[data-test-id=success-notification]").shouldBe(Condition.visible, Duration.ofSeconds(30));
                $("[data-test-id=success-notification] .notification__content")
                        .shouldHave(Condition.text("Встреча успешно запланирована на " + secondMeetingDate));
            }
        }
    }
}
