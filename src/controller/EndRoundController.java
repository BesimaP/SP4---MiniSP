package controller;

import model.DatabaseConnection;
import model.Session;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class EndRoundController {

    // Afslutter den aktive IVF-runde
    public void handleEndRound(int journeyId, String result) {
        Connection connection = DatabaseConnection.getConnection();

        try {
            // Opdater resultatet i fertility_journey tabellen
            String sql1 = "UPDATE fertility_journey SET result = ? WHERE journey_id = ?";
            PreparedStatement statement1 = connection.prepareStatement(sql1);
            statement1.setString(1, result);
            statement1.setInt(2, journeyId);
            statement1.executeUpdate();
            System.out.println("Round result updated!");

            // Opdater status til COMPLETED i journey tabellen
            String sql2 = "UPDATE journey SET status = 'COMPLETED' WHERE id = ?";
            PreparedStatement statement2 = connection.prepareStatement(sql2);
            statement2.setInt(1, journeyId);
            statement2.executeUpdate();
            System.out.println("Journey completed!");

            // Gem event i event tabellen
            String eventSql = "INSERT INTO event (journey_id, date, type, description) VALUES (?, ?, ?, ?)";
            PreparedStatement eventStatement = connection.prepareStatement(eventSql);
            eventStatement.setInt(1, journeyId);
            eventStatement.setString(2, LocalDate.now().toString());
            eventStatement.setString(3, "PREGNANCY_TEST");
            eventStatement.setString(4, "Round ended with result: " + result);
            eventStatement.executeUpdate();
            System.out.println("Event saved!");

        } catch (SQLException e) {
            System.out.println("Could not end round: " + e.getMessage());
        }
    }
}