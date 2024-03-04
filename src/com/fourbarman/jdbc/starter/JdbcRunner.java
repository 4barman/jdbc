package com.fourbarman.jdbc.starter;

import com.fourbarman.jdbc.starter.util.ConnectionManager;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class JdbcRunner {
    public static void main(String[] args) throws SQLException {
//        Long flightId = 2L;
//        List<Long> listByFlightId = getListByFlightId(flightId);
//        System.out.println(listByFlightId);
//        System.out.println(getFlightsBetween(LocalDate.of(2020, 10, 1).atStartOfDay(), LocalDate.now().atStartOfDay()));
        try{
            checkMetadata();
        } finally {
            ConnectionManager.closePool();
        }
    }

    private static void checkMetadata() throws SQLException {
        try (Connection connection = ConnectionManager.get()) {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet catalogs = metaData.getCatalogs();
            //all databases
            while (catalogs.next()) {
                System.out.println(catalogs.getString(1));
                //current database schemas
                ResultSet schemas = metaData.getSchemas();
                while (schemas.next()) {
                    System.out.println(schemas.getString("TABLE_SCHEM"));

                    //table names. "%" means all.
                    ResultSet tables = metaData.getTables(null, null, "%", null);
                    while(tables.next()) {
                        System.out.println(tables.getString("TABLE_NAME"));
                    }
                }
            }
        }
    }

    private static List<Long> getFlightsBetween(LocalDateTime start, LocalDateTime end) {
        String sql = """
                SELECT id
                FROM flight
                WHERE departure_date BETWEEN ? AND ?;
                """;
        List<Long> result = new ArrayList<>();
        try (Connection connection = ConnectionManager.get();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setFetchSize(50);
            preparedStatement.setQueryTimeout(10);
            preparedStatement.setMaxRows(100);

            System.out.println(preparedStatement);
            preparedStatement.setTimestamp(1, Timestamp.valueOf(start));
            System.out.println(preparedStatement);
            preparedStatement.setTimestamp(2, Timestamp.valueOf(end));
            System.out.println(preparedStatement);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                result.add(resultSet.getLong("id"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    private static List<Long> getListByFlightId(Long flightId) {
        String sql = """
                SELECT id
                FROM ticket
                WHERE flight_id = ?
                """;
        List<Long> list = new ArrayList<>();
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, flightId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                list.add(resultSet.getObject("id", Long.class)); //NULL SAFE
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
