package com.example.dshal.cwruride;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class RideBoard extends AppCompatActivity {
    public ArrayList<Ride> shownRides;
    int pageCount = 0;
    boolean testingValues = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_board);
        shownRides = getRideSet(pageCount);
        setTableValues();
        TableRow TableRow1 = (TableRow) findViewById(R.id.ride_board_row1);

        Button post2 = (Button) findViewById(R.id.postARide);
        post2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent postRide = new Intent(getApplicationContext(), PostRide.class);
                startActivity(postRide);
            }
        });

        Button next = (Button) findViewById(R.id.nextPage);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pageCount++;
                shownRides = getRideSet(pageCount);
                setTableValues();
            }
        });

        Button previous = (Button) findViewById(R.id.previousPage);
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pageCount == 0)
                    return;
                pageCount--;
                shownRides = getRideSet(pageCount);
                setTableValues();
            }
        });

    }

    public ArrayList<Ride> getRideSet(int pageNumber) {
        switch (MainActivity.boardNumber) {
            case 0:
                //SQL get method for available rides
                break;
            case 1:
                //SQL get method for available requests
                break;
            case 2:
                //SQL get method for my trips
                break;
            case 3:
                //SQL get method for trip history
                break;
        }

        if (testingValues) {
            ArrayList<Ride> hardCoded = new ArrayList<Ride>(10);
            if (pageNumber == 0) {
                hardCoded.add(new Ride(1, "Testing"));
                hardCoded.add(new Ride(2, "Testing"));
                hardCoded.add(new Ride(3, "Testing"));
                hardCoded.add(new Ride(5, "Testing"));
                hardCoded.add(new Ride(7, "Testing"));
                hardCoded.add(new Ride(10, "Testing"));
                hardCoded.add(new Ride(11, "Testing"));
                hardCoded.add(new Ride(12, "Testing"));
                hardCoded.add(new Ride(13, "Testing"));
                hardCoded.add(new Ride(14, "Testing"));
            }
            if (pageNumber == 1) {
                hardCoded.add(new Ride(15, "Testing"));
                hardCoded.add(new Ride(16, "Testing"));
                hardCoded.add(new Ride(17, "Testing"));
                hardCoded.add(new Ride(18, "Testing"));
                hardCoded.add(new Ride(20, "Testing"));
                hardCoded.add(new Ride(21, "Testing"));
                hardCoded.add(new Ride(22, "Testing"));
                hardCoded.add(new Ride(23, "Testing"));
            }
            return hardCoded;
        }
        return null;
        //This is where we implement the correct SQL stuff
    }

    public void myTableRowClickHandler(View view) {
        switch (view.getId()) {
            case R.id.ride_board_row2:
                if(shownRides.get(0).getDescription().equals("No Ride"))
                    break; //If null Ride
                openDialogue(shownRides.get(0));
                break;
            case R.id.ride_board_row3:
                if(shownRides.get(1).getDescription().equals("No Ride"))
                    break; //If null Ride
                openDialogue(shownRides.get(1));
                break;
            case R.id.ride_board_row4:
                if(shownRides.get(2).getDescription().equals("No Ride"))
                    break; //If null Ride
                openDialogue(shownRides.get(2));
                break;
            case R.id.ride_board_row5:
                if(shownRides.get(3).getDescription().equals("No Ride"))
                    break; //If null Ride
                openDialogue(shownRides.get(3));
                break;
            case R.id.ride_board_row6:
                if(shownRides.get(4).getDescription().equals("No Ride"))
                    break; //If null Ride
                openDialogue(shownRides.get(4));
                break;
            case R.id.ride_board_row7:
                if(shownRides.get(5).getDescription().equals("No Ride"))
                    break; //If null Ride
                openDialogue(shownRides.get(5));
                break;
            case R.id.ride_board_row8:
                if(shownRides.get(6).getDescription().equals("No Ride"))
                    break; //If null Ride
                openDialogue(shownRides.get(6));
                break;
            case R.id.ride_board_row9:
                if(shownRides.get(7).getDescription().equals("No Ride"))
                    break; //If null Ride
                openDialogue(shownRides.get(7));
                break;
            case R.id.ride_board_row10:
                if(shownRides.get(8).getDescription().equals("No Ride"))
                    break; //If null Ride
                openDialogue(shownRides.get(8));
                break;
            case R.id.ride_board_row11:
                if(shownRides.get(9).getDescription().equals("No Ride"))
                    break; //If null Ride
                openDialogue(shownRides.get(9));
                break;
        }
    }

    public void setTableValues() {
        while (shownRides.size() < 10)
            shownRides.add(new Ride(13, "empty"));

        TextView time1 = (TextView) findViewById(R.id.start_time1);
        time1.setText(Integer.toString(shownRides.get(0).getStartTime()));
        TextView time2 = (TextView) findViewById(R.id.start_time2);
        time2.setText(Integer.toString(shownRides.get(1).getStartTime()));
        TextView time3 = (TextView) findViewById(R.id.start_time3);
        time3.setText(Integer.toString(shownRides.get(2).getStartTime()));
        TextView time4 = (TextView) findViewById(R.id.start_time4);
        time4.setText(Integer.toString(shownRides.get(3).getStartTime()));
        TextView time5 = (TextView) findViewById(R.id.start_time5);
        time5.setText(Integer.toString(shownRides.get(4).getStartTime()));
        TextView time6 = (TextView) findViewById(R.id.start_time6);
        time6.setText(Integer.toString(shownRides.get(5).getStartTime()));
        TextView time7 = (TextView) findViewById(R.id.start_time7);
        time7.setText(Integer.toString(shownRides.get(6).getStartTime()));
        TextView time8 = (TextView) findViewById(R.id.start_time8);
        time8.setText(Integer.toString(shownRides.get(7).getStartTime()));
        TextView time9 = (TextView) findViewById(R.id.start_time9);
        time9.setText(Integer.toString(shownRides.get(8).getStartTime()));
        TextView time10 = (TextView) findViewById(R.id.start_time10);
        time10.setText(Integer.toString(shownRides.get(9).getStartTime()));

        TextView date1 = (TextView) findViewById(R.id.ride_date1);
        date1.setText(shownRides.get(0).getDate());
        TextView date2 = (TextView) findViewById(R.id.ride_date2);
        date2.setText(shownRides.get(1).getDate());
        TextView date3 = (TextView) findViewById(R.id.ride_date3);
        date3.setText(shownRides.get(2).getDate());
        TextView date4 = (TextView) findViewById(R.id.ride_date4);
        date4.setText(shownRides.get(3).getDate());
        TextView date5 = (TextView) findViewById(R.id.ride_date5);
        date5.setText(shownRides.get(4).getDate());
        TextView date6 = (TextView) findViewById(R.id.ride_date6);
        date6.setText(shownRides.get(5).getDate());
        TextView date7 = (TextView) findViewById(R.id.ride_date7);
        date7.setText(shownRides.get(6).getDate());
        TextView date8 = (TextView) findViewById(R.id.ride_date8);
        date8.setText(shownRides.get(7).getDate());
        TextView date9 = (TextView) findViewById(R.id.ride_date9);
        date9.setText(shownRides.get(8).getDate());
        TextView date10 = (TextView) findViewById(R.id.ride_date10);
        date10.setText(shownRides.get(9).getDate());

        TextView description1 = (TextView) findViewById(R.id.ride_description1);
        description1.setText(shownRides.get(0).getDescription());
        TextView description2 = (TextView) findViewById(R.id.ride_description2);
        description2.setText(shownRides.get(1).getDescription());
        TextView description3 = (TextView) findViewById(R.id.ride_description3);
        description3.setText(shownRides.get(2).getDescription());
        TextView description4 = (TextView) findViewById(R.id.ride_description4);
        description4.setText(shownRides.get(3).getDescription());
        TextView description5 = (TextView) findViewById(R.id.ride_description5);
        description5.setText(shownRides.get(4).getDescription());
        TextView description6 = (TextView) findViewById(R.id.ride_description6);
        description6.setText(shownRides.get(5).getDescription());
        TextView description7 = (TextView) findViewById(R.id.ride_description7);
        description7.setText(shownRides.get(6).getDescription());
        TextView description8 = (TextView) findViewById(R.id.ride_description8);
        description8.setText(shownRides.get(7).getDescription());
        TextView description9 = (TextView) findViewById(R.id.ride_description9);
        description9.setText(shownRides.get(8).getDescription());
        TextView description10 = (TextView) findViewById(R.id.ride_description10);
        description10.setText(shownRides.get(9).getDescription());

        TextView userRating1 = (TextView) findViewById(R.id.user_rating1);
        userRating1.setText(Integer.toString(shownRides.get(0).getUserRating()));
        TextView userRating2 = (TextView) findViewById(R.id.user_rating2);
        userRating2.setText(Integer.toString(shownRides.get(1).getUserRating()));
        TextView userRating3 = (TextView) findViewById(R.id.user_rating3);
        userRating3.setText(Integer.toString(shownRides.get(2).getUserRating()));
        TextView userRating4 = (TextView) findViewById(R.id.user_rating4);
        userRating4.setText(Integer.toString(shownRides.get(3).getUserRating()));
        TextView userRating5 = (TextView) findViewById(R.id.user_rating5);
        userRating5.setText(Integer.toString(shownRides.get(4).getUserRating()));
        TextView userRating6 = (TextView) findViewById(R.id.user_rating6);
        userRating6.setText(Integer.toString(shownRides.get(5).getUserRating()));
        TextView userRating7 = (TextView) findViewById(R.id.user_rating7);
        userRating7.setText(Integer.toString(shownRides.get(6).getUserRating()));
        TextView userRating8 = (TextView) findViewById(R.id.user_rating8);
        userRating8.setText(Integer.toString(shownRides.get(7).getUserRating()));
        TextView userRating9 = (TextView) findViewById(R.id.user_rating9);
        userRating9.setText(Integer.toString(shownRides.get(8).getUserRating()));
        TextView userRating10 = (TextView) findViewById(R.id.user_rating10);
        userRating10.setText(Integer.toString(shownRides.get(9).getUserRating()));
    }

    public void openDialogue(Ride ride) {
        final Dialog dialog = new Dialog(RideBoard.this);
        dialog.setContentView(R.layout.dialog2);

        // set up the text boxes
        //description text
        TextView text = (TextView) dialog.findViewById(R.id.textView2);
        text.setText(ride.getDescription());

        //Date text
        TextView text2 = (TextView) dialog.findViewById(R.id.textView3);
        text2.setText(ride.getDate());

        //Time text
        TextView text3 = (TextView) dialog.findViewById(R.id.textView4);
        text3.setText(Integer.toString(ride.getStartTime()));

        //Earnings text
        TextView text4 = (TextView) dialog.findViewById(R.id.textView5);
        text4.setText(Integer.toString(ride.getCost()));

        //User id text
        TextView text5 = (TextView) dialog.findViewById(R.id.textView6);
        text5.setText(ride.getComplementaryUserID());

        // set up the buttons
        Button button = (Button) dialog.findViewById(R.id.Cancel);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        Button button1 = (Button) dialog.findViewById(R.id.Confirm);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //write to SQL
            }
        });

        //Show Dialog
        dialog.show();
    }
}