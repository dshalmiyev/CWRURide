package com.example.dshal.cwruride;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class RemoteConnection {
    public static Connection getRemoteConnection() {
        Connection con;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://dshalmiyevdb.c6dnsienqpqh.us-east-1.rds.amazonaws.com/dshalmiyevdb","dshalmiyev","Sdidag#15");
            return con;
        }
        catch (ClassNotFoundException e) {
            System.out.println("Class not found exception");
            e.printStackTrace();
        }
        catch (SQLException e) {
            System.out.println("SQL exception");
            e.printStackTrace();
        }
        return null;
    }
}

