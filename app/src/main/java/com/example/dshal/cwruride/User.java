package com.example.dshal.cwruride;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class User {
    private String name;
    private String phoneNumber;
    private double feedbackValue;
    private int feedbackCount;

    //Payment Info
    private int ccInfo;
    private int ccCCV;
    private String billAddress;
    private String ccName;
    private int ppInfo;
    private int vmInfo;
    private String tableName;

    private ArrayList<Ride> userRides = new ArrayList<Ride>();

    private int carYear;
    private String carMake;
    private String carModel;
    private String carPlate;
    private String userLicense;
    //Idk if this is even usable now then

    private int userId;
    private String username;
    private String password;

    boolean testing = true;

    public User(String actualName, String cellPhone, String email, String pass){ //Object Created on Login
        /*Relevant sql information I hope you realize I have no idea what to place here*/

        if (testing) {
            name = actualName;
            phoneNumber = cellPhone;
            username = email;
            password = pass;
            userId = 1;

            feedbackValue = 5;
            feedbackCount = 1;

            ccInfo = 123;
            ccCCV = 420;
            ccName = "Anthony Shalmiyev";
            billAddress = "1357 This Way";
            ppInfo = 456;
            vmInfo = 789;

            carYear = 2014; //SQL User Database
            carMake = "Michael"; //SQL User Database
            carModel = "Mia Malkova"; //SQL User Database
            carPlate = "Steak"; //SQL User Database
            userLicense = "To Kill"; //SQL User Database
        }


        name = actualName;
        phoneNumber = cellPhone;
        username = email;
        password = pass;
        feedbackValue = 5;
        feedbackCount = 1;
    }
    /*
    public String verifyRide(int day, int month, int year,int timeStart,int timeRange, int maxDriveEnd,boolean isDriving){
        if(isDriving && !canDrive())
            return "unable to drive";
        //check for time overlaps
        for(Ride currentRide : userRides){
            if(currentRide.dateIsEqual(day,month,year)){
                if(currentRide.getStartTime() < timeStart && currentRide.getEndTime() > timeStart)
                    return "time mismatch";
                if(currentRide.getStartTime() < maxDriveEnd && currentRide.getEndTime() > maxDriveEnd)
                    return "time mismatch";
            }
        }

        return "can post";
    }
    */

    public boolean canDrive(){
        if(ccInfo == 0 || ppInfo == 0 || vmInfo == 0 || carYear == 0 || carMake.equals("")|| carModel.equals("")||carPlate.equals("")||userLicense.equals(""))
            return false;
        return true;//same with this?
    }

    //Get User Info
    public String getFullName() {
        return name;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public double getFeedbackValue() {
        return feedbackValue/feedbackCount;
    }
    public void setFeedbackValue(double feedback) {
        feedbackValue = feedback;
    }
    public void setFeedbackCount(int feedCount) {
        feedbackCount = feedCount;
    }

    public int getUserId() {return userId; }
    public void setUserID(int id) {userId = id; }
    public String getUsername() {return username;}
    public String getPassword() {return password; }

    //Get CC Info
    public int getCcInfo() {
        return ccInfo;
    }
    public int getCcCCV() {
        return ccCCV;
    }
    public String getBillAddress() {
        return billAddress;
    }
    public String getCcName() {
        return ccName;
    }
    //Set CC Info
    public void setCcInfo(int ccInfo) {
        this.ccInfo = ccInfo;
    }
    public void setCcCCV(int ccCCV) {
        this.ccCCV = ccCCV;
    }
    public void setBillAddress(String billAddress) {
        this.billAddress = billAddress;
    }
    public void setCcName(String ccName) {
        this.ccName = ccName;
    }
    //Other Payment Methods
    public int getPpInfo() {
        return ppInfo;
    }
    public int getVmInfo() {
        return vmInfo;
    }

    //Get Car info
    public int getCarYear() {
        return carYear;
    }
    public String getCarMake() {
        return carMake;
    }
    public String getCarModel() {
        return carModel;
    }
    public String getCarPlate() {
        return carPlate;
    }
    public String getUserLicense() {
        return userLicense;
    }

    //Set Car info
    public void setCarYear(int input) {
        carYear = input;
    }
    public void setCarMake(String carMake) {
        this.carMake = carMake;
    }
    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }
    public void setCarPlate(String carPlate) {
        this.carPlate = carPlate;
    }
    public void setUserLicense(String userLicense) {
        this.userLicense = userLicense;
    }
}
