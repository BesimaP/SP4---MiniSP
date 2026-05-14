package controller;

import model.DatabaseConnection;
import model.Session;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class HormoneLogController {

    // Køres når brugeren klikker Tilføj Værdi
    public void handleAddValue(LocalDate date, String hormone, double value, String unit) {
        handleSave(date, hormone, value, unit);
    }

    // Gemmer en hormonværdi i databasen
    public void handleSave(LocalDate date, String hormone, double value, String unit) {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "INSERT INTO hormone_log (journey_id, date, hormone, value, unit) VALUES (?, ?, ?, ?, ?)";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, Session.getCurrentJourneyId());
            statement.setString(2, date.toString());
            statement.setString(3, hormone);
            statement.setDouble(4, value);
            statement.setString(5, unit);
            statement.executeUpdate();
            System.out.println("Hormone value saved!");

            // Gem event i event tabellen
            String eventSql = "INSERT INTO event (journey_id, date, type, description) VALUES (?, ?, ?, ?)";
            PreparedStatement eventStatement = connection.prepareStatement(eventSql);
            eventStatement.setInt(1, Session.getCurrentJourneyId());
            eventStatement.setString(2, date.toString());
            eventStatement.setString(3, "STIMULATION");
            eventStatement.setString(4, hormone + ": " + value + " " + unit);
            eventStatement.executeUpdate();
            System.out.println("Event saved!");

        } catch (SQLException e) {
            System.out.println("Could not save hormone value: " + e.getMessage());
        }
    }

    // Henter den seneste hormonværdi for det aktive forløb
    public String getLatestHormoneValue() {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "SELECT hormone, value, unit FROM hormone_log WHERE journey_id = ? ORDER BY date DESC LIMIT 1";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, Session.getCurrentJourneyId());
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                // Returner fx "Oestradiol: 450 pmol/L"
                return result.getString("hormone") + ": " + result.getDouble("value") + " " + result.getString("unit");
            }
        } catch (SQLException e) {
            System.out.println("Could not get hormone value: " + e.getMessage());
        }
        return "No data"; // hvis ingen hormonværdi findes
    }
}