package model;
import java.time.LocalDate;

public class Event {
    private LocalDate date;
    private EventType type;
    private String description;

    //Constructor
    public Event(LocalDate date, EventType type, String description){
        this.date = date;
        this.type = type;
        this.description = description;
    }
    //Brug af Event-objekt: Event myEvent = new Event(LocalDate.now(), EventType.CONSULTATION, "Første konsultation");

    //Getters
    public LocalDate getDate(){
        return date;
    }

    public EventType getType() {
        return type;
    }
    public String getDescription(){
        return description;
    }
}
