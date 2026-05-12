package controller;

import model.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class JourneyTypeController {

    public void handleSelectJourney(int patientId, String type){
        Connection connection = DatabaseConnection.getConnection();

        String sql = "INSERT INTO journey(patient_id, type, startDate,status) VALUES (?, ?, ?, ?)";

        try{
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, patientId);        // patient_id
            statement.setString(2, type);          // forløbstype fx "Fertilitet"
            statement.setString(3, LocalDate.now().toString()); // startdato — i dag
            statement.setString(4, "ACTIVE");      // status — altid ACTIVE når man starter

            statement.executeUpdate();
            System.out.println("Journey created!");
        } catch (SQLException e) {
            System.out.println("Could not create journey");
        }

    }
}
