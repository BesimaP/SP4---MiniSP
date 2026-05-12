package model;

import enums.Status;
import java.time.LocalDate;

    // Repræsenterer et psykiatrisk forløb — ikke implementeret i denne version
    public class PsychiatryJourney extends Journey {

        // Felter
        private String condition;

        // Konstruktør — kalder Journey's konstruktør via super()
        public PsychiatryJourney(LocalDate startDate, Status status, String condition) {
            super(startDate, status);
            this.condition = condition;
        }

        // Hent patientens psykiatriske tilstand
        public String getCondition() {
            return condition;
        }
    }