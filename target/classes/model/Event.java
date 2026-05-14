package model;

import enums.EventType;
import java.time.LocalDate;

    // Repræsenterer en hændelse i patientens forløb
    // Bruges til at bygge tidslinjen og give patienten overblik over forløbet
    public class Event {

        // Felter
        private LocalDate date;
        private EventType type;
        private String description;

        // Konstruktør — bruges når vi opretter en ny hændelse
        public Event(LocalDate date, EventType type, String description) {
            this.date = date;
            this.type = type;
            this.description = description;
        }

        // Hent datoen for hændelsen
        public LocalDate getDate() {
            return date;
        }

        // Hent typen af hændelsen
        public EventType getType() {
            return type;
        }

        // Hent beskrivelsen af hændelsen
        public String getDescription() {
            return description;
        }
    }