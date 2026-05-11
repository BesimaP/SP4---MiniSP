package model;

import enums.AppointmentType;

import java.time.LocalDate;

    public class Appointment {
        private LocalDate date;
        private AppointmentType type;
        private String location;

        public Appointment(LocalDate date, AppointmentType type, String location){
            this.date = date;
            this.type = type;
            this.location = location;
        }

        public LocalDate getDate(){
            return date;
        }

        public AppointmentType getType(){
            return type;
        }

        public String getLocation(){
            return location;
        }
    }
