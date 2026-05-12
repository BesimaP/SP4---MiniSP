package controller;

import model.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class HormoneLogController {

    // Køres når brugeren klikker Tilføj Værdi
    public void handleAddValue(int journeyId, LocalDate date, String hormone, double value, String unit) {
        // Kald handleSave med de indtastede oplysninger
        handleSave(journeyId, date, hormone, value, unit);
    }

    // Gemmer en hormonværdi i databasen
    public void handleSave(int journeyId, LocalDate date, String hormone, double value, String unit) {

        // Hent databaseforbindelsen
        Connection connection = DatabaseConnection.getConnection();

        // Gem hormonværdien i hormone_log tabellen
        String sql = "INSERT INTO hormone_log (journey_id, date, hormone, value, unit) VALUES (?, ?, ?, ?, ?)";

        try {
            // Gør SQL klar
            PreparedStatement statement = connection.prepareStatement(sql);

            // Udfyld de fem ?
            statement.setInt(1, journeyId);          // hvilket forløb
            statement.setString(2, date.toString()); // dato
            statement.setString(3, hormone);          // hormontype
            statement.setDouble(4, value);            // værdi
            statement.setString(5, unit);             // enhed

            // Gem i databasen
            statement.executeUpdate();
            System.out.println("Hormone value saved!");

        } catch (SQLException e) {
            System.out.println("Could not save hormone value: " + e.getMessage());
        }
    }
}