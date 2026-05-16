package model;

    // Session klassen holder styr på den bruger der er logget ind
    // Den deles mellem alle controllers så de ved hvilken patient og forløb der er aktivt
    // Flowet er: Bruger logger ind → Bruger vælger forløb → Controllers bruger Session

    public class Session {

        // Gemmer den patient der er logget ind
        // static betyder at den deles på tværs af hele programmet
        private static Patient currentPatient;

        // Gemmer id på det forløb patienten har valgt
        // Bruges af alle controllers til at hente og gemme data til det rigtige forløb
        private static int currentJourneyId;

        // Gem den aktive patient — kaldes når brugeren logger ind
        public static void setCurrentPatient(Patient patient) {
            currentPatient = patient;
        }

        // Hent den aktive patient — bruges i alle views og controllers
        public static Patient getCurrentPatient() {
            return currentPatient;
        }

        // Gem det aktive journey_id — kaldes når brugeren vælger forløbstype
        public static void setCurrentJourneyId(int journeyId) {
            currentJourneyId = journeyId;
        }

        // Hent det aktive journey_id — bruges i alle controllers til database-kald
        public static int getCurrentJourneyId() {
            return currentJourneyId;
        }
    }
