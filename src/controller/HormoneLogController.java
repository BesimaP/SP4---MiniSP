package controller;

import model.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

// Håndterer tilføjelse og gemning af hormonværdier
public class HormoneLogController {

    // Køres når brugeren klikker Tilføj Værdi
    public void handleAddValue(LocalDate date, String hormone, double value, String unit) {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "INSERT INTO patient (date, hormone, value, unit) VALUES (?, ?, ?, ?)";

        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, date.toString());
            statement.setString(2, hormone);
            statement.setDouble(3, value);
            statement.setString(4, unit);
            statement.executeUpdate();
            System.out.println("Values are added");
        }catch(SQLException e){
            System.out.println("Values could not be added: "+ e.getMessage());
        }
    }

    // Køres når brugeren klikker Gem
    public void handleSave(LocalDate date, String hormone, double value, String unit) {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "INSERT INTO patient (date, hormone, value, unit) VALUES (?, ?, ?, ?)";

        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, date.toString());
            statement.setString(2, hormone);
            statement.setDouble(3, value);
            statement.setString(4, unit);
            statement.executeUpdate();
            System.out.println("The values are saved");
        }catch (SQLException e){
            System.out.println("The values could not be saved: "+ e.getMessage());
        }
    }
}