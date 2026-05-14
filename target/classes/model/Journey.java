package model;

import enums.Status;
import java.time.LocalDate;

    // Abstrakt klasse der repræsenterer et patientforløb
    // Kan ikke oprettes direkte — kun via FertilityJourney, CancerJourney osv.
    public abstract class Journey {

        // Felter
        private LocalDate startDate;
        private Status status;

        // Konstruktør — kaldes fra subklasserne via super()
        public Journey(LocalDate startDate, Status status) {
            this.startDate = startDate;
            this.status = status;
        }

        // Opdater status på forløbet
        public void setStatus(Status status) {
            this.status = status;
        }

        // Hent startdatoen for forløbet
        public LocalDate getStartDate() {
            return startDate;
        }

        // Hent status på forløbet
        public Status getStatus() {
            return status;
        }
    }