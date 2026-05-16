package controller;

import model.DatabaseConnection;
import model.Session;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

// RoundHistoryController henter historik over alle IVF-runder for det aktive forløb
public class RoundHistoryController {

    // Henter alle IVF-runder for det aktive forløb
    // Returnerer en liste af tekstrækker — én per runde
    public ArrayList<String> initialize() {

        // Hent forbindelsen til SQLite databasen
        Connection connection = DatabaseConnection.getConnection();

        // Hent alle runder der tilhører det aktive forløb
        String sql = "SELECT * FROM fertility_journey WHERE journey_id = ?";

        // Listen der fyldes op og returneres til view
        ArrayList<String> rounds = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, Session.getCurrentJourneyId()); // aktivt forløb

            // Hent resultatet fra databasen
            ResultSet result = statement.executeQuery();

            // Løb igennem alle runder og tilføj dem til listen
            while (result.next()) {
                int roundNumber = result.getInt("roundNumber");
                int eggsRetrieved = result.getInt("eggsRetrieved");       // antal udtagne æg
                int eggsFertilised = result.getInt("eggsFertilised");     // antal befrugtede æg
                String roundResult = result.getString("result");           // fx POSITIVE

                // Byg en tekstlinje for hver runde og tilføj til listen
                rounds.add("Round " + roundNumber +
                        " — Eggs retrieved: " + eggsRetrieved +
                        " — Eggs fertilised: " + eggsFertilised +
                        " — Result: " + roundResult);
            }
            System.out.println("Round history loaded!");

        } catch (SQLException e) {
            // Udskriv fejlbesked hvis noget gik galt
            System.out.println("Could not load round history: " + e.getMessage());
        }

        // Returner listen — tom hvis ingen runder findes
        return rounds;
    }
}