package controller;

import model.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

    // JourneyTypeController håndterer oprettelse af et nyt forløb.
    // Når patienten vælger en forløbstype gemmes det i journey tabellen
    // med status ACTIVE og dagens dato som startdato.
    public class JourneyTypeController {

        // Opretter et nyt forløb i databasen
        public void handleSelectJourney(int patientId, String type) {

            // Hent databaseforbindelsen
            Connection connection = DatabaseConnection.getConnection();

            // Gem det nye forløb i journey tabellen
            String sql = "INSERT INTO journey (patient_id, type, startDate, status) VALUES (?, ?, ?, ?)";

            try {
                // Gør SQL klar
                PreparedStatement statement = connection.prepareStatement(sql);

                // Udfyld de fire ?
                statement.setInt(1, patientId);                     // hvilken patient
                statement.setString(2, type);                       // forløbstype fx "Fertilitet"
                statement.setString(3, LocalDate.now().toString()); // startdato — i dag
                statement.setString(4, "ACTIVE");                   // status — altid ACTIVE når man starter

                // Gem i databasen
                statement.executeUpdate();
                System.out.println("Journey created!");

            } catch (SQLException e) {
                System.out.println("Could not create journey: " + e.getMessage());
            }
        }
    }