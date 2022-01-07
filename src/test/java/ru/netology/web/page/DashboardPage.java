package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class DashboardPage { // Страница личного кабинета
    private SelenideElement heading = $("[data-test-id=dashboard]"); // находим заголовок Личный кабинет


    public DashboardPage() {
        heading.shouldBe(visible); // заголовок страницы (Личный кабинет) должен быть виден
    }

}