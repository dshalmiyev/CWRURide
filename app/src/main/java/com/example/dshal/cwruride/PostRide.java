package com.example.dshal.cwruride;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.w3c.dom.Text;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostRide extends AppCompatActivity implements AdapterView.OnItemSelectedListener, OnMapReadyCallback {

    private GoogleMap mMap;
    String storedTextsp1 = "Time Selecter";
    String storedTextsp2 = "AM/PM Selecter!";
    String storedTextsp3 = "Description Text";
    int spinner0ID, spinner1ID, spinner2ID;
    boolean Pass_Drive; //Driver = True, Passenger = False


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_ride);

        //Map
        MapFragment map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map));
        map.getMapAsync(this);

        //Spinner1
        Spinner spinner1 = findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.timelist,android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);
        spinner1.setOnItemSelectedListener(this);
        spinner1ID = spinner1.getId();

        //Description textBox
        final EditText descriptionBox = findViewById(R.id.editText);

        //Datepicker textBox
        final EditText editTextFromDate = (EditText) findViewById(R.id.editText3);
        setDate fromDate = new setDate(editTextFromDate, this);

        //Timepicker textBox
        final EditText editTextFromTime = (EditText) findViewById(R.id.editText4);
        setTime fromTime = new setTime(editTextFromTime, this);

        //Error textView
        final TextView errorText = (TextView) findViewById(R.id.errorText);
        errorText.setVisibility(View.INVISIBLE);

        //Driver Button
        Button driver = (Button)findViewById(R.id.driver);
        driver.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!FieldChecking.checkTime(editTextFromTime.getEditableText().toString())) {
                    String problems = "Time Format Error "+editTextFromTime.getEditableText().toString();
                    errorText.setText(problems);
                    errorText.setVisibility(View.VISIBLE);
                    return;
                }

                else if (!FieldChecking.checkDate(editTextFromDate.getEditableText().toString())) {
                    errorText.setText("Date Format Error");
                    errorText.setVisibility(View.VISIBLE);
                    return;
                }

                else
                    errorText.setVisibility(View.INVISIBLE);

                Pass_Drive = true;

                final Dialog dialog = new Dialog(PostRide.this);
                dialog.setContentView(R.layout.fragment_confirm_dialog);

                // set up the text boxes
                //description text
                TextView text = (TextView) dialog.findViewById(R.id.textView2);
                text.setText(descriptionBox.getText().toString());

                //Date text
                TextView text2 = (TextView) dialog.findViewById(R.id.textView3);
                text2.setText(editTextFromDate.getEditableText().toString());

                //Time text
                TextView text3 = (TextView) dialog.findViewById(R.id.textView4);
                text3.setText(editTextFromTime.getEditableText().toString());

                //Earnings text
                TextView text4 = (TextView) dialog.findViewById(R.id.textView5);
                if (Pass_Drive == true) {
                    text4.setText("Cost = OVER 9000!");
                }
                else {
                    text4.setText("Earnings = OVER 9000!");
                }

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
                        //SQL connection
                        /*Connection SQLConnect = null;
                        SQLConnect = RemoteConnection.getRemoteConnection();
                        if(SQLConnect == null) {
                            Toast.makeText(PostRide.this,"You lost again",Toast.LENGTH_SHORT).show();
                        }
                        */
                    }
                });

                //Show Dialog
                dialog.show();
            }
        });

        //Passenger button
        Button passenger = (Button)findViewById(R.id.passenger);
        passenger.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                if (!FieldChecking.checkTime(editTextFromTime.getEditableText().toString())) {
                    errorText.setText("Time Format Error");
                    errorText.setVisibility(View.VISIBLE);
                    return;
                }

                else if (!FieldChecking.checkDate(editTextFromDate.getEditableText().toString())) {
                    errorText.setText("Date Format Error");
                    errorText.setVisibility(View.VISIBLE);
                    return;
                }

                else
                    errorText.setVisibility(View.INVISIBLE);

                Pass_Drive = false;

                //Create Dialog
                final Dialog dialog = new Dialog(PostRide.this);
                dialog.setContentView(R.layout.fragment_confirm_dialog);

                //Dialog Description text
                TextView text = (TextView) dialog.findViewById(R.id.textView2);
                text.setText(descriptionBox.getText().toString());

                //Dialog Date text
                TextView text2 = (TextView) dialog.findViewById(R.id.textView3);
                text2.setText(editTextFromDate.getEditableText().toString());

                //Dialog Time text
                TextView text3 = (TextView) dialog.findViewById(R.id.textView4);
                text3.setText(editTextFromTime.getEditableText().toString());

                //Dialog Cost text
                TextView text4 = (TextView) dialog.findViewById(R.id.textView5);
                if (Pass_Drive == false) {
                    text4.setText("Cost = OVER 9000!");
                }
                else {
                    text4.setText("Earnings = OVER 9000!");
                }

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
                        /*Connection SQLConnect = null;
                        SQLConnect = RemoteConnection.getRemoteConnection();
                        if(SQLConnect == null) {
                            Toast.makeText(PostRide.this,"You lost again",Toast.LENGTH_SHORT).show();
                        }
                        */
                    }
                });

                //Show Dialog
                dialog.show();

            }
        });
    }

    //Map code
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng point) {
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(point));
            }
        });
    }

    //Selecting Values for Spinners
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(),text,Toast.LENGTH_SHORT).show();
        if(parent.getId() == spinner1ID) {//need to determine how ID's work
            storedTextsp1 = text;
        }
        if(parent.getId() == spinner2ID) {//need to determine how ID's work
            storedTextsp2 = text;
        }
    }

    //Selecting Nothing for Spinners
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //do nothing
    }
}
