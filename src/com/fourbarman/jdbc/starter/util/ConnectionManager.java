package com.fourbarman.jdbc.starter.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Utility class
 */
public final class ConnectionManager {
    private static final String PASSWORD = "postgres";
    private static final String USER = "postgres";
    private static final String URL = "jdbc:postgresql://localhost:5432/flight_repository";

    /**
     * Before jdk 1.8 we have to load driver
     */
    static {
        loadDriver();
    }

    private static void loadDriver() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private ConnectionManager() {

    }

    public static Connection open() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
