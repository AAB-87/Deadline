package ru.netology.web.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.val;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage { // Страница личного кабинета
    private SelenideElement heading = $("[data-test-id=dashboard]"); // находим заголовок Личный кабинет
    private SelenideElement buttonFirstCard = $("[data-test-id='92df3f1c-a033-48e6-8390-206f6b1f56c0'] button"); // находим кнопку Пополнить первой карты
    private SelenideElement buttonSecondCard = $("[data-test-id='0f3f5c2a-249e-4c3d-8287-09f7a039391d'] button"); // находим кнопку Пополнить второй карты
    private ElementsCollection cards = $$(".list__item"); // где list__item - элемент строки содержащий **** **** **** 0001, баланс: 10000 р. + кнопку
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";


    public DashboardPage() {
        heading.shouldBe(visible); // заголовок страницы (Личный кабинет) должен быть виден
    }

    private int extractBalance(String text) { // Извлечение баланса из текста элемента
        val start = text.indexOf(balanceStart); // indexOf - возвращает позицию, с которой начинается подстрока в строке
        val finish = text.indexOf(balanceFinish); // indexOf - возвращает позицию, с которой начинается подстрока в строке
        val value = text.substring(start + balanceStart.length(), finish); // substring - вырезает подстроку из строки
        return Integer.parseInt(value);
    }

    public int getFirstCardBalance() {
        val text = cards.first().text(); // метод text возвращает текст элемента **** **** **** 0001, баланс: 10000 р. + кнопку
        return extractBalance(text); // извлекает баланс первой карты из элеманта
    }

    public int getSecondCardBalance() {
        val text = cards.last().text(); // метод text возвращает текст элемента **** **** **** 0001, баланс: 10000 р. + кнопку
        return extractBalance(text); // извлекает баланс второй карты из элеманта
    }

}