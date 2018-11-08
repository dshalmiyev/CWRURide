package com.example.dshal.cwruride;

public class Ride {
    private int timeStart;
    private int timeEnd;
    private int driveLength;
    private int cost;
    private String complementaryUserID;

    public Ride(int rideID, String tableName){
        if(tableName == "Testing") {
            timeStart = rideID;
            timeEnd = rideID + 2*rideID;
            driveLength = 2*rideID;
            cost = 3*rideID;
            complementaryUserID = rideID*5 + "";
        }
    }

    //methods
    public boolean dateIsEqual(int day, int month, int year){
        return true;
    }
    public int getTimeStart(){
        return timeStart;
    }
    public int getTimeEnd(){
        return timeEnd;
    }
    public int calculateCost(){
        return 0;//everything is free rn buy one get them all
        //Is this passed from google maps api?
    }

}
