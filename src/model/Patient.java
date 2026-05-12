package model;

import java.time.LocalDate;

    // Repræsenterer en patient i systemet
    public class Patient {

        // Felter
        private String name;
        private LocalDate dateOfBirth;
        private String diagnosis;
        private String username;
        private String password;

        // Konstruktør — bruges når vi opretter en ny patient
        public Patient(String name, LocalDate dateOfBirth, String diagnosis, String username, String password) {
            this.name = name;
            this.dateOfBirth = dateOfBirth;
            this.diagnosis = diagnosis;
            this.username = username;
            this.password = password;
        }

        // Hent patientens navn
        public String getName() {
            return name;
        }

        // Hent patientens fødselsdato
        public LocalDate getDateOfBirth() {
            return dateOfBirth;
        }

        // Hent patientens diagnose
        public String getDiagnosis() {
            return diagnosis;
        }

        // Hent patientens brugernavn
        public String getUsername() {
            return username;
        }

        // Hent patientens adgangskode
        public String getPassword() {
            return password;
        }
    }