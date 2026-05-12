package controller;

import model.DatabaseConnection;
import model.Session;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class DiaryController {

    // Køres når brugeren klikker Tilføj Note
    public void handleAddNote(LocalDate date, String title, String content) {
        // Kald handleSave med de indtastede oplysninger
        handleSave(Session.getCurrentJourneyId(), date, title, content);
    }

    // Gemmer en dagbogsnote i databasen
    public void handleSave(int journeyId, LocalDate date, String title, String content) {
        Connection connection = DatabaseConnection.getConnection();

        String sql = "INSERT INTO diary_entry (journey_id, date, title, content) VALUES (?, ?, ?, ?)";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, journeyId);           // hvilket forløb
            statement.setString(2, date.toString());  // dato
            statement.setString(3, title);             // titel
            statement.setString(4, content);           // indhold
            statement.executeUpdate();
            System.out.println("Diary entry saved!");
        } catch (SQLException e) {
            System.out.println("Could not save diary entry: " + e.getMessage());
        }
    }
}