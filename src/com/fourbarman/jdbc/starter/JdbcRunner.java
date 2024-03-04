package com.fourbarman.jdbc.starter;

import com.fourbarman.jdbc.starter.util.ConnectionManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcRunner {
    public static void main(String[] args) {
        String sql = """
                    INSERT INTO info(data)
                    VALUES ('test1'), ('test2'), ('test3'), ('test4');
                """;
        try(Connection connection = ConnectionManager.open();
            Statement statement = connection.createStatement()) {
            System.out.println(connection.getTransactionIsolation());
            int executeResult = statement.executeUpdate(sql);
            System.out.println(executeResult);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
