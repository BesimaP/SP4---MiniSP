package controller;

import model.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EndRoundController {

    // Afslutter den aktive IVF-runde
    public void handleEndRound(int roundId, String result) {
        Connection connection = DatabaseConnection.getConnection();

        // Opdater rundens status og resultat
        String sql = "UPDATE fertility_journey SET result = ? WHERE id = ? AND journey_id = ?";

        try {
            // Gør SQL klar
            PreparedStatement statement = connection.prepareStatement(sql);

            // Udfyld de to ?
            statement.setString(1, result);  // resultat fx POSITIVE, NEGATIVE eller PENDING
            statement.setInt(2, roundId);    // hvilken runde der afsluttes

            // Gem i databasen
            statement.executeUpdate();
            System.out.println("Round ended!");

        } catch (SQLException e) {
            System.out.println("Could not end round: " + e.getMessage());
        }
    }
}