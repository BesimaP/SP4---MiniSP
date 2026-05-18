package controller;

import model.DatabaseConnection;
import model.Session;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

    // HormoneLogController håndterer gemning og hentning af hormonværdier
    public class HormoneLogController {

        // Gemmer en hormonværdi i databasen
        public void handleSave(LocalDate date, String hormone, double value, String unit) {

            // Hent forbindelsen til SQLite databasen
            Connection connection = DatabaseConnection.getConnection();

            // SQL der indsætter en ny hormonværdi i hormone_log tabellen
            String sql = "INSERT INTO hormone_log (journey_id, date, hormone, value, unit) VALUES (?, ?, ?, ?, ?)";

            try {
                // Gør SQL klar med PreparedStatement
                PreparedStatement statement = connection.prepareStatement(sql);

                // Udfyld de fem ?
                statement.setInt(1, Session.getCurrentJourneyId()); // aktivt forløb
                statement.setString(2, date.toString());            // dato som tekst
                statement.setString(3, hormone);                    // hormontype fx Oestradiol
                statement.setDouble(4, value);                      // målt værdi fx 450.0
                statement.setString(5, unit);                       // enhed fx pmol/L

                // Gem i databasen
                statement.executeUpdate();
                System.out.println("Hormone value saved!");

                // Gem også en hændelse i event tabellen så tidslinjen opdateres
                String eventSql = "INSERT INTO event (journey_id, date, type, description) VALUES (?, ?, ?, ?)";
                PreparedStatement eventStatement = connection.prepareStatement(eventSql);
                eventStatement.setInt(1, Session.getCurrentJourneyId());
                eventStatement.setString(2, date.toString());
                eventStatement.setString(3, "STIMULATION");
                eventStatement.setString(4, hormone + ": " + value + " " + unit);
                eventStatement.executeUpdate();
                System.out.println("Event saved!");

            } catch (SQLException e) {
                // Udskriv fejlbesked hvis noget gik galt
                System.out.println("Could not save hormone value: " + e.getMessage());
            }
        }

        // Henter den seneste hormonværdi for det aktive forløb
        // Bruges til at vise statistik på dashboardet
        public String getLatestHormoneValue() {

            // Hent forbindelsen til SQLite databasen
            Connection connection = DatabaseConnection.getConnection();

            // ORDER BY date DESC sorterer nyeste dato først
            // LIMIT 1 henter kun den allerseneste række
            String sql = "SELECT hormone, value, unit FROM hormone_log WHERE journey_id = ? ORDER BY date DESC LIMIT 1";

            try {
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, Session.getCurrentJourneyId());
                ResultSet result = statement.executeQuery();

                // Hvis der findes en hormonværdi — returner den som tekst
                if (result.next()) {
                    // Returner fx "Oestradiol: 450.0 pmol/L"
                    return result.getString("hormone") + ": " + result.getDouble("value") + " " + result.getString("unit");
                }
            } catch (SQLException e) {
                System.out.println("Could not get hormone value: " + e.getMessage());
            }

            // Returner — hvis ingen hormonværdi er logget endnu
            return "-";
        }
    }