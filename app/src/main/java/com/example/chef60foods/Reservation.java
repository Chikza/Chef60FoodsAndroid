package com.example.chef60foods;

public class Reservation {

    private String reserverName;
    private String numberOfPersons;
    private String numberOfTables;
    private String reservationDate;
    private String reservationTime;

    public Reservation() {
        // Default constructor required for Firebase
    }

    public Reservation(String reserverName, String numberOfPersons, String numberOfTables, String reservationDate, String reservationTime){
        this.reserverName = reserverName;
        this.numberOfPersons = numberOfPersons;
        this.numberOfTables = numberOfTables;
        this.reservationDate = reservationDate;
        this.reservationTime = reservationTime;
    }

    public String getReserverName() {
        return reserverName;
    }

    public void setReserverName(String name) {
        this.reserverName = name;
    }

    public String getNumberOfPersons() {
        return numberOfPersons;
    }

    public void setNumberOfPersons(String numberOfPersons) {
        this.numberOfPersons = numberOfPersons;
    }

    public String getNumberOfTables() {
        return numberOfTables;
    }

    public void setNumberOfTables(String numberOfTables) {
        this.numberOfTables = numberOfTables;
    }

    public String getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(String reservationDate) {
        this.reservationDate = reservationDate;
    }

    public String getReservationTime() {
        return reservationTime;
    }

    public void setReservationTime(String reservationTime) {
        this.reservationTime = reservationTime;
    }
}
