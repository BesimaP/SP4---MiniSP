package controller;

import model.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoundHistoryController {

    // Henter alle IVF-runder for det aktive forløb
    public void initialize(int journeyId) {
        Connection connection = DatabaseConnection.getConnection();

        // Hent alle runder fra databasen
        String sql = "SELECT * FROM fertility_journey WHERE journey_id = ?";

        try {
            // Gør SQL klar
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, journeyId); // hvilket forløb

            // Hent svaret fra databasen
            ResultSet result = statement.executeQuery();

            // Løb igennem alle runder
            while (result.next()) {
                int roundNumber = result.getInt("roundNumber");       // rundenummer
                int eggsRetrieved = result.getInt("eggsRetrieved");   // udtagne æg
                int eggsFertilised = result.getInt("eggsFertilised"); // befrugtede æg
                String roundResult = result.getString("result");       // resultat

                System.out.println("Round: " + roundNumber);
                System.out.println("Eggs retrieved: " + eggsRetrieved);
                System.out.println("Eggs fertilised: " + eggsFertilised);
                System.out.println("Result: " + roundResult);
            }

        } catch (SQLException e) {
            System.out.println("Could not load round history: " + e.getMessage());
        }
    }
}