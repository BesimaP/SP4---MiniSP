package model;

import enums.Status;
import java.time.LocalDate;

    // Repræsenterer et kræftforløb — ikke implementeret i denne version
    public class CancerJourney extends Journey {

        // Felter
        private String cancerType;
        private String stage;

        // Konstruktør — kalder Journey's konstruktør via super()
        public CancerJourney(LocalDate startDate, Status status, String cancerType, String stage) {
            super(startDate, status);
            this.cancerType = cancerType;
            this.stage = stage;
        }

        // Hent kræfttypen
        public String getCancerType() {
            return cancerType;
        }

        // Hent stadiet
        public String getStage() {
            return stage;
        }
    }