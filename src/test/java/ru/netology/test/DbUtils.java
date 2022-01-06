package ru.netology.test;

import lombok.SneakyThrows;

import java.sql.*;

public class DbUtils {

    private DbUtils() { // конструктор
    }

    @SneakyThrows
    //  генерирует обертки для проверяемых исключений, позволяет не писать блоки catch к блоку try или throws в сигнатуре метода для указания возможных исключений
    public static Connection getConnection() { // создаём подключение к БД
        final Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/app", "AAB-87", "17A05A87b");
        return connection;
    }

    @SneakyThrows
    //  генерирует обертки для проверяемых исключений, позволяет не писать блоки catch к блоку try или throws в сигнатуре метода для указания возможных исключений
    public static String getVerificationCode(String login) { // получаем код для пуш уведомления из БД
        String userId = null;
        String dataSQL = "SELECT id FROM users WHERE login = ?;"; // пишем запрос, ? - значит используем как шаблон (placeholder)
        try (Connection connection = getConnection(); // try-with-resources, конструкция языка, позволяющая не писать нам вызов метода close. Компилятор сам сгенерирует правильный вызов закрытия ресурсов
             PreparedStatement idStmt = connection.prepareStatement(dataSQL); // создаём консоль под именем idStmt (интерфейс) через которую будем отправлять SQL-запросы. Консоль принимает в параметрах запрос
        ) {
            idStmt.setString(1, login); // подставляем (в SQL-запросе) вместо знака ? значение
            try (ResultSet rs = idStmt.executeQuery()) { // вызываем у косоли обновление - executeQuery (обновление для SELECT-запроса (возвращает ResultSet)) и записываем его в ResultSet который может хранить в качестве результата любое кол-во возвращаемых строк и столбцов
                if (rs.next()) { // метод next опредиляет есть ли следующая строка в возвращённом от БД результате. Next возвращает true если в вернувшимся запросе есть первая строка и false если её нет (т.е. пустой ответ пришёл). Так вот если в итоге true, то выполнится следующее условие ниже
                    userId = rs.getString("id"); // сохраняем полученный id в String-овом формате и записываем его в переменную userId
                }
            }
        }
        String code = null;
        String authCode = "SELECT code FROM auth_codes WHERE user_id = ? order by created desc limit 1;"; // пишем запрос, ? - значит используем как шаблон (placeholder)
        try (Connection connection = getConnection(); // try-with-resources, конструкция языка, позволяющая не писать нам вызов метода close. Компилятор сам сгенерирует правильный вызов закрытия ресурсов
             PreparedStatement codeStmt = connection.prepareStatement(authCode); // создаём консоль под именем codeStmt (интерфейс) через которую будем отправлять SQL-запросы. Консоль принимает в параметрах запрос
        ) {
            codeStmt.setString(1, userId); // подставляем (в SQL-запросе) вместо знака ? значение
            try (ResultSet rs = codeStmt.executeQuery()) { // вызываем у косоли обновление - executeQuery (обновление для SELECT-запроса (возвращает ResultSet)) и записываем его в ResultSet который может хранить в качестве результата любое кол-во возвращаемых строк и столбцов
                if (rs.next()) { // метод next опредиляет есть ли следующая строка в возвращённом от БД результате. Next возвращает true если в вернувшимся запросе есть первая строка и false если её нет (т.е. пустой ответ пришёл). Так вот если в итоге true, то выполнится следующее условие ниже
                    code = rs.getString("code"); // сохраняем полученный code в String-овом формате и записываем его в переменную code
                }
            }
        }
        return code;
    }

    @SneakyThrows
    //  генерирует обертки для проверяемых исключений, позволяет не писать блоки catch к блоку try или throws в сигнатуре метода для указания возможных исключений
    public static String getStatusFromDb(String login) { // получаем статус
        String statusSQL = "SELECT status FROM users WHERE login = ?;";
        String status = null;
        try (Connection connection = getConnection(); // try-with-resources, конструкция языка, позволяющая не писать нам вызов метода close. Компилятор сам сгенерирует правильный вызов закрытия ресурсов
             PreparedStatement statusStm = connection.prepareStatement(statusSQL); // создаём консоль под именем statusStm (интерфейс) через которую будем отправлять SQL-запросы. Консоль принимает в параметрах запрос
        ) {
            statusStm.setString(1, login); // подставляем (в SQL-запросе) вместо знака ? значение
            try (ResultSet rs = statusStm.executeQuery()) { // вызываем у косоли обновление - executeQuery (обновление для SELECT-запроса (возвращает ResultSet)) и записываем его в ResultSet который может хранить в качестве результата любое кол-во возвращаемых строк и столбцов
                if (rs.next()) { // метод next опредиляет есть ли следующая строка в возвращённом от БД результате. Next возвращает true если в вернувшимся запросе есть первая строка и false если её нет (т.е. пустой ответ пришёл). Так вот если в итоге true, то выполнится следующее условие ниже
                    status = rs.getString("status"); // сохраняем полученный status в String-овом формате и записываем его в переменную status
                }
            }
        }
        return status;
    }

    @SneakyThrows
    public static void cleanDb() { // очищаем БД
        String deleteCards = "DELETE FROM cards WHERE TRUE;"; // чистим таблицы в следующем порядке т.к. у первых двух есть ограничение по внешнему ключу (FOREIGN KEY)
        String deleteAuthCodes = "DELETE FROM auth_codes WHERE TRUE;";
        String deleteUsers = "DELETE FROM users WHERE TRUE;";
        try (Connection connection = DbUtils.getConnection(); // try-with-resources, конструкция языка, позволяющая не писать нам вызов метода close. Компилятор сам сгенерирует правильный вызов закрытия ресурсов. Вызываем у конструктора DbUtils метод getConnection
             Statement deleteCardsStmt = connection.createStatement(); // создаём консоль под именем deleteCardsStmt (интерфейс) через которую будем отправлять SQL-запросы. Консоль принимает в параметрах запрос
             Statement deleteAuthCodesStmt = connection.createStatement(); // создаём консоль под именем deleteAuthCodesStmt (интерфейс) через которую будем отправлять SQL-запросы. Консоль принимает в параметрах запрос
             Statement deleteUsersStmt = connection.createStatement(); // создаём консоль под именем deleteUsersStmt (интерфейс) через которую будем отправлять SQL-запросы. Консоль принимает в параметрах запрос
        ) {
            deleteCardsStmt.executeUpdate(deleteCards); // в консоле вызываем Update в параметры которого принимаются SQl- запрос на удаление см.стр 71
            deleteAuthCodesStmt.executeUpdate(deleteAuthCodes); // в консоле вызываем Update в параметры которого принимаются SQl- запрос на удаление см.стр 72
            deleteUsersStmt.executeUpdate(deleteUsers); // в консоле вызываем Update в параметры которого принимаются SQl- запрос на удаление см.стр 73
        }
    }
}
