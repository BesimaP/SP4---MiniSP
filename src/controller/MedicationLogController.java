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
        // Kald handleSave med de indtastede oplysninger
        handleSave(date, medication, dose, taken);
    }

    // Gemmer en medicinindtastning i databasen
    public void handleSave(LocalDate date, String medication, String dose, boolean taken) {
        int journeyId = Session.getCurrentJourneyId(); //hentes fra session
        // Hent databaseforbindelsen
        Connection connection = DatabaseConnection.getConnection();

        // Gem medicinindtastningen i medication_log tabellen
        String sql = "INSERT INTO medication_log (journey_id, date, medication, dose, taken) VALUES (?, ?, ?, ?, ?)";

        try {
            // Gør SQL klar
            PreparedStatement statement = connection.prepareStatement(sql);

            // Udfyld de fem ?
            statement.setInt(1, journeyId);           // hvilket forløb
            statement.setString(2, date.toString());  // dato
            statement.setString(3, medication);        // medicinens navn
            statement.setString(4, dose);              // dosis
            statement.setBoolean(5, taken);            // om medicinen er taget

            // Gem i databasen
            statement.executeUpdate();
            System.out.println("Medication saved!");

        } catch (SQLException e) {
            System.out.println("Could not save medication: " + e.getMessage());
        }
    }
}