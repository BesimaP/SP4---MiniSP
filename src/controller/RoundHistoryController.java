package controller;

import model.DatabaseConnection;
import model.Session;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RoundHistoryController {

    // Henter alle IVF-runder for det aktive forløb
    public ArrayList<String> initialize() {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "SELECT * FROM fertility_journey WHERE journey_id = ?";

        ArrayList<String> rounds = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, Session.getCurrentJourneyId());

            ResultSet result = statement.executeQuery();

            // Løb igennem alle runder
            while (result.next()) {
                int roundNumber = result.getInt("roundNumber");
                int eggsRetrieved = result.getInt("eggsRetrieved");
                int eggsFertilised = result.getInt("eggsFertilised");
                String roundResult = result.getString("result");

                // Tilføj runden som en tekstlinje
                rounds.add("Round " + roundNumber +
                        " — Eggs retrieved: " + eggsRetrieved +
                        " — Eggs fertilised: " + eggsFertilised +
                        " — Result: " + roundResult);
            }
            System.out.println("Round history loaded!");

        } catch (SQLException e) {
            System.out.println("Could not load round history: " + e.getMessage());
        }

        return rounds;
    }
}