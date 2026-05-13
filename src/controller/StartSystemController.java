package controller;

import model.DatabaseConnection;
import model.Patient;
import model.Session;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

    // StartSystemController håndterer login når programmet starter.
    // Den søger efter patienten i databasen med brugernavn og adgangskode.
    // Returnerer et Patient-objekt hvis login er korrekt — ellers null.
    public class StartSystemController {

        // Tjekker om brugernavn og adgangskode findes i databasen
        // Returnerer patienten hvis login er korrekt — ellers null
        public Patient handleLogin(String username, String password) {

            // Hent databaseforbindelsen
            Connection connection = DatabaseConnection.getConnection();

            // Søg efter patient med brugernavn og adgangskode
            String sql = "SELECT * FROM patient WHERE username = ? AND password = ?";

            try {
                // Gør SQL klar
                PreparedStatement statement = connection.prepareStatement(sql);

                // Udfyld de to ?
                statement.setString(1, username);
                statement.setString(2, password);

                // Hent svaret fra databasen
                ResultSet result = statement.executeQuery();

                // Hvis patienten findes — returner Patient-objektet
                if (result.next()) {
                    System.out.println("Login successful!");
                    return new Patient(
                            result.getInt("id"),  // tilføj id
                            result.getString("name"),
                            LocalDate.parse(result.getString("dateOfBirth")),
                            result.getString("diagnosis"),
                            result.getString("username"),
                            result.getString("password")
                    );
                } else {
                    // Forkert brugernavn eller adgangskode
                    System.out.println("Wrong username or password");
                    return null;
                }

            } catch (SQLException e) {
                // Noget gik galt med databasen
                System.out.println("Could not login: " + e.getMessage());
                return null;
            }
        }

        // Tjekker om patienten allerede har et aktivt forløb
        public boolean hasActiveJourney(int patientId) {
            Connection connection = DatabaseConnection.getConnection();
            String sql = "SELECT * FROM journey WHERE patient_id = ? AND status = 'ACTIVE'";

            try {
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, patientId);
                ResultSet result = statement.executeQuery();

                if (result.next()) {
                    // Gem journey_id i Session
                    Session.setCurrentJourneyId(result.getInt("id"));
                    return true; // aktivt forløb findes
                }
            } catch (SQLException e) {
                System.out.println("Could not check journey: " + e.getMessage());
            }
            return false; // ingen aktivt forløb
        }
    }