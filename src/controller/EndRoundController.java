package controller;

import model.DatabaseConnection;
import model.Session;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

// EndRoundController håndterer afslutning af den aktive IVF-runde
public class EndRoundController {

    // Afslutter den aktive IVF-runde
    // journeyId = det aktive forløbs id
    // result = POSITIVE, NEGATIVE eller PENDING
    public void handleEndRound(int journeyId, String result) {

        // Hent forbindelsen til SQLite databasen
        Connection connection = DatabaseConnection.getConnection();

        try {
            // 1. Opdater resultatet i fertility_journey tabellen
            // Sætter fx result = 'POSITIVE' for den aktive runde
            String sql1 = "UPDATE fertility_journey SET result = ? WHERE journey_id = ?";
            PreparedStatement statement1 = connection.prepareStatement(sql1);
            statement1.setString(1, result);    // fx POSITIVE
            statement1.setInt(2, journeyId);    // hvilken runde
            statement1.executeUpdate();
            System.out.println("Round result updated!");

            // 2. Opdater status til COMPLETED i journey tabellen
            // Så systemet ved at forløbet er afsluttet ved næste login
            String sql2 = "UPDATE journey SET status = 'COMPLETED' WHERE id = ?";
            PreparedStatement statement2 = connection.prepareStatement(sql2);
            statement2.setInt(1, journeyId);
            statement2.executeUpdate();
            System.out.println("Journey completed!");

            // 3. Gem en hændelse i event tabellen så tidslinjen opdateres
            String eventSql = "INSERT INTO event (journey_id, date, type, description) VALUES (?, ?, ?, ?)";
            PreparedStatement eventStatement = connection.prepareStatement(eventSql);
            eventStatement.setInt(1, journeyId);
            eventStatement.setString(2, LocalDate.now().toString()); // dagens dato
            eventStatement.setString(3, "PREGNANCY_TEST");
            eventStatement.setString(4, "Round ended with result: " + result);
            eventStatement.executeUpdate();
            System.out.println("Event saved!");

        } catch (SQLException e) {
            // Udskriv fejlbesked hvis noget gik galt
            System.out.println("Could not end round: " + e.getMessage());
        }
    }
}