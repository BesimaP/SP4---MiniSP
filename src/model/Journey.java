package model;

import enums.Status;

import java.time.LocalDate;

abstract public class Journey {

    private LocalDate startDate;
    private Status status;

    public Journey(LocalDate startDate, Status status) {
        this.startDate = startDate;
        this.status = status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public Status getStatus() {
        return status;
    }
}


