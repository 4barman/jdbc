package com.fourbarman.jdbc.starter;

import com.fourbarman.jdbc.starter.util.ConnectionManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

public class TransactionRunner {
    public static void main(String[] args) throws SQLException {
        Long flightId = 9L;
        String deleteFlightSql = "DELETE FROM flight where id = " + flightId;
        String deleteTicketsSql = "DELETE FROM ticket where flight_id = " + flightId;

        Connection connection = null;
        Statement statement = null;
        try {
            connection = ConnectionManager.open();
            connection.setAutoCommit(false);
            statement = connection.createStatement();

            statement.addBatch(deleteTicketsSql);
            statement.addBatch(deleteFlightSql);

            //return [8, 1] where 8 - number of deleted ticket rows, 1 - number of deleted flight rows
            int[] ints = statement.executeBatch();

            System.out.println(Arrays.toString(ints));

            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            if (connection != null) {
                connection.rollback();
            }
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (statement != null) {
                statement.close();
            }
        }
    }
}
