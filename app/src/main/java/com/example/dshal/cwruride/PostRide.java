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

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.TravelMode;

import org.joda.time.DateTime;

import java.io.IOException;

public class PostRide extends AppCompatActivity implements AdapterView.OnItemSelectedListener, OnMapReadyCallback {

    private GoogleMap mMap;
    String storedTextsp1 = "Time Selecter";
    String storedTextsp2 = "AM/PM Selecter!";
    int spinner1ID, spinner2ID;
    boolean Pass_Drive; //Driver = True, Passenger = False


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_ride);

        //Map
        MapFragment map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map));
        map.getMapAsync(this);

        //Spinner1
        final Spinner spinner1 = findViewById(R.id.driveLength);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.timelist,android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);
        spinner1.setOnItemSelectedListener(this);
        spinner1ID = spinner1.getId();

        final Spinner driveLength = findViewById(R.id.driveLength);

        //Description textBox
        final EditText descriptionBox = findViewById(R.id.description);

        //Datepicker textBox
        final EditText editTextFromDate = (EditText) findViewById(R.id.datePick);
        setDate fromDate = new setDate(editTextFromDate, this);

        //Timepicker textBox
        final EditText editTextFromTime = (EditText) findViewById(R.id.timePick);
        setTime fromTime = new setTime(editTextFromTime, this);

        //Error textView
        final TextView errorText = (TextView) findViewById(R.id.errorText);
        errorText.setVisibility(View.INVISIBLE);

        //Destination textBox
        final EditText destination = (EditText) findViewById(R.id.dropoffLocation);
        final EditText pickup = (EditText) findViewById(R.id.pickupLocation);

        destination.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                //calculateRoute(destination,pickup);
            }
        });

        pickup.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                //calculateRoute(destination,pickup);
            }
        });

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
                    text4.setText("$14.00");
                }
                else {
                    text4.setText("$14.00");
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
                        new RemoteConnection().addRide(MainActivity.testUser.getUserId(), MainActivity.testUser.getFullName(),editTextFromTime.getEditableText().toString(),
                                editTextFromDate.getEditableText().toString(), driveLength.getSelectedItem().toString(),MainActivity.testUser.getFeedbackValue(),0,0,
                                pickup.getEditableText().toString(),destination.getEditableText().toString(),descriptionBox.getEditableText().toString());
                        System.out.println("You made it this far. congratz");
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
                    String check = "time format error" + editTextFromTime.getEditableText().toString();
                    errorText.setText(check);
                    errorText.setVisibility(View.VISIBLE);
                    return;
                }

                else if (!FieldChecking.checkDate(editTextFromDate.getEditableText().toString())) {
                    String check = "date format error" + editTextFromDate.getEditableText().toString();
                    errorText.setText(check);
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
                    text4.setText("$14.00");
                }
                else {
                    text4.setText("$14.00");
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
                        new RemoteConnection().addRequest(MainActivity.testUser.getUserId(), MainActivity.testUser.getFullName(),editTextFromTime.getEditableText().toString(),
                                editTextFromDate.getEditableText().toString(), driveLength.getSelectedItem().toString(),MainActivity.testUser.getFeedbackValue(),0,0,
                                pickup.getEditableText().toString(),destination.getEditableText().toString(),descriptionBox.getEditableText().toString());
                    }
                });

                //Show Dialog
                dialog.show();

            }
        });
    }

    private GeoApiContext getGeoContext() {
        GeoApiContext geoApiContext = new GeoApiContext();
        return geoApiContext.setQueryRateLimit(3).setApiKey("AIzaSyBSxxDKw8dQTDxgOq5lVxiFBZ44VM1rzJQ");
    }

    private void calculateRoute(EditText pickupText, EditText destinationText) {
        if (pickupText.getEditableText().toString().equals("") || destinationText.getEditableText().toString().equals("")) {
            //Do nothing
        }
        else {
            String origin = pickupText.getEditableText().toString();
            String destination = destinationText.getEditableText().toString();
            DateTime now = new DateTime();
            try {
                DirectionsResult result = DirectionsApi.newRequest(getGeoContext()).mode(TravelMode.DRIVING).origin(origin).destination(destination).departureTime(now).await();
                addMarkersToMap(result,mMap);
            }
            catch (com.google.maps.errors.ApiException api) {
                System.out.println("API exception");
                api.printStackTrace();
            }
            catch (InterruptedException ie){
                System.out.println("Interrupted Exception");
                ie.printStackTrace();
            }
            catch (IOException ioe){
                System.out.println("IO Exception");
                ioe.printStackTrace();
            }
        }
    }

    private void addMarkersToMap(DirectionsResult results, GoogleMap mMap) {
        mMap.addMarker(new MarkerOptions().position(new LatLng(results.routes[0].legs[0].startLocation.lat,results.routes[0].legs[0].startLocation.lng)).title(results.routes[0].legs[0].startAddress));
        mMap.addMarker(new MarkerOptions().position(new LatLng(results.routes[0].legs[0].endLocation.lat,results.routes[0].legs[0].endLocation.lng)).title(results.routes[0].legs[0].endAddress));
    }

    //Map code
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

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
