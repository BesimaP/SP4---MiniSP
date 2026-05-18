package model;

import java.time.LocalDate;

    // Repræsenterer en hormonmåling i et fertilitetforløb
    public class HormoneLog {

        // Felter
        private LocalDate date;
        private String hormone;
        private double value;
        private String unit;

        // Konstruktør — bruges når vi opretter en ny hormonmåling
        public HormoneLog(LocalDate date, String hormone, double value, String unit) {
            this.date = date;
            this.hormone = hormone;
            this.value = value;
            this.unit = unit;
        }

        // Hent datoen for målingen
        public LocalDate getDate() {
            return date;
        }

        // Hent hormontypen
        public String getHormone() {
            return hormone;
        }

        // Hent værdien af målingen
        public double getValue() {
            return value;
        }
    }
