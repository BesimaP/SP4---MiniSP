package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static Connection connection = null;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection("jdbc:sqlite:simpl.db");
                System.out.println("Database connected!");
            } catch (SQLException e) {
                System.out.println("Could not connect to database: " + e.getMessage());
            }
        }
        return connection;
    }
}