package controller;

import model.DatabaseConnection;
import model.Session;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

    // MedicationLogController håndterer tilføjelse og gemning af medicinindtastninger
    public class MedicationLogController {

        // Gemmer en medicinindtastning i databasen
        public void handleSave(LocalDate date, String medication, String dose, boolean taken) {

            // Hent forbindelsen til SQLite databasen
            Connection connection = DatabaseConnection.getConnection();

            // SQL der indsætter en ny medicinindtastning i medication_log tabellen
            String sql = "INSERT INTO medication_log (journey_id, date, medication, dose, taken) VALUES (?, ?, ?, ?, ?)";

            try {
                // Gør SQL klar med PreparedStatement
                PreparedStatement statement = connection.prepareStatement(sql);

                // Udfyld de fem ?
                statement.setInt(1, Session.getCurrentJourneyId()); // aktivt forløb
                statement.setString(2, date.toString());            // dato som tekst
                statement.setString(3, medication);                 // medicinens navn fx Gonal-F
                statement.setString(4, dose);                       // dosis fx 150 IU
                statement.setBoolean(5, taken);                     // true hvis medicinen er taget

                // Gem i databasen
                statement.executeUpdate();
                System.out.println("Medication saved!");

                // Gem også en hændelse i event tabellen så tidslinjen opdateres
                String eventSql = "INSERT INTO event (journey_id, date, type, description) VALUES (?, ?, ?, ?)";
                PreparedStatement eventStatement = connection.prepareStatement(eventSql);
                eventStatement.setInt(1, Session.getCurrentJourneyId());
                eventStatement.setString(2, date.toString());
                eventStatement.setString(3, "OTHER");
                eventStatement.setString(4, "Medication: " + medication + " " + dose);
                eventStatement.executeUpdate();
                System.out.println("Event saved!");

            } catch (SQLException e) {
                // Udskriv fejlbesked hvis noget gik galt
                System.out.println("Could not save medication: " + e.getMessage());
            }
        }
    }