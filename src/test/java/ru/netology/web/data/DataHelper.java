package ru.netology.web.data;

import lombok.Value;

public class DataHelper {
    private DataHelper() {
    }

    @Value
    public static class AuthInfo { // Информация об авторизации
        private String login;
        private String password;
    }
    //@Value - аннотация Lombok, дает возможность с помощью аннотации создавать Value Objects.
    //Генерирует для объекта конструктор, методы toString()/equals()/hashCode()

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    @Value
    public static class VerificationCode { // Код верификации
        private String code;
    }

    public static VerificationCode getVerificationCodeFor(AuthInfo authInfo) {
        return new VerificationCode("12345");
    }

    @Value
    public static class CardInfo {
        private String cardNumber;
    }

    public static CardInfo getFirstCardNumber() {
        return new CardInfo("5559_0000_0000_0001");
    }

    public static CardInfo getSecondCardNumber() {
        return new CardInfo("5559_0000_0000_0002");
    }

}