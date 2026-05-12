package model;
//Session klassen holder styr på den bruger der er logget
//Bruger logger ind -> Bruger vælger forløb -> bruges af de enkelte controllers

public class Session {
    private Patient currentPatient; //Gemmer den patient der er logget ind
    private int currentJourneyId; //Gemmer id på den Journey brugeren har valgt

    public void setCurrentPatient(Patient patient){
        this.currentPatient = patient;
    }

    public Patient getCurrentPatient(){
        return currentPatient;
    }

    public void setCurrentJourneyId(int JourneyId) {
        this.currentJourneyId = JourneyId;
    }

    public int getCurrentJourneyId(){
        return currentJourneyId;
    }
}
