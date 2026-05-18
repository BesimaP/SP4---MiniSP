package model;

import enums.Result;
import enums.Status;
import java.time.LocalDate;

    // Repræsenterer et fertilitetforløb — det primære forløb i systemet
    public class FertilityJourney extends Journey {

        // Felter
        private int roundNumber;
        private int eggsRetrieved;
        private int eggsFertilised;
        private Result result;

        // Konstruktør — kalder Journey's konstruktør via super()
        public FertilityJourney(LocalDate startDate, Status status, int roundNumber, int eggsRetrieved, int eggsFertilised, Result result) {
            super(startDate, status);
            this.roundNumber = roundNumber;
            this.eggsRetrieved = eggsRetrieved;
            this.eggsFertilised = eggsFertilised;
            this.result = result;
        }

        // Opdater resultatet af runden
        public void setResult(Result result) {
            this.result = result;
        }

        // Hent rundenummeret
        public int getRoundNumber() {
            return roundNumber;
        }

        // Hent resultatet af runden
        public Result getResult() {
            return result;
        }
    }