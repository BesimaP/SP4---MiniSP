package controller;

import model.DatabaseConnection;
import model.Session;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class AppointmentController {

    // Køres når brugeren klikker Tilføj Aftale
    public void handleAddAppointment(LocalDate date, String type, String location) {
        handleSave(date, type, location);
    }

    // Køres når brugeren markerer en aftale som gennemført
    public void handleMarkCompleted(int appointmentId) {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "UPDATE appointment SET completed = 1 WHERE id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, appointmentId);
            statement.executeUpdate();
            System.out.println("Appointment completed!");

        } catch (SQLException e) {
            System.out.println("Could not update appointment: " + e.getMessage());
        }
    }

    // Gemmer en aftale i databasen
    public void handleSave(LocalDate date, String type, String location) {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "INSERT INTO appointment (journey_id, date, type, location) VALUES (?, ?, ?, ?)";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, Session.getCurrentJourneyId());
            statement.setString(2, date.toString());
            statement.setString(3, type);
            statement.setString(4, location);
            statement.executeUpdate();
            System.out.println("Appointment saved!");

            // Gem event i event tabellen
            String eventSql = "INSERT INTO event (journey_id, date, type, description) VALUES (?, ?, ?, ?)";
            PreparedStatement eventStatement = connection.prepareStatement(eventSql);
            eventStatement.setInt(1, Session.getCurrentJourneyId());
            eventStatement.setString(2, date.toString());
            eventStatement.setString(3, "CONSULTATION");
            eventStatement.setString(4, "Appointment: " + type + " at " + location);
            eventStatement.executeUpdate();
            System.out.println("Event saved!");

        } catch (SQLException e) {
            System.out.println("Could not save appointment: " + e.getMessage());
        }
    }
}