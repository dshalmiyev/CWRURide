package com.example.dshal.cwruride;

public class Ride {
    private int startTime;
    private int endTime;
    private int driveLength;
    private int cost;
    private int userRating;
    private String complementaryUserID;
    private String description;
    private String date;
    private boolean started;
    private boolean ended;

    public Ride(int rideID, String tableName){
        if(tableName.equals("Testing")) {
            startTime = rideID;
            endTime = rideID + 2*rideID;
            driveLength = 2*rideID;
            cost = 3*rideID;
            complementaryUserID = rideID*5 + "";
            userRating = 3;
            description = "description" + startTime;
            date = startTime + "/" + driveLength + "/" + cost;
        }
        if(tableName.equals("empty")){
            startTime = 0;
            endTime = 0;
            driveLength = 0;
            cost = 0;
            complementaryUserID = 0 + "";
            userRating = 0;
            description = "No Ride";
            date = "No Date";
        }

    }

    //methods
    public boolean dateIsEqual(int day, int month, int year){
        return true;
    }
    public int getStartTime(){
        return startTime;
    }
    public int getUserRating(){ return userRating;}
    public int getEndTime(){
        return endTime;
    }
    public String getDescription(){ return description; }
    public String getDate(){return date;}
    public int getCost(){return cost;}
    public String getComplementaryUserID(){return complementaryUserID;}
    public int calculateCost(){
        return 0;//everything is free rn buy one get them all
        //Is this passed from google maps api?
    }
    public boolean getStart() {
        return started;
    }
    public boolean getEnded() {
        return ended;
    }

    public void setStart(boolean input) {
        started = input;
    }
    public void setEnd(boolean input) {
        ended = input;
    }
}
