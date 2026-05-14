package model;

import java.time.LocalDate;

    // Repræsenterer en dagbogsnote i patientens forløb
    // Giver patienten et privat rum til at skrive tanker, symptomer og refleksioner
    public class DiaryEntry {

        // Felter
        private LocalDate date;
        private String title;
        private String content;

        // Konstruktør — bruges når vi opretter en ny dagbogsnote
        public DiaryEntry(LocalDate date, String title, String content) {
            this.date = date;
            this.title = title;
            this.content = content;
        }

        // Hent datoen for noten
        public LocalDate getDate() {
            return date;
        }

        // Hent titlen på noten
        public String getTitle() {
            return title;
        }

        // Hent indholdet af noten
        public String getContent() {
            return content;
        }
    }