package controller;

import model.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

// JourneyTypeController håndterer oprettelse af et nyt forløb.
public class JourneyTypeController {

    // Opretter et nyt forløb i databasen og returnerer det nye journey_id
    public int handleSelectJourney(int patientId, String type) {

        Connection connection = DatabaseConnection.getConnection();
        String sql = "INSERT INTO journey (patient_id, type, startDate, status) VALUES (?, ?, ?, ?)";

        try {
            // RETURN_GENERATED_KEYS henter det nye id fra databasen
            PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            statement.setInt(1, patientId);
            statement.setString(2, type);
            statement.setString(3, LocalDate.now().toString());
            statement.setString(4, "ACTIVE");

            statement.executeUpdate();
            System.out.println("Journey created!");

            // Hent det nye journey_id
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            }

        } catch (SQLException e) {
            System.out.println("Could not create journey: " + e.getMessage());
        }

        return -1; // fejl
    }
}