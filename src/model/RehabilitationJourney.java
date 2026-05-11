package model;
import java.time.LocalDate;

public class RehabilitationJourney extends Journey{
    private String injuryType;
    private String goal;

    public RehabilitationJourney(LocalDate startDate, Status status, String injuryType, String goal){
        super(startDate, status);
        this.injuryType = injuryType;
        this.goal = goal;
    }

    public String getInjuryType(){
        return injuryType;
    }
    public String getGoal(){
        return goal;
    }
}
