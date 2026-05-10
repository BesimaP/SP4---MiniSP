package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    // Den statiske forbindelse til databasen — kun én forbindelse ad gangen
    private static Connection connection = null;

    public static Connection getConnection() {
        // Hvis forbindelsen ikke allerede eksisterer, opret den
        if (connection == null) {
            try {
                // Opret forbindelse til SQLite-databasen simpl.db
                connection = DriverManager.getConnection("jdbc:sqlite:simpl.db");
                System.out.println("Database connected!");
            } catch (SQLException e) {
                // Noget gik galt under forbindelsen
                System.out.println("Could not connect to database: " + e.getMessage());
            }
        }
        // Returner forbindelsen
        return connection;
    }
}