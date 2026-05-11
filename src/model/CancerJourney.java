package model;
import java.time.LocalDate;

public class CancerJourney extends Journey {
    private String cancerType;
    private String stage;

    public CancerJourney(LocalDate startDate, Status status, String cancerType, String stage){
        super(startDate, status); // kalder Journeys constructor
        this.cancerType = cancerType;
        this.stage = stage;

    }

    public String getCancerType(){
        return cancerType;
    }
    public String getStage(){
        return stage;
    }
}
