package com.fourbarman.jdbc.starter;

import com.fourbarman.jdbc.starter.util.ConnectionManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcRunner {
    public static void main(String[] args) {
        String sql = """
                    CREATE TABLE IF NOT EXISTS info(
                    id SERIAL PRIMARY KEY ,
                    data text NOT NULL );
                """;
        try(Connection connection = ConnectionManager.open();
            Statement statement = connection.createStatement()) {
            System.out.println(connection.getTransactionIsolation());
            boolean executeResult = statement.execute(sql);
            System.out.println(executeResult);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
