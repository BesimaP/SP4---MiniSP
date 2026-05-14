package controller;

import model.DatabaseConnection;
import model.Session;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class NewRoundController {

    public void handleStartRound(int roundNumber) {

        Connection connection = DatabaseConnection.getConnection();

        // Gem ny runde i fertility_journey tabellen
        String sql = "INSERT INTO fertility_journey (journey_id, roundNumber, result) VALUES (?, ?, ?)";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, Session.getCurrentJourneyId());
            statement.setInt(2, roundNumber);
            statement.setString(3, "PENDING");
            statement.executeUpdate();
            System.out.println("New round started!");

            // Gem event i event tabellen
            String eventSql = "INSERT INTO event (journey_id, date, type, description) VALUES (?, ?, ?, ?)";
            PreparedStatement eventStatement = connection.prepareStatement(eventSql);
            eventStatement.setInt(1, Session.getCurrentJourneyId());
            eventStatement.setString(2, LocalDate.now().toString());
            eventStatement.setString(3, "OTHER");
            eventStatement.setString(4, "Round " + roundNumber + " started");
            eventStatement.executeUpdate();
            System.out.println("Event saved!");

        } catch (SQLException e) {
            System.out.println("Could not start round: " + e.getMessage());
        }
    }
}