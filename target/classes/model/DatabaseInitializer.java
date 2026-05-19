package model;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

// DatabaseInitializer opretter alle tabeller i SQLite-databasen når programmet starter.
// Klassen bruger CREATE TABLE IF NOT EXISTS — det betyder at tabellerne kun oprettes
// hvis de ikke allerede eksisterer. Så kan programmet startes flere gange
// uden at tabellerne bliver slettet eller oprettet igen.
// Tabellerne afspejler systemets domænemodel med patient, journey, fertility_journey,
// appointment, medication_log, hormone_log, diary_entry og event.
// ON DELETE CASCADE sikrer at relaterede rækker slettes automatisk
// når en patient eller et forløb slettes — så vi ikke får forældreløse data.
public class DatabaseInitializer {

    // Opretter alle tabeller i databasen
    public static void initialize() {
        // Hent forbindelsen til databasen
        Connection connection = DatabaseConnection.getConnection();

        try {
            // Opret et statement til at køre SQL
            Statement statement = connection.createStatement();

            // Opret patient tabellen
            statement.execute("CREATE TABLE IF NOT EXISTS patient (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name TEXT NOT NULL," +
                    "dateOfBirth TEXT NOT NULL," +
                    "diagnosis TEXT," +
                    "username TEXT NOT NULL UNIQUE," +
                    "password TEXT NOT NULL)");

            // Opret journey tabellen
            statement.execute("CREATE TABLE IF NOT EXISTS journey (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "patient_id INTEGER NOT NULL," +
                    "type TEXT NOT NULL," +
                    "startDate TEXT NOT NULL," +
                    "status TEXT NOT NULL," +
                    "FOREIGN KEY (patient_id) REFERENCES patient(id) ON DELETE CASCADE)");

            // Opret fertility_journey tabellen
            statement.execute("CREATE TABLE IF NOT EXISTS fertility_journey (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "journey_id INTEGER NOT NULL," +
                    "roundNumber INTEGER NOT NULL," +
                    "eggsRetrieved INTEGER," +
                    "eggsFertilised INTEGER," +
                    "result TEXT," +
                    "FOREIGN KEY (journey_id) REFERENCES journey(id) ON DELETE CASCADE)");

            // Opret appointment tabellen
            statement.execute("CREATE TABLE IF NOT EXISTS appointment (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "journey_id INTEGER NOT NULL," +
                    "date TEXT NOT NULL," +
                    "type TEXT NOT NULL," +
                    "location TEXT," +
                    "completed INTEGER DEFAULT 0," +
                    "FOREIGN KEY (journey_id) REFERENCES journey(id) ON DELETE CASCADE)");

            // Opret medication_log tabellen
            statement.execute("CREATE TABLE IF NOT EXISTS medication_log (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "journey_id INTEGER NOT NULL," +
                    "date TEXT NOT NULL," +
                    "medication TEXT NOT NULL," +
                    "dose TEXT NOT NULL," +
                    "taken INTEGER DEFAULT 0," +
                    "FOREIGN KEY (journey_id) REFERENCES journey(id) ON DELETE CASCADE)");

            // Opret hormone_log tabellen
            statement.execute("CREATE TABLE IF NOT EXISTS hormone_log (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "journey_id INTEGER NOT NULL," +
                    "date TEXT NOT NULL," +
                    "hormone TEXT NOT NULL," +
                    "value REAL NOT NULL," +
                    "unit TEXT NOT NULL," +
                    "FOREIGN KEY (journey_id) REFERENCES journey(id) ON DELETE CASCADE)");

            // Opret diary_entry tabellen
            statement.execute("CREATE TABLE IF NOT EXISTS diary_entry (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "journey_id INTEGER NOT NULL," +
                    "date TEXT NOT NULL," +
                    "title TEXT NOT NULL," +
                    "content TEXT," +
                    "FOREIGN KEY (journey_id) REFERENCES journey(id) ON DELETE CASCADE)");

            // Opret event tabellen
            statement.execute("CREATE TABLE IF NOT EXISTS event (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "journey_id INTEGER NOT NULL," +
                    "date TEXT NOT NULL," +
                    "type TEXT NOT NULL," +
                    "description TEXT," +
                    "FOREIGN KEY (journey_id) REFERENCES journey(id) ON DELETE CASCADE)");

            System.out.println("Database initialized!");

        } catch (SQLException e) {
            // Noget gik galt under oprettelsen af tabellerne
            System.out.println("Could not initialize database: " + e.getMessage());
        }
    }
}