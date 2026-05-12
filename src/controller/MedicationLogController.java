package controller;

import model.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

// Håndterer tilføjelse og gemning af medicinindtastninger

public class MedicationLogController {

    // Køres når brugeren klikker Tilføj Medicin
    public void handleAddMedication() {

    }


    // Køres når brugeren klikker Gem
    public void handleSave(LocalDate date, String medication, String dose, boolean taken) {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "INSERT INTO medicationLog (date, hormone, value, unit) VALUES (?, ?, ?, ?)";

        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, date.toString());
            statement.setString(2, medication);
            statement.setString(3, dose);
            statement.setBoolean(4, taken);
            statement.executeUpdate();
            System.out.println("The values have been saved!");
        }catch (SQLException e){
            System.out.println("The values could not be saved: "+ e.getMessage());
        }

    }

    }


