package com.fourbarman.jdbc.starter;

import com.fourbarman.jdbc.starter.util.ConnectionManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcRunner {
    public static void main(String[] args) {
        String sql = """
                    SELECT *
                    FROM ticket;
                """;
        try(Connection connection = ConnectionManager.open();
            Statement statement = connection.createStatement()) {
            System.out.println(connection.getTransactionIsolation());

            ResultSet executeResult = statement.executeQuery(sql);

            System.out.println(executeResult);

            while(executeResult.next()) {
                System.out.println(executeResult.getLong("id"));
                System.out.println(executeResult.getString("passenger_no"));
                System.out.println(executeResult.getBigDecimal("cost"));
                System.out.println("-----------");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
