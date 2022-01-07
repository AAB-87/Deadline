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

    public static AuthInfo getOtherAuthInfo() {
        return new AuthInfo("petya", "555");
    }

    @Value
    public static class VerificationCode { // Код верификации
        private String code;
    }

    @Value
    public static class CardInfo {
        private String cardNumber;
    }

}