package controller;

import model.DatabaseConnection;
import model.Session;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class DiaryController {

    public DiaryController() {}

    // Køres når brugeren klikker Tilføj Note
    public void handleAddNote(LocalDate date, String title, String content) {
        handleSave(date, title, content);
    }

    // Gemmer en dagbogsnote i databasen
    public void handleSave(LocalDate date, String title, String content) {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "INSERT INTO diary_entry (journey_id, date, title, content) VALUES (?, ?, ?, ?)";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, Session.getCurrentJourneyId());
            statement.setString(2, date.toString());
            statement.setString(3, title);
            statement.setString(4, content);
            statement.executeUpdate();
            System.out.println("Diary entry saved!");

            // Gem event i event tabellen
            String eventSql = "INSERT INTO event (journey_id, date, type, description) VALUES (?, ?, ?, ?)";
            PreparedStatement eventStatement = connection.prepareStatement(eventSql);
            eventStatement.setInt(1, Session.getCurrentJourneyId());
            eventStatement.setString(2, date.toString());
            eventStatement.setString(3, "OTHER");
            eventStatement.setString(4, "Diary entry: " + title);
            eventStatement.executeUpdate();
            System.out.println("Event saved!");

        } catch (SQLException e) {
            System.out.println("Could not save diary entry: " + e.getMessage());
        }
    }

    // Tæller antal dagbogsnoter for det aktive forløb
    public int countDiaryEntries() {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "SELECT COUNT(*) FROM diary_entry WHERE journey_id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, Session.getCurrentJourneyId());
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                return result.getInt(1); // returner antal noter
            }
        } catch (SQLException e) {
            System.out.println("Could not count diary entries: " + e.getMessage());
        }
        return 0; // hvis ingen noter findes
    }
}