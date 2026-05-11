package model;

import java.time.LocalDate;

    // Repræsenterer en medicinindtastning i et forløb
    public class MedicationLog {

        // Felter
        private LocalDate date;
        private String medication;
        private String dose;
        private boolean taken;

        // Konstruktør — bruges når vi opretter en ny medicinindtastning
        public MedicationLog(LocalDate date, String medication, String dose, boolean taken) {
            this.date = date;
            this.medication = medication;
            this.dose = dose;
            this.taken = taken;
        }

        // Sæt om medicinen er taget
        public void setTaken(boolean taken) {
            this.taken = taken;
        }

        // Hent datoen for medicinindtastningen
        public LocalDate getDate() {
            return date;
        }

        // Hent medicinens navn
        public String getMedication() {
            return medication;
        }

        // Hent dosis
        public String getDose() {
            return dose;
        }

        // Hent om medicinen er taget
        public boolean isTaken() {
            return taken;
        }
    }