package controller;

import model.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

// ManageProfileController håndterer oprettelse, redigering og sletning af patientprofil
public class ManageProfileController {

    // Opretter en ny patient i databasen
    public void handleCreatePatient(String name, LocalDate dateOfBirth, String diagnosis, String username, String password) {

        // Hent databaseforbindelsen
        Connection connection = DatabaseConnection.getConnection();

        // Gem den nye patient i patient tabellen
        String sql = "INSERT INTO patient (name, dateOfBirth, diagnosis, username, password) VALUES (?, ?, ?, ?, ?)";

        try {
            // Gør SQL klar
            PreparedStatement statement = connection.prepareStatement(sql);

            // Udfyld de fem ?
            statement.setString(1, name);
            statement.setString(2, dateOfBirth.toString());
            statement.setString(3, diagnosis);
            statement.setString(4, username);
            statement.setString(5, password);

            // Gem i databasen
            statement.executeUpdate();
            System.out.println("User is created!");
        } catch (SQLException e) {
            System.out.println("Could not create user: " + e.getMessage());
        }
    }

    // Redigerer en eksisterende patients oplysninger
    public void handleEditPatient(int id, String name, LocalDate dateOfBirth, String diagnosis) {

        // Hent databaseforbindelsen
        Connection connection = DatabaseConnection.getConnection();

        // Opdater patientens oplysninger
        String sql = "UPDATE patient SET name = ?, dateOfBirth = ?, diagnosis = ? WHERE id = ?";

        try {
            // Gør SQL klar
            PreparedStatement statement = connection.prepareStatement(sql);

            // Udfyld de fire ?
            statement.setString(1, name);
            statement.setString(2, dateOfBirth.toString());
            statement.setString(3, diagnosis);
            statement.setInt(4, id);

            // Gem i databasen
            statement.executeUpdate();
            System.out.println("Patient updated!");
        } catch (SQLException e) {
            System.out.println("Could not update patient: " + e.getMessage());
        }
    }

    // Sletter en patient fra databasen
    public void handleDeletePatient(int id) {

        // Hent databaseforbindelsen
        Connection connection = DatabaseConnection.getConnection();

        // Slet patienten med det givne id
        String sql = "DELETE FROM patient WHERE id = ?";

        try {
            // Gør SQL klar
            PreparedStatement statement = connection.prepareStatement(sql);

            // Udfyld ?
            statement.setInt(1, id);

            // Slet fra databasen
            statement.executeUpdate();
            System.out.println("Patient deleted!");
        } catch (SQLException e) {
            System.out.println("Could not delete patient: " + e.getMessage());
        }
    }
}