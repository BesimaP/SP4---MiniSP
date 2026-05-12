package controller;

import model.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class ManageProfileController {


    public void createUser(String name, LocalDate dateOfBirth, String diagnosis, String username, String password) {
        Connection connection = DatabaseConnection.getConnection();

        String sql = "INSERT INTO patient ( name , dateOfBirth , diagnosis , userName , password ) VALUES ( ? , ? , ? , ?, ?)";

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

    public void handleEditPatient(){

    }

    public void handleDeletePatient(){

    }
}
