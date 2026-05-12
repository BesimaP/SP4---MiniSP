package controller;

import enums.EventType;
import model.DatabaseConnection;
import model.Event;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class TimelineController {

    // Henter alle hændelser for det aktive forløb
    public ArrayList<Event> initialize(int journeyId) {
        Connection connection = DatabaseConnection.getConnection();

        // Hent alle hændelser sorteret efter dato
        String sql = "SELECT * FROM event WHERE journey_id = ? ORDER BY date ASC";

        ArrayList<Event> events = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, journeyId);

            ResultSet result = statement.executeQuery();

            // Løb igennem alle hændelser og tilføj dem til listen
            while (result.next()) {
                Event event = new Event(
                        LocalDate.parse(result.getString("date")),
                        EventType.valueOf(result.getString("type")),
                        result.getString("description")
                );
                events.add(event);
            }
            System.out.println("Timeline loaded!");

        } catch (SQLException e) {
            System.out.println("Could not load timeline: " + e.getMessage());
        }

        return events;
    }
}