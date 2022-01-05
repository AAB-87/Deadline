package ru.netology.test;

import com.github.javafaker.Faker;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class DbInteraction {
    @BeforeEach
    @SneakyThrows // аннотация из Lombok, генерирует обертки для проверяемых исключений, позволяет не писать блоки catch
        // к блоку try или throws в сигнатуре метода для указания возможных исключен
    void SetUp() {
        Faker faker = new Faker();
        String dataSQL = "INSERT INTO users(login, password) VALUES (?, ?);"; // создаём шаблон для ввода любых данных
        try (
                Connection connection = DriverManager.getConnection( //вызываем у DriverManager-а метод getConnection
                        "jdbc:mysql://localhost:3306/app", "AAB-87", "17A05A87b" // cоздаём новое подключение к БД
                );
                PreparedStatement console = connection.prepareStatement(dataSQL); // создаём консоль для написания запросов
        ) {
            console.setString(1, faker.name().username()); // заполяем шаблон
            console.setString(2, "password");
            console.executeUpdate(); // обновляем данные в таблице
        }
    }
}
