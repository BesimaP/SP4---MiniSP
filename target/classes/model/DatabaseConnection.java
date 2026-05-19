package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

    // DatabaseConnection håndterer forbindelsen til SQLite-databasen simpl.db
    // én forbindelse til databasen, som deles af alle controllers i systemet.
    // Forbindelsen oprettes første gang getConnection() kaldes og genbruges derefter.
    public class DatabaseConnection {

        // Den statiske forbindelse til databasen — kun én forbindelse ad gangen
        private static Connection connection = null;

        // Returnerer forbindelsen til databasen
        // Opretter forbindelsen hvis den ikke allerede eksisterer
        public static Connection getConnection() {
            if (connection == null) {
                try {
                    connection = DriverManager.getConnection("jdbc:sqlite:simpl.db");

                    // Tænd for foreign key-kontrol i SQLite
                    // Skal gøres for hver forbindelse — SQLite har det slået fra som standard
                    Statement statement = connection.createStatement();
                    statement.execute("PRAGMA foreign_keys = ON");

                    System.out.println("Database connected!");
                } catch (SQLException e) {
                    System.out.println("Could not connect to database: " + e.getMessage());
                }
            }
            return connection;
        }
    }