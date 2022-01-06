package ru.netology.test;

import lombok.val;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;

public class DbInteractionTest {

    @Test
    @BeforeEach
    void setUp() {
        open("http://localhost:9999"); // открываем необходимую страницу
        val loginPage = new LoginPage(); // создаём переменную и инициализируем её новым объектом
        val authInfo = DataHelper.getAuthInfo(); // получаем информацию для авторизации для передачи её новому объекту (возвращает authInfo)
        val verificationPage = loginPage.validLogin(authInfo); // у переменной LoginPage вызывваем метод и передаём туда инфу для авторизации (возвращает verificationPage)
        val verificationCode = DataHelper.getVerificationCodeFor(authInfo); // у DataHelper вызываем метод запроса пуш-кода
        val dashboardPage = verificationPage.validVerify(verificationCode);
    }

}
