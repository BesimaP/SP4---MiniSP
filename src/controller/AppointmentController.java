package controller;

import model.DatabaseConnection;
import model.Session;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class AppointmentController {
        // Køres når brugeren klikker Tilføj Aftale
        public void handleAddAppointment(LocalDate date, String type, String location){
            handleSave(date, type, location);
        }

        //Muligt alternativ til koden herover



        // Køres når brugeren markerer en aftale som gennemført
        public void handleMarkCompleted(int appointmentId) {
            Connection connection = DatabaseConnection.getConnection();

            String sql = "UPDATE appointment SET completed = 1 WHERE id = ?";

            try{
                PreparedStatement statement = connection.prepareStatement(sql);

                statement.setInt(1,appointmentId);

                statement.executeUpdate();
                System.out.println("Appointment completed!");

            } catch (SQLException e) {
                System.out.println("Could not update appointment: " + e.getMessage());
            }

        }

        // Køres når brugeren klikker Gem
        public void handleSave(LocalDate date, String type, String location) {
            int journeyId = Session.getCurrentJourneyId();
            Connection connection = DatabaseConnection.getConnection();

            String sql = "INSERT INTO appointment (journey_id, date, type, location) VALUES (?, ?, ?, ?)";

            try{
                PreparedStatement statement = connection.prepareStatement(sql);

                statement.setInt(1, journeyId);
                statement.setString(2,date.toString()); // LocalDate -> String til databasen
                statement.setString(3,type);
                statement.setString(4, location);

                statement.executeUpdate();
                System.out.println("Appointment saved!");
            } catch (SQLException e){
                System.out.println("Could not save appointment: " + e.getMessage());
            }
        }
    }