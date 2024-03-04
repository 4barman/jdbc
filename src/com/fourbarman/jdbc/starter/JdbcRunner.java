package com.fourbarman.jdbc.starter;

import com.fourbarman.jdbc.starter.util.ConnectionManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JdbcRunner {
    public static void main(String[] args) {
        String flightId = "2";
        List<Long> listByFlightId = getListByFlightId(flightId);
        System.out.println(listByFlightId);
    }

    private static List<Long> getListByFlightId(String flightId) {
        String sql = """
                SELECT id
                FROM ticket
                WHERE flight_id = %s; DROP TABLE info;
                """.formatted(flightId);
        List<Long> list = new ArrayList<>();
        try (Connection connection = ConnectionManager.open();
        Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()) {
                list.add(resultSet.getObject("id", Long.class)); //NULL SAFE
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
