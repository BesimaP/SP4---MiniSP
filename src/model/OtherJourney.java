package model;
import java.time.LocalDate;

public class OtherJourney extends Journey{
    private String description;

    public OtherJourney(LocalDate startDate, Status status, String description){
        super(startDate, status);
        this.description = description;
    }

    public String getDescription(){
        return description;
    }
}
