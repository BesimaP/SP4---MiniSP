package controller;

import model.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

    // JourneyTypeController håndterer oprettelse af et nyt forløb
    public class JourneyTypeController {

        // Opretter et nyt forløb i databasen og returnerer det nye journey_id
        // patientId = hvilken patient der opretter forløbet
        // type = fx "Fertility", "Cancer" eller "Other"
        public int handleSelectJourney(int patientId, String type) {

            // Hent forbindelsen til SQLite databasen
            Connection connection = DatabaseConnection.getConnection();

            // SQL der indsætter et nyt forløb i journey tabellen
            String sql = "INSERT INTO journey (patient_id, type, startDate, status) VALUES (?, ?, ?, ?)";

            try {
                // RETURN_GENERATED_KEYS beder databasen om at returnere det nye id
                // Vi skal bruge det til at gemme journey_id i Session
                PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

                // Udfyld de fire ?
                statement.setInt(1, patientId);                     // hvilken patient
                statement.setString(2, type);                       // forløbstype
                statement.setString(3, LocalDate.now().toString()); // startdato — i dag
                statement.setString(4, "ACTIVE");                   // status er altid ACTIVE ved opstart

                // Gem i databasen
                statement.executeUpdate();
                System.out.println("Journey created!");

                // Hent det nye journey_id som databasen genererede
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1); // returner det nye id
                }

            } catch (SQLException e) {
                // Udskriv fejlbesked hvis noget gik galt
                System.out.println("Could not create journey: " + e.getMessage());
            }

            // Returner -1 hvis noget gik galt — bruges til fejlhåndtering
            return -1;
        }
    }