package model;

import enums.Status;
import java.time.LocalDate;

    // Repræsenterer et andet forløb — ikke implementeret i denne version
    public class OtherJourney extends Journey {

        // Felter
        private String description;

        // Konstruktør — kalder Journey's konstruktør via super()
        public OtherJourney(LocalDate startDate, Status status, String description) {
            super(startDate, status);
            this.description = description;
        }

        // Hent beskrivelsen af forløbet
        public String getDescription() {
            return description;
        }
    }