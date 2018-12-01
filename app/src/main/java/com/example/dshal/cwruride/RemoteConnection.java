package com.example.dshal.cwruride;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class RemoteConnection {
    public static Connection getRemoteConnection() {
        Connection con;
        String ip = "129.22.23.215";
        String port = "1433";
        String db = "CWRURide";
        String un = "CWRURide";
        String pass = "EuIv2!#3XGe&";

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection("jdbc:sqlserver://" + ip + ":" + port + ";" + "databaseName=" + db + ";user=" + un + ";password=" + pass + ";");
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

