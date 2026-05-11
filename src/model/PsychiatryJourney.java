package model;
import enums.Status;

import java.time.LocalDate;

public class PsychiatryJourney extends Journey{
    private String condition;

    public PsychiatryJourney(LocalDate startDate, Status status, String condition){
        super(startDate, status);
        this.condition = condition;
    }

    //Getters
    public String getCondition(){
        return condition;
    }
}
