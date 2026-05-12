package controller;

import model.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

// Håndterer oprettelse af en ny IVF-runde
public class NewRoundController {

    // Gemmer en ny IVF-runde i databasen
    public void handleStartRound(int journeyId, int roundNumber) {

        // Hent forbindelse
        Connection connection = DatabaseConnection.getConnection();

        // SQL
        String sql = "INSERT INTO fertility_journey (journey_id, roundNumber, result) VALUES (?, ?, ?)";

        try {
            // Forbered SQL
            PreparedStatement statement = connection.prepareStatement(sql);

            // Udfyld ?
            statement.setInt(1, journeyId);
            statement.setInt(2, roundNumber);
            statement.setString(3, "PENDING");

            // Gem i databasen
            statement.executeUpdate();
            System.out.println("New round started!");

        } catch (SQLException e) {
            // Fejl
            System.out.println("Could not start round: " + e.getMessage());
        }
    }
}