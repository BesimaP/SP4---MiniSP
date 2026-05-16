package controller;

import model.DatabaseConnection;
import model.Session;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

// NewRoundController håndterer opstart af en ny IVF-runde
public class NewRoundController {

    // Starter en ny IVF-runde og gemmer den i databasen
    // roundNumber = hvilken runde det er fx 1, 2 eller 3
    public void handleStartRound(int roundNumber) {

        // Hent forbindelsen til SQLite databasen
        Connection connection = DatabaseConnection.getConnection();

        // SQL der indsætter en ny runde i fertility_journey tabellen
        // result sættes til PENDING fordi resultatet ikke kendes endnu
        String sql = "INSERT INTO fertility_journey (journey_id, roundNumber, result) VALUES (?, ?, ?)";

        try {
            // Gør SQL klar med PreparedStatement
            PreparedStatement statement = connection.prepareStatement(sql);

            // Udfyld de tre ?
            statement.setInt(1, Session.getCurrentJourneyId()); // aktivt forløb
            statement.setInt(2, roundNumber);                   // rundenummer
            statement.setString(3, "PENDING");                  // resultat afventer

            // Gem i databasen
            statement.executeUpdate();
            System.out.println("New round started!");

            // Gem også en hændelse i event tabellen så tidslinjen opdateres
            String eventSql = "INSERT INTO event (journey_id, date, type, description) VALUES (?, ?, ?, ?)";
            PreparedStatement eventStatement = connection.prepareStatement(eventSql);
            eventStatement.setInt(1, Session.getCurrentJourneyId());
            eventStatement.setString(2, LocalDate.now().toString()); // dagens dato
            eventStatement.setString(3, "OTHER");
            eventStatement.setString(4, "Round " + roundNumber + " started");
            eventStatement.executeUpdate();
            System.out.println("Event saved!");

        } catch (SQLException e) {
            // Udskriv fejlbesked hvis noget gik galt
            System.out.println("Could not start round: " + e.getMessage());
        }
    }
}