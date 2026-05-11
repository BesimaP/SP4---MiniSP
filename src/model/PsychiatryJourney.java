package model;
import jdk.internal.org.jline.utils.Status;
import java.time.LocalDate;

public class PsychiatryJourney extends Journey{
    private String injuryType;
    private String goal;

    public PsychiatryJourney(LocalDate StartDate, Status status, String injuryType, String goal){
        super(StartDate, status);
        this.injuryType = injuryType;
        this.goal = goal;
    }

    //Getters
    public String getInjuryType(){
        return injuryType;
    }
    public String getGoal(){
        return goal;
    }
}
