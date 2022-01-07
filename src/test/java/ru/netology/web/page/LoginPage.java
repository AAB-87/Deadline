package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage { // Страница входа
    // Реализуем pattern Page Object (позволяет реализовывать несколько методов и не копировать их, а обращаться к приватным полям)
    // Все элемынеы с которыми мы взаимодействуем (их селекторы) выносятся в приватные поля класса
    // а внутри теста обращаемся к полям класса
    private SelenideElement loginField = $("[data-test-id=login] input"); // находим поле Логин
    private SelenideElement passwordField = $("[data-test-id=password] input"); // находим поле Пароль
    private SelenideElement loginButton = $("[data-test-id=action-login]"); // находим кнопку Продолжить

    public VerificationPage validLogin(DataHelper.AuthInfo info) { // запрашиваем инфу у DataHelper
        // обращаемся к полям класса
        loginField.setValue(info.getLogin()); // заполняем поле Логин
        passwordField.setValue(info.getPassword()); // заполняем поле Пароль
        loginButton.click(); // кликаем по кнопке Продолжить
        return new VerificationPage(); // возвращает страницу для прохождения верефикации (см. VerificationPage)
    }

    public void cleanLoginFields() { // очищаем поля на странице входа
        loginField.doubleClick().sendKeys(Keys.BACK_SPACE); // кликаем дважды в поле Логин (т.е. выделяем логин) и стираем значение
        passwordField.doubleClick().sendKeys(Keys.BACK_SPACE); // кликаем дважды в поле Пароль (т.е. выделяем пароль) и стираем значение
    }

    public void invalidLogin(DataHelper.AuthInfo otherInfo) {// запрашиваем инфу у DataHelper
        // обращаемся к полям класса
        loginField.setValue(otherInfo.getLogin()); // заполняем поле Логин
        passwordField.setValue(otherInfo.getPassword()); // заполняем поле Пароль
        loginButton.click(); // кликаем по кнопке Продолжить
    }

}