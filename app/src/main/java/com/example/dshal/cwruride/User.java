package com.example.dshal.cwruride;

import java.util.*;
public class User {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private double feedbackValue;
    //Payment Info
    private int ccInfo;
    private int ppInfo;
    private int vmInfo;
    private String tableName;

    private ArrayList<Ride> userRides;

    private int carYear;
    private String carMake;//This can be done in Car objects if we want?
    private String carModel;
    private String carPlate;
    private String userLicense;
    //Idk if this is even usable now then

    boolean testing = true;

    public User(){ //Object Created on Case Login
        /*Relevant sql information I hope you realize I have no idea what to place here*/
        if (testing == true) {
            firstName = "Anthony"; //From Case
            lastName = "Shalmiyev"; // From Case
            phoneNumber = "666-666-6666"; //From Case, look into link
            feedbackValue = 1.1; // Average of all feedbacks in SQL User Database
            ccInfo = 123; //SQL User Database
            ppInfo = 456; //SQL User Database
            vmInfo = 789; //SQL User Database

            //Rides
            //Make new SQL table called X, SELECT * FROM Rides WHERE firstName = Rides.firstName and lastName = Rides.lastName
            //MAKE RIDES
            //int rideCount = SQL procedure SELECT COUNT...
            //for (int i = 0; i < rideCount; i++) {
                //userRides.add(new Ride(i, X));
            //}
            for (int i = 0; i < 5; i++) {
                userRides.add(new Ride(i, "Testing"));
            }

            carYear = 2014; //SQL User Database
            carMake = "Michael"; //SQL User Database
            carModel = "Mia Malkova"; //SQL User Database
            carPlate = "Steak"; //SQL User Database
            userLicense = "To Kill"; //SQL User Database
        }
    }
    public String verifyRide(int day, int month, int year,int timeStart,int timeRange, int maxDriveEnd,boolean isDriving){
        if(isDriving && canDrive())
            return "unable to drive";
        //check for time overlaps
        for(Ride currentRide : userRides){
            if(currentRide.dateIsEqual(day,month,year)){
                if(currentRide.getTimeStart() > timeStart && currentRide.getTimeStart() < maxDriveEnd)
                    return "time mismatch";
                if(currentRide.getTimeEnd() > timeStart && currentRide.getTimeEnd() < maxDriveEnd)
                    return "time mismatch";
            }
        }

        return "can post";
    }
    public boolean canDrive(){
        if(ccInfo == 0 || ppInfo == 0 || vmInfo == 0 || carYear == 0 || carMake.equals(null)|| carModel.equals(null)||carPlate.equals(null)||userLicense.equals(null))
            return false;
        return true;//same with this?
    }

}
