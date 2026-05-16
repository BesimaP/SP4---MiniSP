package controller;

import model.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

// ManageProfileController håndterer oprettelse, redigering og sletning af patientprofil
public class ManageProfileController {

    // Opretter en ny patient i databasen
    // Kaldes fra ProfileView når brugeren klikker Save
    public void handleCreatePatient(String name, LocalDate dateOfBirth, String diagnosis, String username, String password) {

        // Hent forbindelsen til SQLite databasen
        Connection connection = DatabaseConnection.getConnection();

        // SQL der indsætter en ny patient i patient tabellen
        String sql = "INSERT INTO patient (name, dateOfBirth, diagnosis, username, password) VALUES (?, ?, ?, ?, ?)";

        try {
            // Gør SQL klar med PreparedStatement
            PreparedStatement statement = connection.prepareStatement(sql);

            // Udfyld de fem ?
            statement.setString(1, name);                   // patientens navn
            statement.setString(2, dateOfBirth.toString()); // LocalDate konverteres til tekst
            statement.setString(3, diagnosis);              // diagnose fx PCOS
            statement.setString(4, username);               // brugernavn — UNIQUE i databasen
            statement.setString(5, password);               // adgangskode

            // Gem i databasen
            statement.executeUpdate();
            System.out.println("User is created!");

        } catch (SQLException e) {
            System.out.println("Could not create user: " + e.getMessage());
        }
    }

    // Redigerer en eksisterende patients oplysninger
    // Bemærk: username og password kan ikke ændres her
    public void handleEditPatient(int id, String name, LocalDate dateOfBirth, String diagnosis) {

        // Hent forbindelsen til SQLite databasen
        Connection connection = DatabaseConnection.getConnection();

        // SQL der opdaterer navn, fødselsdato og diagnose for den valgte patient
        String sql = "UPDATE patient SET name = ?, dateOfBirth = ?, diagnosis = ? WHERE id = ?";

        try {
            // Gør SQL klar med PreparedStatement
            PreparedStatement statement = connection.prepareStatement(sql);

            // Udfyld de fire ?
            statement.setString(1, name);
            statement.setString(2, dateOfBirth.toString());
            statement.setString(3, diagnosis);
            statement.setInt(4, id); // WHERE id = ? — hvilken patient der opdateres

            // Gem ændringerne i databasen
            statement.executeUpdate();
            System.out.println("Patient updated!");

        } catch (SQLException e) {
            System.out.println("Could not update patient: " + e.getMessage());
        }
    }

    // Sletter en patient fra databasen
    // Kaldes med patientens id så kun den rigtige patient slettes
    public void handleDeletePatient(int id) {

        // Hent forbindelsen til SQLite databasen
        Connection connection = DatabaseConnection.getConnection();

        // SQL der sletter patienten med det givne id
        String sql = "DELETE FROM patient WHERE id = ?";

        try {
            // Gør SQL klar med PreparedStatement
            PreparedStatement statement = connection.prepareStatement(sql);

            // Udfyld ? med patientens id
            statement.setInt(1, id);

            // Slet fra databasen
            statement.executeUpdate();
            System.out.println("Patient deleted!");

        } catch (SQLException e) {
            System.out.println("Could not delete patient: " + e.getMessage());
        }
    }
}