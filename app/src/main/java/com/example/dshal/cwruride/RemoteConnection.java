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

                case "getTripHistory":
                    query = "SELECT * FROM Rides WHERE (driver_id = " + params[1] + " OR passenger_id = " + params[1] + ") AND rides_end_status = 1 ORDER BY rides_id OFFSET " + params[2] + " ROWS FETCH NEXT 10 ROWS ONLY";
                    rs = stmt.executeQuery(query);
                    return rs;

                case "getMyTrips":
                    query = "SELECT * FROM Rides WHERE (driver_id = " + params[1] + " OR passenger_id = " + params[1] + ") AND rides_end_status = 0 ORDER BY rides_id OFFSET " + params[2] + " ROWS FETCH NEXT 10 ROWS ONLY";
                    rs = stmt.executeQuery(query);
                    return rs;

                case "addRequest":
                    query = "INSERT INTO Rides (passenger_id, passenger_name, rides_time, rides_date, drive_length, rating, rides_start_status, rides_end_status, start_location, end_location, description) "
                            + "VALUES (" + params[1] + ", '" + params[2] + "', '" + params[3] + "', '" + params[4] + "', '" + params[5] + "', "
                            + params[6] + ", " + params[7] + ", " + params[8] + ", '" + params[9] + "', '" + params[10] + "', '" + params[11] + "')";
                    stmt.executeUpdate(query);
                    System.out.println(query);
                    return null;

                case "addRide":
                    System.out.println("about to query");
                    query = "INSERT INTO Rides (driver_id, driver_name, rides_time, rides_date, cost, rating, rides_start_status, rides_end_status, start_location, end_location, description) "
                            + "VALUES (" + params[1] + ", '" + params[2] + "', '" + params[3] + "', '" + params[4] + "', " + params[5] + ", "
                            + params[6] + ", " + params[7] + ", " + params[8] + ", '" + params[9] + "', '" + params[10] + "', '" + params[11] + "')";
                    stmt.executeUpdate(query);
                    System.out.println(query);
                    return null;

                case "addUser":
                    query = "SELECT COUNT(*) FROM Users WHERE email = '" + params[3] + "'";
                    rs = stmt.executeQuery(query);
                    rs.next();
                    if (rs.getInt(1) == 0) {
                        query = "INSERT INTO Users (user_name, password, email, phone_number, rating_sum, rating_count) VALUES ('"
                                + params[1] + "', '" + params[2] + "', '" + params[3] + "', '" + params[4] + "', " + params[5] + ", " + params[6] + ")";
                        stmt.executeUpdate(query);
                        query = "SELECT user_id FROM Users WHERE email = '" + params[3] + "'";
                        rs = stmt.executeQuery(query);
                        return rs;
                    }
                    else {
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

                case "startRide":
                    query = "UPDATE Rides SET rides_start_status = 1 WHERE rides_id = " + params[1];
                    stmt.executeUpdate(query);
                    return null;

                case "endRide":
                    query = "UPDATE Rides SET rides_end_status = 1 WHERE rides_id = " + params[1];
                    stmt.executeUpdate(query);
                    return null;

                case "acceptRide":
                    query = "UPDATE Rides SET passenger_id = " + params[2] + ", passenger_name = '" + params[1] + "' WHERE rides_id = " + params[3];
                    stmt.executeUpdate(query);
                    return null;

                case "acceptRequest":
                    query = "UPDATE Rides SET driver_id = " + params[2] + ", driver_name = '" + params[1] + "' WHERE rides_id = " + params[3];
                    stmt.executeUpdate(query);
                    return null;

                case "review":
                    query = "UPDATE Users SET rating_sum = rating_sum + " + params[1] + ", rating_count = rating_count + 1 WHERE user_id = " + params[2];
                    stmt.executeUpdate(query);
                    return null;

                case "addCar":
                    query = "UPDATE Users SET year = " + params[1] + ", make = '" + params[2] + "', model = '" + params[3] + "', plate = '" + params[4] + "', license = '" + params[5] + "' WHERE user_id = " + params[6];
                    stmt.executeUpdate(query);
                    return null;

                case "removeCar":
                    query = "UPDATE Users SET year = NULL, make = NULL, model = NULL, plate = NULL, license = NULL WHERE user_id = " + params[1];
                    stmt.executeUpdate(query);
                    return null;

                case "checkCar":
                    query = "SELECT year, make, model, plate, license FROM Users WHERE user_id = " + params[1];
                    rs = stmt.executeQuery(query);
                    return rs;
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
        try {
            new RemoteConnection().execute("addRequests", Integer.toString(passengerID), passengerName, ridesTime, ridesDate, rideLength,
                    Double.toString(rating), Integer.toString(startStatus), Integer.toString(endStatus), startLocation, endLocation, description).get();
        } catch (InterruptedException ie) {
            Log.e("InterruptedException", ie.getMessage());
        } catch (ExecutionException ee) {
            Log.e("ExecutionException", ee.getMessage());
        }
    }

    public void addRide(int driverID, String driverName, String ridesTime, String ridesDate, Double cost, double rating,
                        int startStatus, int endStatus, String startLocation, String endLocation, String description) {
        try {
            new RemoteConnection().execute("addRide", Integer.toString(driverID), driverName, ridesTime, ridesDate, Double.toString(cost),
                Double.toString(rating), Integer.toString(startStatus), Integer.toString(endStatus), startLocation, endLocation, description).get();
        } catch (InterruptedException ie) {
            Log.e("InterruptedException", ie.getMessage());
        } catch (ExecutionException ee) {
            Log.e("ExecutionException", ee.getMessage());
        }
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

    public ResultSet getMyTrips (int userID, int pageNum) {
        try {
            return new RemoteConnection().execute("getMyTrips", Integer.toString(userID), Integer.toString(pageNum)).get();
        } catch (InterruptedException ie) {
            Log.e("InterruptedException", ie.getMessage());
        } catch (ExecutionException ee) {
            Log.e("ExecutionException", ee.getMessage());
        }
        return null;
    }

    public ResultSet getTripHistory (int userID, int pageNum) {
        try {
            return new RemoteConnection().execute("getTripHistory", Integer.toString(userID), Integer.toString(pageNum)).get();
        } catch (InterruptedException ie) {
            Log.e("InterruptedException", ie.getMessage());
        } catch (ExecutionException ee) {
            Log.e("ExecutionException", ee.getMessage());
        }
        return null;
    }

    public void startRide (int rideID) {
        try {
            new RemoteConnection().execute("startRide", Integer.toString(rideID)).get();
        } catch (InterruptedException ie) {
            Log.e("InterruptedException", ie.getMessage());
        } catch (ExecutionException ee) {
            Log.e("ExecutionException", ee.getMessage());
        }
    }

    public void endRide (int rideID) {
        try {
            new RemoteConnection().execute("endRide", Integer.toString(rideID)).get();
        } catch (InterruptedException ie) {
            Log.e("InterruptedException", ie.getMessage());
        } catch (ExecutionException ee) {
            Log.e("ExecutionException", ee.getMessage());
        }
    }

    public void acceptRide (String fullName, int userID, int rideID) {
        try {
            new RemoteConnection().execute("acceptRide", fullName, Integer.toString(userID), Integer.toString(rideID)).get();
        } catch (InterruptedException ie) {
            Log.e("InterruptedException", ie.getMessage());
        } catch (ExecutionException ee) {
            Log.e("ExecutionException", ee.getMessage());
        }
    }

    public void acceptRequest (String fullName, int userID, int rideID) {
        try {
            new RemoteConnection().execute("acceptRequest", fullName, Integer.toString(userID), Integer.toString(rideID)).get();
        } catch (InterruptedException ie) {
            Log.e("InterruptedException", ie.getMessage());
        } catch (ExecutionException ee) {
            Log.e("ExecutionException", ee.getMessage());
        }
    }

    public void review (String rating, int userID) {
        try {
            new RemoteConnection().execute("review", rating, Integer.toString(userID)).get();
        } catch (InterruptedException ie) {
            Log.e("InterruptedException", ie.getMessage());
        } catch (ExecutionException ee) {
            Log.e("ExecutionException", ee.getMessage());
        }
    }

    public void addCar(int year, String make, String model, String plate, String license, int userID) {
        try {
            new RemoteConnection().execute("addCar", Integer.toString(year), make, model, plate, license, Integer.toString(userID)).get();
        } catch (InterruptedException ie) {
            Log.e("InterruptedException", ie.getMessage());
        } catch (ExecutionException ee) {
            Log.e("ExecutionException", ee.getMessage());
        }
    }

    public void removeCar(int userID) {
        try {
            new RemoteConnection().execute("removeCar", Integer.toString(userID)).get();
        }
        catch (InterruptedException ie) {
            Log.e("InterruptedException", ie.getMessage());
        }
        catch (ExecutionException ee) {
            Log.e("ExecutionException", ee.getMessage());
        }
    }

    public ResultSet checkCar(int userID) {
        try {
            return new RemoteConnection().execute("checkCar", Integer.toString(userID)).get();
        }
        catch (InterruptedException ie) {
            Log.e("InterruptedException", ie.getMessage());
        }
        catch (ExecutionException ee) {
            Log.e("ExecutionException", ee.getMessage());
        }
        return null;
    }

}

