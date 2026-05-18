package model;

import enums.AppointmentType;
import java.time.LocalDate;

    // Repræsenterer en aftale i patientens forløb
    public class Appointment {

        // Felter
        private LocalDate date;
        private AppointmentType type;
        private String location;

        // Konstruktør — bruges når vi opretter en ny aftale
        public Appointment(LocalDate date, AppointmentType type, String location) {
            this.date = date;
            this.type = type;
            this.location = location;
        }

        // Hent datoen for aftalen
        public LocalDate getDate() {
            return date;
        }

        // Hent typen af aftalen
        public AppointmentType getType() {
            return type;
        }
    }