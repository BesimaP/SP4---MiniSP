package controller;

import model.DatabaseConnection;
import model.Session;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

    // AppointmentController håndterer oprettelse og hentning af aftaler
    public class AppointmentController {

        // Køres når brugeren klikker Tilføj Aftale
        public void handleAddAppointment(LocalDate date, String type, String location) {
            handleSave(date, type, location);
        }

        // Markerer en aftale som gennemført i databasen
        public void handleMarkCompleted(int appointmentId) {
            Connection connection = DatabaseConnection.getConnection();

            // Sæt completed til 1 (true) for den valgte aftale
            String sql = "UPDATE appointment SET completed = 1 WHERE id = ?";

            try {
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, appointmentId); // hvilken aftale
                statement.executeUpdate();
                System.out.println("Appointment completed!");
            } catch (SQLException e) {
                System.out.println("Could not update appointment: " + e.getMessage());
            }
        }

        // Gemmer en ny aftale i databasen
        public void handleSave(LocalDate date, String type, String location) {
            Connection connection = DatabaseConnection.getConnection();

            // Indsæt aftalen i appointment tabellen
            String sql = "INSERT INTO appointment (journey_id, date, type, location) VALUES (?, ?, ?, ?)";

            try {
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, Session.getCurrentJourneyId()); // aktivt forløb
                statement.setString(2, date.toString());            // dato som tekst
                statement.setString(3, type);                       // aftaletype
                statement.setString(4, location);                   // sted
                statement.executeUpdate();
                System.out.println("Appointment saved!");

                // Gem også en hændelse i event tabellen så tidslinjen opdateres
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

        // Henter alle kommende aftaler for det aktive forløb
        // Returnerer en liste af tekstrækker — én per aftale
        public ArrayList<String> getUpcomingAppointments() {
            Connection connection = DatabaseConnection.getConnection();

            // Hent kun aftaler der ikke er gennemført (completed = 0)
            // Sorteret efter dato så den nærmeste aftale kommer først
            String sql = "SELECT * FROM appointment WHERE journey_id = ? AND completed = 0 ORDER BY date ASC";

            ArrayList<String> appointments = new ArrayList<>();

            try {
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, Session.getCurrentJourneyId());
                ResultSet result = statement.executeQuery();

                // Løb igennem alle aftaler og tilføj dem til listen
                while (result.next()) {
                    String date = result.getString("date");
                    String type = result.getString("type");
                    String location = result.getString("location");
                    appointments.add(type + " — " + date + " — " + location);
                }

            } catch (SQLException e) {
                System.out.println("Could not get appointments: " + e.getMessage());
            }

            return appointments;
        }
    }