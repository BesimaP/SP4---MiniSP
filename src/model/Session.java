package model;
//Session klassen holder styr på den bruger der er logget
//Bruger logger ind -> Bruger vælger forløb -> bruges af de enkelte controllers

public class Session {
    private static Patient currentPatient; //Gemmer den patient der er logget ind
    private static int currentJourneyId; //Gemmer id på den Journey brugeren har valgt

    public static void setCurrentPatient(Patient patient){
        currentPatient = patient;
    }

    public static Patient getCurrentPatient(){
        return currentPatient;
    }

    public static void setCurrentJourneyId(int JourneyId) {
        currentJourneyId = JourneyId;
    }

    public static int getCurrentJourneyId(){
        return currentJourneyId;
    }
}
