package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class VerificationPage { // Страница верефикации
    private SelenideElement codeField = $("[data-test-id=code] input"); // поле для кода из пуш-уведомления
    private SelenideElement verifyButton = $("[data-test-id=action-verify]"); // кнопка подтверждения

    public VerificationPage() { // проверяем что поле ввода кода из пуш-уведомления появилось
        codeField.shouldBe(visible);
    } // проверяем что поле для ввода пуш-уведомления появилось

    public DashboardPage validVerify(String code) { // запрашиваем инфу у DataHelper
        codeField.setValue(code); // вводим код из пуш-уведомления
        verifyButton.click(); // кнопка подтверждения
        return new DashboardPage(); // возвражается страница Личного кабинета
    }
}