package com.example.dshal.cwruride;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.ExecutionException;

import android.os.AsyncTask;
import android.util.Log;

public class RemoteConnection extends AsyncTask<String, String, ResultSet> {

    Connection con;
    ResultSet rs;
    Statement stmt;

    @Override
    protected ResultSet doInBackground(String... params) {
        String ip = "129.22.23.215";
        String port = "1433";
        String db = "CWRURide";
        String un = "CWRURide";
        String pass = "EuIv2!#3XGe&";
        String query;
        try {
            con = DriverManager.getConnection("jdbc:jtds:sqlserver://" + ip + ":" + port + ";" + "databaseName=" + db + ";user=" + un + ";password=" + pass + ";");
            stmt = con.createStatement();

            switch (params[0]) {
                case "getRides":
                    query = "SELECT * FROM Rides WHERE driver_id IS NOT NULL AND passenger_id IS NULL ORDER BY driver_id OFFSET " + params[1] + " ROWS FETCH NEXT 10 ROWS ONLY";
                    rs = stmt.executeQuery(query);
                    return rs;

                case "getRequests":
                    query = "SELECT * FROM Rides WHERE driver_id IS NULL AND passenger_id IS NOT NULL ORDER BY driver_id OFFSET " + params[1] + " ROWS FETCH NEXT 10 ROWS ONLY";
                    rs = stmt.executeQuery(query);
                    return rs;

                case "getCurrentTrips":
                    query = "SELECT * FROM Rides WHERE driver_id == currentUser OR passenger_id == currentUser AND rides_start_status == true OFFSET " + params[1] + " ROWS FETCH NEXT 10 ROWS ONLY";
                    rs = stmt.executeQuery(query);
                    return rs;

                case "getMyTrips":
                    query = "SELECT * FROM Rides WHERE driver_id == currentUser OR passenger_id == currentUser OFFSET " + params[1] + " ROWS FETCH NEXT 10 ROWS ONLY";
                    rs = stmt.executeQuery(query);
                    return rs;

                case "addRequest":
                    query = "INSERT INTO Rides (passenger_id, passenger_name, rides_time, rides_date, drive_length, rating, rides_start_status, rides_end_status, start_location, end_location, description) "
                            + "VALUES (" + params[1] + ", '" + params[2] + "', '" + params[3] + "', '" + params[4] + "', '" + params[5] + "', "
                            + params[6] + ", " + params[7] + ", " + params[8] + ", '" + params[9] + "', '" + params[10] + "', '" + params[11] + "')";
                    stmt.executeUpdate(query);
                    return null;

                case "addRide":
                    query = "INSERT INTO Rides (driver_id, driver_name, rides_time, rides_date, drive_length, rating, rides_start_status, rides_end_status, start_location, end_location, description) "
                            + "VALUES (" + params[1] + ", '" + params[2] + "', '" + params[3] + "', '" + params[4] + "', '" + params[5] + "', "
                            + params[6] + ", " + params[7] + ", " + params[8] + ", '" + params[9] + "', '" + params[10] + "', '" + params[11] + "')";
                    stmt.executeUpdate(query);
                    return null;

                case "addUser":
                    query = "SELECT COUNT(*) FROM Users WHERE email = '" + params[3] + "'";
                    rs = stmt.executeQuery(query);
                    if (rs.next()) {
                        query = "INSERT INTO Users (user_name, password, email, phone_number, rating_sum, rating_count) VALUES ('"
                                + params[1] + "', '" + params[2] + "', '" + params[3] + "', '" + params[4] + "', " + params[5] + ", " + params[6] + ")";
                        stmt.executeUpdate(query);
                        query = "SELECT user_id FROM Users WHERE email = '" + params[3] + "'";
                        rs = stmt.executeQuery(query);
                        return rs;
                    }
                    else {
                        System.out.println("Fuck you u little bitch");
                        return null;
                    }

                case "login":
                    query = "SELECT COUNT(*) FROM Users WHERE email = '" + params[1] + "' AND password = '" + params[2] + "'";
                    rs = stmt.executeQuery(query);
                    if (rs.next()){
                        query = "SELECT * FROM Users WHERE email = '" + params[1] + "' AND password = '" + params[2] + "'";
                        rs = stmt.executeQuery(query);
                        return rs;
                    }
                    else {
                        return null;
                    }
            }
        } catch (SQLException e) {
            System.out.println("SQL exception");
            e.printStackTrace();
        }
        return null;
    }

    public ResultSet getRides(int pageNum) {
        try {
            return new RemoteConnection().execute("getRides", Integer.toString(pageNum)).get();
        } catch (ExecutionException ee) {
            Log.e("ExecutionException", ee.getMessage());
        } catch (InterruptedException ie) {
            Log.e("InterruptedException", ie.getMessage());
        }
        return null;
    }

    public ResultSet getRequests(int pageNum) {
        try {
            return new RemoteConnection().execute("getRequests", Integer.toString(pageNum)).get();
        } catch (ExecutionException ee) {
            Log.e("ExecutionException", ee.getMessage());
        } catch (InterruptedException ie) {
            Log.e("InterruptedException", ie.getMessage());
        }
        return null;
    }

    public void addRequest(int passengerID, String passengerName, String ridesTime, String ridesDate, String rideLength, double rating,
                           int startStatus, int endStatus, String startLocation, String endLocation, String description) {
        new RemoteConnection().execute("addRequests", Integer.toString(passengerID), passengerName, ridesTime, ridesDate, rideLength,
                Double.toString(rating), Integer.toString(startStatus), Integer.toString(endStatus), startLocation, endLocation, description);
    }

    public void addRide(int driverID, String driverName, String ridesTime, String ridesDate, String rideLength, double rating,
                        int startStatus, int endStatus, String startLocation, String endLocation, String description) {
        new RemoteConnection().execute("addRequests", Integer.toString(driverID), driverName, ridesTime, ridesDate, rideLength,
                Double.toString(rating), Integer.toString(startStatus), Integer.toString(endStatus), startLocation, endLocation, description);
    }

    public ResultSet addUser (String name, String password, String email, String phoneNumber, double feedback, int reviewCount) {
        try {
            return new RemoteConnection().execute("addUser", name, password, email, phoneNumber, Double.toString(feedback), Integer.toString(reviewCount)).get();
        } catch (InterruptedException ie) {
            Log.e("InterruptedException", ie.getMessage());
        } catch (ExecutionException ee) {
            Log.e("ExecutionException", ee.getMessage());
        }
        return null;
    }

    public ResultSet login (String name, String password) {
        try {
            return new RemoteConnection().execute("login", name, password).get();
        } catch (InterruptedException ie) {
            Log.e("InterruptedException", ie.getMessage());
        } catch (ExecutionException ee) {
            Log.e("ExecutionException", ee.getMessage());
        }
        return null;
    }
}

