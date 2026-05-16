package controller;

import model.DatabaseConnection;
import model.Patient;
import model.Session;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

// StartSystemController håndterer login når programmet starter
// Den søger efter patienten i databasen med brugernavn og adgangskode
// Returnerer et Patient-objekt hvis login er korrekt — ellers null
public class StartSystemController {

    // Tjekker om brugernavn og adgangskode findes i databasen
    // Returnerer patienten hvis login er korrekt — ellers null
    public Patient handleLogin(String username, String password) {

        // Hent forbindelsen til SQLite databasen
        Connection connection = DatabaseConnection.getConnection();

        // Søg efter patient med det angivne brugernavn og adgangskode
        String sql = "SELECT * FROM patient WHERE username = ? AND password = ?";

        try {
            // Gør SQL klar med PreparedStatement
            PreparedStatement statement = connection.prepareStatement(sql);

            // Udfyld de to ?
            statement.setString(1, username); // brugernavn
            statement.setString(2, password); // adgangskode

            // Hent svaret fra databasen
            ResultSet result = statement.executeQuery();

            // Hvis en patient blev fundet — opret og returner et Patient-objekt
            if (result.next()) {
                System.out.println("Login successful!");
                return new Patient(
                        result.getInt("id"),                                  // unikt id
                        result.getString("name"),                             // navn
                        LocalDate.parse(result.getString("dateOfBirth")),     // fødselsdato
                        result.getString("diagnosis"),                        // diagnose
                        result.getString("username"),                         // brugernavn
                        result.getString("password")                          // adgangskode
                );
            } else {
                // Ingen patient fandt med de angivne oplysninger
                System.out.println("Wrong username or password");
                return null;
            }

        } catch (SQLException e) {
            // Udskriv fejlbesked hvis noget gik galt
            System.out.println("Could not login: " + e.getMessage());
            return null;
        }
    }

    // Tjekker om patienten allerede har et aktivt forløb
    // Hvis ja — gemmes journey_id i Session så alle controllers kan bruge det
    public boolean hasActiveJourney(int patientId) {

        // Hent forbindelsen til SQLite databasen
        Connection connection = DatabaseConnection.getConnection();

        // Søg efter et aktivt forløb for den angivne patient
        String sql = "SELECT * FROM journey WHERE patient_id = ? AND status = 'ACTIVE'";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, patientId);
            ResultSet result = statement.executeQuery();

            // Hvis et aktivt forløb blev fundet
            if (result.next()) {
                // Gem journey_id i Session så andre controllers kan bruge det
                Session.setCurrentJourneyId(result.getInt("id"));
                return true; // aktivt forløb findes
            }

        } catch (SQLException e) {
            System.out.println("Could not check journey: " + e.getMessage());
        }

        // Returner false hvis ingen aktivt forløb findes
        return false;
    }
}