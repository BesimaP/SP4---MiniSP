package model;

    public class FertilityJourney extends Journey{
        private int roundNumber;
        private int eggsRetrieved;
        private int eggsFertilised;
        private Result result;

        public FertilityJourney(int roundNumber, int eggsRetrieved, int eggsFertilised, Result result){
            this.roundNumber = roundNumber;
            this.eggsRetrieved = eggsRetrieved;
            this.eggsFertilised = eggsFertilised;
            this.result = result;
        }

        public void setResult(Result result){
            this.result = result;
        }
        public int getRoundNumber(){
            return roundNumber;
        }

        public int getEggsRetrieved(){
            return eggsRetrieved;
        }

        public int getEggsFertilised(){
            return eggsFertilised;
        }

        public Result getResult(){
            return result;
        }
    }