package model;

import java.time.LocalDate;

public class Patient {

    private String name;
    private LocalDate dateOfBirth;
    private String diagnosis;
    private String username;
    private String password;


    public Patient(String name, LocalDate dateOfBirth, String diagnosis, String username, String password){
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.diagnosis = diagnosis;
        this.username = username;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }


}
