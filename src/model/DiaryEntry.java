package model;

import java.time.LocalDate;

public class DiaryEntry {
    private LocalDate date;
    private String title;
    private String content;

    //Constructor
    public DiaryEntry(LocalDate date, String title, String content){
        this.date = date;
        this.title = title;
        this.content = content;
    }
    //Brug af DiaryEntry-objekt: DiaryEntry myEntry = new DiaryEntry(LocalDate.now(), "Min overskrift", "Indhold i min entry");


    //Getters
    public LocalDate getDate(){
        return date;
    }
    public String getTitle(){
        return title;
    }
    public String getContent(){
        return content;
    }
}
