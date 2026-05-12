package controller;

import model.DatabaseConnection;
import model.Patient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class StartSystemController {

    //Login hvis bruger allerede er oprettet:

    public Patient handleLogin(String userName, String passWord){
        Connection connection = DatabaseConnection.getConnection();
    String sql = "SELECT * FROM patient WHERE username = ? AND password = ?";

    try {
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, userName);
        statement.setString(2, passWord);
        ResultSet result = statement.executeQuery();

        if(result.next()){
            System.out.println("Login succesfull!");
            return new Patient(
                    result.getString("name"),
                    LocalDate.parse(result.getString("dateOfBirth")),
                    result.getString("diagnosis"),
                    result.getString("username"),
                    result.getString("password")
            );
        }else{
            System.out.println("Wrong username or password");
            return null;
        }
    }catch (SQLException e){
        System.out.println("FAIL: "+ e.getMessage());
        return null;
    }
}
}
