package ru.netology.test;

import lombok.val;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DbInteractionTest {

    @Test
    void shouldBeSuccessfulLogin() {
        open("http://localhost:9999"); // открываем необходимую страницу
        val loginPage = new LoginPage(); // создаём переменную и инициализируем её новым объектом
        val authInfo = DataHelper.getAuthInfo(); // получаем информацию для авторизации для передачи её новому объекту (возвращает authInfo)
        val verificationPage = loginPage.validLogin(authInfo); // у переменной LoginPage вызывваем метод и передаём туда инфу для авторизации (возвращает verificationPage)
        val verificationCode = DbUtils.getVerificationCode(authInfo.getLogin()); // у DbUtils вызываем метод запроса пуш-кода
        val dashboardPage = verificationPage.validVerify(verificationCode);
    }

    @Test
    void shouldBlockedAfterThreeInvalidPassword() {
        open("http://localhost:9999"); // открываем необходимую веб страницу
        val loginPage = new LoginPage(); // создаём переменную и инициализируем её новым объектом
        val OtherAuthInfo = DataHelper.getOtherAuthInfo(); // получаем информацию для авторизации для передачи её новому объекту (возвращает OtherAuthInfo)
        loginPage.invalidLogin(OtherAuthInfo); // вводим неверные логин и пароль, нажимаем Продолжить
        loginPage.cleanLoginFields(); // очищаем поле Логин
        loginPage.invalidLogin(OtherAuthInfo); // вводим неверные логин и пароль, нажимаем Продолжить
        loginPage.cleanLoginFields(); // очищаем поле Логин
        loginPage.invalidLogin(OtherAuthInfo); // вводим неверные логин и пароль, нажимаем Продолжить
        val statusSQL = DbUtils.getStatusFromDb(OtherAuthInfo.getLogin()); // получаем статус
        assertEquals("blocked", statusSQL); // сравниваем ОЖ и полученный статус
    }

    @AfterAll
    static void close() {
        DbUtils.cleanDb(); // очищаем данные в таблицах
    }

}
