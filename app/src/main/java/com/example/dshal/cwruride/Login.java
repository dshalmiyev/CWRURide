package com.example.dshal.cwruride;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.widget.EditText;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Login extends AppCompatActivity {
    //String username;
    //String password;
    int placementID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText editTextforUsername = (EditText)findViewById(R.id.name1);
        final EditText editTextforPassword = (EditText)findViewById(R.id.password1);
        final EditText editTextforFullName = (EditText)findViewById(R.id.fullName);
        final EditText editTextforPasswordCreate = (EditText)findViewById(R.id.password2);
        final EditText editTextforPhoneNumber = (EditText)findViewById(R.id.phoneNumber);
        final EditText editTextforEmail = (EditText)findViewById(R.id.name2);

        Button login = (Button)findViewById(R.id.loginButton);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResultSet rs = new RemoteConnection().login(editTextforUsername.getEditableText().toString(), editTextforPassword.getEditableText().toString());
                if (rs == null){
                    System.out.println("You Done Fucked Up A-ARON");
                }
                else {
                    try {
                        rs.next();
                        MainActivity.testUser = new User(rs.getString(2),rs.getString(7), rs.getString(5), rs.getString(6));
                        MainActivity.testUser.setUserID(rs.getInt(1));
                        MainActivity.testUser.setFeedbackValue(rs.getDouble(3));
                        MainActivity.testUser.setFeedbackCount(rs.getInt(4));
                    }
                    catch (SQLException e){
                        e.printStackTrace();
                    }
                }
            }
        });

        Button createAccount = (Button)findViewById(R.id.createButton);
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(attemptUserPlacement(editTextforFullName.getEditableText().toString(), editTextforPasswordCreate.getEditableText().toString(),
                        editTextforEmail.getEditableText().toString(), editTextforPhoneNumber.getEditableText().toString())){
                    MainActivity.testUser = new User(editTextforFullName.getEditableText().toString(), editTextforPhoneNumber.getEditableText().toString(),
                            editTextforEmail.getEditableText().toString(), editTextforPasswordCreate.getEditableText().toString());
                    MainActivity.testUser.setUserID(placementID);
                }
            }
        });
    }

    //search for email with correct password in sql

    public boolean attemptUserPlacement(String name, String password, String username, String phoneNumber){
        ResultSet rs = new RemoteConnection().addUser(name,password,username,phoneNumber,5,1);
        try {
            if(rs == null) {
                return false;
            }
            else {
                rs.next();
                placementID = rs.getInt(1);
                return true;
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

}
