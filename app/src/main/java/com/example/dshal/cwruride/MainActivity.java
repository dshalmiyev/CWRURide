package com.example.dshal.cwruride;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.support.v4.widget.DrawerLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static int boardNumber;
    public static User testUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Post a Ride
        Button post = (Button) findViewById(R.id.post);
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent postRide = new Intent(getApplicationContext(), PostRide.class);
                startActivity(postRide);
            }
        });

        final DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        //Navigation View Code
        final NavigationView navview = findViewById(R.id.navview);
        navview.bringToFront();
        navview.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int i = menuItem.getItemId();
                switch (i) {
                    case R.id.available_rides:
                        boardNumber = 0;
                        Intent requestBoard = new Intent(getApplicationContext(), RideBoard.class);
                        startActivity(requestBoard);
                        drawerLayout.closeDrawer(Gravity.START);
                        return true;
                    case R.id.available_requests:
                        boardNumber = 1;
                        Intent ridesBoard = new Intent(getApplicationContext(), RideBoard.class);
                        startActivity(ridesBoard);
                        drawerLayout.closeDrawer(Gravity.START);
                        return true;
                    case R.id.my_trips:
                        boardNumber = 2;
                        Intent myTrips = new Intent(getApplicationContext(), RideBoard.class);
                        startActivity(myTrips);
                        drawerLayout.closeDrawer(Gravity.START);
                        return true;
                    case R.id.trip_history:
                        boardNumber = 3;
                        Intent tripHistory = new Intent(getApplicationContext(), RideBoard.class);
                        startActivity(tripHistory);
                        drawerLayout.closeDrawer(Gravity.START);
                        return true;
                    case R.id.buzzwords:
                        Intent buzzwords = new Intent(getApplicationContext(), Buzzwords.class);
                        startActivity(buzzwords);
                        drawerLayout.closeDrawer(Gravity.START);
                        return true;
                    case R.id.help:
                        Intent help = new Intent(getApplicationContext(), Help.class);
                        startActivity(help);
                        drawerLayout.closeDrawer(Gravity.START);
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.drawer_view2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.login:
                Intent login = new Intent(getApplicationContext(), Login.class);
                startActivity(login);
                return true;
            case R.id.account_details:
                if (testUser == null) {
                    Toast.makeText(getApplicationContext(), "No User Found", Toast.LENGTH_LONG).show();
                    Intent Login = new Intent(getApplicationContext(), Login.class);
                    startActivity(Login);
                } else {
                    Intent accountDetails = new Intent(getApplicationContext(), AccountDetails.class);
                    startActivity(accountDetails);
                }
                return true;
        }
        return false;
    }
}
