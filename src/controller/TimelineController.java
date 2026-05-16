package controller;

import enums.EventType;
import model.DatabaseConnection;
import model.Event;
import model.Session;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

    // TimelineController henter alle hændelser for det aktive forløb
    public class TimelineController {

        // Henter alle hændelser for det aktive forløb sorteret efter dato
        // Returnerer en liste af Event-objekter — ét per hændelse
        public ArrayList<Event> initialize() {

            // Hent journey_id fra Session — bruges til at finde de rigtige hændelser
            int journeyId = Session.getCurrentJourneyId();

            // Hent forbindelsen til SQLite databasen
            Connection connection = DatabaseConnection.getConnection();

            // Hent alle hændelser sorteret efter dato — ældste først
            String sql = "SELECT * FROM event WHERE journey_id = ? ORDER BY date ASC";

            // Listen der fyldes op og returneres til view
            ArrayList<Event> events = new ArrayList<>();

            try {
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, journeyId); // aktivt forløb

                // Hent resultatet fra databasen
                ResultSet result = statement.executeQuery();

                // Løb igennem alle hændelser og opret et Event-objekt for hver
                while (result.next()) {
                    Event event = new Event(
                            LocalDate.parse(result.getString("date")),  // dato
                            EventType.valueOf(result.getString("type")), // fx STIMULATION
                            result.getString("description")              // beskrivelse
                    );
                    events.add(event);
                }
                System.out.println("Timeline loaded!");

            } catch (SQLException e) {
                // Udskriv fejlbesked hvis noget gik galt
                System.out.println("Could not load timeline: " + e.getMessage());
            }

            // Returner listen — tom hvis ingen hændelser findes
            return events;
        }
    }