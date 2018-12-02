package com.example.dshal.cwruride;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;


import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RideBoard extends AppCompatActivity {
    //ArrayList to store viewable rides
    public ArrayList<Ride> shownRides;
    //int that changes what rides are viewable
    int pageCount = 0;
    //For Testing
    boolean testingValues = true;
    //Keep track of current ride (for indexing)
    Ride currentRide;

    /*Creates all activities with tables to view and select already posted rides
        Creates initial viewable rides in shownRides
        Creates table to store and view the rides
        Creates buttons to increment and decrement pageCount with next, previous
        Creates button to bring user to PostRide activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_board);
        shownRides = getRideSet(pageCount);
        setTableValues();
        TableRow TableRow1 = (TableRow) findViewById(R.id.ride_board_row1);

        //Brings the user to the PostRide activity
        Button post2 = (Button) findViewById(R.id.postARide);
        post2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent postRide = new Intent(getApplicationContext(), PostRide.class);
                startActivity(postRide);
            }
        });

        //Increments pageCount
        Button next = (Button) findViewById(R.id.nextPage);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pageCount++;
                shownRides = getRideSet(pageCount);
                setTableValues();
            }
        });

        //Decrements pageCount
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

    /*
        Takes an input of type int and returns an ArrayList
        Selection is based on boardNumber which is the type of board that will be viewed
        adds rides to shownRides based on board type
     */
    public ArrayList<Ride> getRideSet(int pageNumber) {
        switch (MainActivity.boardNumber) {
            case 0:
                //SQL get method for available rides
                ResultSet rs = new RemoteConnection().getRides(pageNumber*10);
                ArrayList<Ride> results = new ArrayList<Ride>();
                try {
                    while (rs.next()) {
                        results.add(new Ride(rs.getInt("rides_id"), rs.getInt("driver_id"), rs.getInt("passenger_id"),
                                rs.getString("driver_name"), rs.getString("passenger_name"), rs.getString("rides_time"), rs.getString("rides_date"), rs.getDouble("drive_length"),
                                rs.getDouble("rating"), rs.getBoolean("rides_start_status"), rs.getBoolean("rides_end_status"),
                                rs.getString("start_location"), rs.getString("end_location"), rs.getString("description"), ""));
                    }
                    return results;
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
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

        /*
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
        */
        return null;
    }

    /*
        Takes input of type View and returns nothing
        Opens a dialogue based on the table row selected
     */

    public void myTableRowClickHandler(View view) {
        switch (view.getId()) {
            case R.id.ride_board_row2:
                if(shownRides.get(0).getDescription().equals("No Ride"))
                    break; //If null Ride
                currentRide = shownRides.get(0);
                openDialogue(shownRides.get(0));
                break;
            case R.id.ride_board_row3:
                if(shownRides.get(1).getDescription().equals("No Ride"))
                    break; //If null Ride
                currentRide = shownRides.get(1);
                openDialogue(shownRides.get(1));
                break;
            case R.id.ride_board_row4:
                if(shownRides.get(2).getDescription().equals("No Ride"))
                    break; //If null Ride
                currentRide = shownRides.get(2);
                openDialogue(shownRides.get(2));
                break;
            case R.id.ride_board_row5:
                if(shownRides.get(3).getDescription().equals("No Ride"))
                    break; //If null Ride
                currentRide = shownRides.get(3);
                openDialogue(shownRides.get(3));
                break;
            case R.id.ride_board_row6:
                if(shownRides.get(4).getDescription().equals("No Ride"))
                    break; //If null Ride
                currentRide = shownRides.get(4);
                openDialogue(shownRides.get(4));
                break;
            case R.id.ride_board_row7:
                if(shownRides.get(5).getDescription().equals("No Ride"))
                    break; //If null Ride
                currentRide = shownRides.get(5);
                openDialogue(shownRides.get(5));
                break;
            case R.id.ride_board_row8:
                if(shownRides.get(6).getDescription().equals("No Ride"))
                    break; //If null Ride
                currentRide = shownRides.get(6);
                openDialogue(shownRides.get(6));
                break;
            case R.id.ride_board_row9:
                if(shownRides.get(7).getDescription().equals("No Ride"))
                    break; //If null Ride
                currentRide = shownRides.get(7);
                openDialogue(shownRides.get(7));
                break;
            case R.id.ride_board_row10:
                if(shownRides.get(8).getDescription().equals("No Ride"))
                    break; //If null Ride
                currentRide = shownRides.get(8);
                openDialogue(shownRides.get(8));
                break;
            case R.id.ride_board_row11:
                if(shownRides.get(9).getDescription().equals("No Ride"))
                    break; //If null Ride
                currentRide = shownRides.get(9);
                openDialogue(shownRides.get(9));
                break;
        }
    }

    /*
        Takes no inputs and returns nothing
        Replaces values in the table
        Values are taken from shownRides
        if the shownRides array has less than 10 Rides inserts null rides with no information
     */
    public void setTableValues() {
        while (shownRides.size() < 10)
            shownRides.add(new Ride(1,2,3,"An","Sm","1:00","11/11/11",1.50,4.2,false,false,"ugly","costco","fun time with friendcake", "empty"));

        //Sets the times in the table
        TextView time1 = (TextView) findViewById(R.id.start_time1);
        time1.setText(shownRides.get(0).getStartTime());
        TextView time2 = (TextView) findViewById(R.id.start_time2);
        time2.setText(shownRides.get(1).getStartTime());
        TextView time3 = (TextView) findViewById(R.id.start_time3);
        time3.setText(shownRides.get(2).getStartTime());
        TextView time4 = (TextView) findViewById(R.id.start_time4);
        time4.setText(shownRides.get(3).getStartTime());
        TextView time5 = (TextView) findViewById(R.id.start_time5);
        time5.setText(shownRides.get(4).getStartTime());
        TextView time6 = (TextView) findViewById(R.id.start_time6);
        time6.setText(shownRides.get(5).getStartTime());
        TextView time7 = (TextView) findViewById(R.id.start_time7);
        time7.setText(shownRides.get(6).getStartTime());
        TextView time8 = (TextView) findViewById(R.id.start_time8);
        time8.setText(shownRides.get(7).getStartTime());
        TextView time9 = (TextView) findViewById(R.id.start_time9);
        time9.setText(shownRides.get(8).getStartTime());
        TextView time10 = (TextView) findViewById(R.id.start_time10);
        time10.setText(shownRides.get(9).getStartTime());

        //Sets the dates in the table
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

        //Sets the descriptions in the table
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

        //Sets the user ratings in the table
        TextView userRating1 = (TextView) findViewById(R.id.user_rating1);
        userRating1.setText(String.valueOf(shownRides.get(0).getUserRating()));
        TextView userRating2 = (TextView) findViewById(R.id.user_rating2);
        userRating2.setText(String.valueOf(shownRides.get(1).getUserRating()));
        TextView userRating3 = (TextView) findViewById(R.id.user_rating3);
        userRating3.setText(String.valueOf(shownRides.get(2).getUserRating()));
        TextView userRating4 = (TextView) findViewById(R.id.user_rating4);
        userRating4.setText(String.valueOf(shownRides.get(3).getUserRating()));
        TextView userRating5 = (TextView) findViewById(R.id.user_rating5);
        userRating5.setText(String.valueOf(shownRides.get(4).getUserRating()));
        TextView userRating6 = (TextView) findViewById(R.id.user_rating6);
        userRating6.setText(String.valueOf(shownRides.get(5).getUserRating()));
        TextView userRating7 = (TextView) findViewById(R.id.user_rating7);
        userRating7.setText(String.valueOf(shownRides.get(6).getUserRating()));
        TextView userRating8 = (TextView) findViewById(R.id.user_rating8);
        userRating8.setText(String.valueOf(shownRides.get(7).getUserRating()));
        TextView userRating9 = (TextView) findViewById(R.id.user_rating9);
        userRating9.setText(String.valueOf(shownRides.get(8).getUserRating()));
        TextView userRating10 = (TextView) findViewById(R.id.user_rating10);
        userRating10.setText(String.valueOf(shownRides.get(9).getUserRating()));
    }

    /*
        Takes an input of type ride and returns nothing
        Creates dialogue box to be opened on click
        Sets text values based on the input ride
     */
    public void openDialogue(final Ride ride) {
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
        text3.setText(ride.getStartTime());

        //Earnings text
        TextView text4 = (TextView) dialog.findViewById(R.id.textView5);
        text4.setText(Integer.toString(ride.getCost()));

        //User id text
        TextView text5 = (TextView) dialog.findViewById(R.id.textView6);
        if(ride.getPassengerUserID() == 0){
            text5.setText(ride.getDriverName());
        }
        else
            text5.setText(ride.getPassengerName());

        //set up Buttons
        //Button to close open dialogue
        Button button = (Button) dialog.findViewById(R.id.Cancel);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        //Button to accept a ride and send information to the database
        final Button button1 = (Button) dialog.findViewById(R.id.Confirm);
        if (MainActivity.boardNumber == 2) {
            if (currentRide.getStart())
                button1.setText("End Ride");
            else
                button1.setText("Start Ride");
        }
        else
            button1.setText("Confirm");


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(MainActivity.boardNumber) {
                    case 0:
                        //write to SQL
                        break;
                    case 1:
                        //write to sql
                    case 2:
                        if (currentRide.getStart()) {
                            currentRide.setEnd(true);
                            reviewDialogue();
                            dialog.dismiss();
                            /*MAKE NEW THINGIE MICHAEL*/
                        } else {
                            currentRide.setStart(true);
                            button1.setText("End Ride");
                            dialog.dismiss();
                        }

                        break;
                    case 3:
                        //
                        break;
                }
            }
        });

        //Show Dialog
        dialog.show();
    }
    public void reviewDialogue(){
        final Dialog dialog2 = new Dialog(RideBoard.this);
        dialog2.setContentView(R.layout.review_layout);

        final Button button2 = dialog2.findViewById(R.id.review_confirm);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog2.dismiss();
            }

        });

        dialog2.show();

    }
}