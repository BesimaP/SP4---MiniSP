package controller;

import model.DatabaseConnection;
import model.Session;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

// Håndterer tilføjelse og gemning af medicinindtastninger
public class MedicationLogController {

    // Køres når brugeren klikker Tilføj Medicin
    public void handleAddMedication(LocalDate date, String medication, String dose, boolean taken) {
        handleSave(date, medication, dose, taken);
    }

    // Gemmer en medicinindtastning i databasen
    public void handleSave(LocalDate date, String medication, String dose, boolean taken) {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "INSERT INTO medication_log (journey_id, date, medication, dose, taken) VALUES (?, ?, ?, ?, ?)";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, Session.getCurrentJourneyId());
            statement.setString(2, date.toString());
            statement.setString(3, medication);
            statement.setString(4, dose);
            statement.setBoolean(5, taken);
            statement.executeUpdate();
            System.out.println("Medication saved!");

            // Gem event i event tabellen
            String eventSql = "INSERT INTO event (journey_id, date, type, description) VALUES (?, ?, ?, ?)";
            PreparedStatement eventStatement = connection.prepareStatement(eventSql);
            eventStatement.setInt(1, Session.getCurrentJourneyId());
            eventStatement.setString(2, date.toString());
            eventStatement.setString(3, "OTHER");
            eventStatement.setString(4, "Medication: " + medication + " " + dose);
            eventStatement.executeUpdate();
            System.out.println("Event saved!");

        } catch (SQLException e) {
            System.out.println("Could not save medication: " + e.getMessage());
        }
    }
}