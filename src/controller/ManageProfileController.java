package controller;

import model.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class ManageProfileController {


    public void handleCreatePatient(String name, LocalDate dateOfBirth, String diagnosis, String username, String password) {
        Connection connection = DatabaseConnection.getConnection();

        String sql = "INSERT INTO patient ( name , dateOfBirth , diagnosis , username , password ) VALUES ( ? , ? , ? , ?, ?)";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            statement.setString(2, dateOfBirth.toString());
            statement.setString(3, diagnosis);
            statement.setString(4, username);
            statement.setString(5, password);
            statement.executeUpdate();
            System.out.println("User is created ");
        } catch (SQLException e) {
            System.out.println("Could not create user " + e.getMessage());
        }
    }

    public void handleEditPatient(int id, String name, LocalDate dateOfBirth, String diagnosis){
        Connection connection = DatabaseConnection.getConnection();

        String sql = "UPDATE patient SET name = ?, dateOfBirth = ?, diagnosis = ? WHERE id = ?";

        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            statement.setString(2, dateOfBirth.toString());
            statement.setString(3, diagnosis);
            statement.setInt(4, id);
            statement.executeUpdate();
            System.out.println("Patient updated!");
        } catch (SQLException e) {
            System.out.println("Could not update patient: " + e.getMessage());
        }
    }

    public void handleDeletePatient(int id){
        Connection connection = DatabaseConnection.getConnection();

        String sql = "DELETE FROM patient where id = ?";

        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1,id);
            statement.executeUpdate();
            System.out.println("Patient deleted!");
        } catch (SQLException e){
            System.out.println("Could not delete patient: " + e.getMessage());
        }
    }
}
