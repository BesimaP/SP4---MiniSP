package model;

import enums.Status;
import java.time.LocalDate;

    // Repræsenterer et genoptræningsforløb — ikke implementeret i denne version
    public class RehabilitationJourney extends Journey {

        // Felter
        private String injuryType;
        private String goal;

        // Konstruktør — kalder Journey's konstruktør via super()
        public RehabilitationJourney(LocalDate startDate, Status status, String injuryType, String goal) {
            super(startDate, status);
            this.injuryType = injuryType;
            this.goal = goal;
        }

        // Hent skadetypen
        public String getInjuryType() {
            return injuryType;
        }

        // Hent målet for genoptræningen
        public String getGoal() {
            return goal;
        }
    }