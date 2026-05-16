package controller;

import model.DatabaseConnection;
import model.Session;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

    // DiaryController håndterer gemning og optælling af dagbogsnoter
    public class DiaryController {

        public DiaryController() {}

        // Køres når brugeren klikker Tilføj Note
        public void handleAddNote(LocalDate date, String title, String content) {
            handleSave(date, title, content);
        }

        // Gemmer en dagbogsnote i databasen
        public void handleSave(LocalDate date, String title, String content) {
            Connection connection = DatabaseConnection.getConnection();

            // Indsæt noten i diary_entry tabellen
            String sql = "INSERT INTO diary_entry (journey_id, date, title, content) VALUES (?, ?, ?, ?)";

            try {
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, Session.getCurrentJourneyId()); // aktivt forløb
                statement.setString(2, date.toString());            // dato som tekst
                statement.setString(3, title);                      // titel på noten
                statement.setString(4, content);                    // indhold
                statement.executeUpdate();
                System.out.println("Diary entry saved!");

                // Gem også en hændelse i event tabellen så tidslinjen opdateres
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
        // Bruges til at vise statistik på dashboardet
        public int countDiaryEntries() {
            Connection connection = DatabaseConnection.getConnection();

            // COUNT(*) tæller antal rækker der matcher betingelsen
            String sql = "SELECT COUNT(*) FROM diary_entry WHERE journey_id = ?";

            try {
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, Session.getCurrentJourneyId());
                ResultSet result = statement.executeQuery();

                // result.getInt(1) henter værdien fra første kolonne — altså antallet
                if (result.next()) {
                    return result.getInt(1);
                }
            } catch (SQLException e) {
                System.out.println("Could not count diary entries: " + e.getMessage());
            }

            return 0; // returner 0 hvis ingen noter findes
        }
    }