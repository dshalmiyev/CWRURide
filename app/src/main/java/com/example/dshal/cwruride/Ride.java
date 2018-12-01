package com.example.dshal.cwruride;

public class Ride {
    private int rideID;
    private String startTime;
    private double driveLength;
    private int cost;
    private double userRating;
    private int PassengerUserID;
    private int DriverUserID;
    private String passengerName;
    private String driverName;
    private String description;
    private String startLocation;
    private String endLocation;

    private String date;
    private boolean started;
    private boolean ended;
    //Ride storeRide = new Ride(rs.getInt("rides_id"), rs.getInt("driver_id"), rs.getInt("passenger_id"),
      //      rs.getString("driver_name"), rs.getString("passenger_name"), rs.getDate("rides_time"), rs.getDouble("rating"), rs.getInt("rides_start_status") == 1
    // ,rs.getInt("rides_end_status") == 1, rs.getBoolean("rides_end_status")rs.getString("start_location"), rs.getString("end_location"),
        //    rs.getString("description"));
    public Ride(int rideSQLID, int driverID, int passengerID, String drivName, String passName, String time, String rideDate, double drive, double rating, boolean rideStarted, boolean rideEnded, String startLocal, String endLocal, String descrip,  String nullEvaluator){
        if(nullEvaluator.equals("Testing")) {
            startTime = rideID + "";
            driveLength = 2*rideID;
            cost = 3*rideID;
            PassengerUserID = rideID*5 ;
            DriverUserID = rideID*5 ;
            passengerName = "Michael";
            driverName = "Silverman";
            userRating = 3;
            description = "description" + startTime;
            date = startTime + "/" + driveLength + "/" + cost;
        }
        if(nullEvaluator.equals("empty")){
            startTime = 0 + "";
            driveLength = 0;
            cost = 0;
            PassengerUserID = 0 ;
            DriverUserID = 0 ;
            passengerName = "No User";
            driverName = "No User";
            userRating = 0;
            description = "No Ride";
            date = "No Date";
        }
        else{
            rideID = rideSQLID;
            DriverUserID = driverID;
            PassengerUserID = passengerID;
            startTime = time;
            driveLength = drive;
            cost = calculateCost(driveLength);
            userRating = rating;
            description = descrip;
            date = rideDate;

        }


    }

    //methods
    public boolean dateIsEqual(int day, int month, int year){
        return true;
    }
    public String getStartTime(){
        return startTime;
    }
    public double getUserRating(){ return userRating;}

    public String getDescription(){ return description; }
    public String getDate(){return date;}
    public int getCost(){return cost;}
    public int getPassengerUserID(){return PassengerUserID;}
    public int getDriverUserID(){return DriverUserID;}
    public String getPassengerName(){ return passengerName;}
    public String getDriverName(){ return driverName;}

    public int calculateCost( double driveLen){
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
