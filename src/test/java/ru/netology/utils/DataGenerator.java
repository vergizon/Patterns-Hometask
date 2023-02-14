package ru.netology.utils;

import com.github.javafaker.Faker;
import ru.netology.data.CardUserInfo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DataGenerator {
    @UtilityClass
    public static class Registration {
        public static CardUserInfo generateUser(String locale) {
            Faker faker = new Faker(new Locale(locale));
            return new CardUserInfo(faker.address().city(),
                    faker.name().fullName(),
                    faker.phoneNumber().phoneNumber());
        }
    }

    public static String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }
}