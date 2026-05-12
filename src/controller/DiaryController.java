package controller;

import model.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class DiaryController {
    public void handleAddNote(){

    }

    public void handleSave(int journey_id, LocalDate date, String title, String content){
        Connection connection = DatabaseConnection.getConnection();

        String sql = "INSERT INTO diary_entry (journey_id, date , title , content ) VALUES (? , ? , ?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, journey_id);
            statement.setString(2, date.toString());
            statement.setString(3, title);
            statement.setString(4, content);
            statement.executeUpdate();
            System.out.println("Diary entry is successful ");
        } catch (SQLException e) {
            System.out.println("Could not create diary entry " + e.getMessage());
        }
    };
}
