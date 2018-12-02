package com.example.dshal.cwruride;

import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import android.os.AsyncTask;
import android.util.Log;

import javax.xml.transform.Result;

public class RemoteConnection extends AsyncTask<String, String, ResultSet>{
    User testUser = new User();

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
        try {
            con = DriverManager.getConnection("jdbc:jtds:sqlserver://" + ip + ":" + port + ";" + "databaseName=" + db + ";user=" + un + ";password=" + pass + ";");

            switch (params[0]) {
                case "getRides":
                    System.out.println(params[1]);
                    String query2 = "SELECT TOP (10) * FROM Rides WHERE driver_id IS NOT NULL AND passenger_id is NULL ORDER BY driver_id";
                    String query = "SELECT * FROM Rides WHERE driver_id IS NOT NULL AND passenger_id IS NULL ORDER BY driver_id OFFSET " + params[1] + " ROWS FETCH NEXT 10 ROWS ONLY";
                    stmt = con.createStatement();
                    rs = stmt.executeQuery(query);
                    return rs;
            }
        }
        catch (SQLException e) {
            System.out.println("SQL exception");
            e.printStackTrace();
        }
        return null;
    }

    public ResultSet getRides(int pageNum){
        try{
            return new RemoteConnection().execute("getRides",Integer.toString(pageNum)).get();
        }
        catch (ExecutionException ee) {
            Log.e("ExecutionException", ee.getMessage());
        }
        catch (InterruptedException ie) {
            Log.e("InterruptedException",ie.getMessage());
        }
        return null;
    }
}

