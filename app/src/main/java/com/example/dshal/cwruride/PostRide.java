package com.example.dshal.cwruride;

import android.Manifest;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PostRide extends AppCompatActivity implements
        OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener{

    private static GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    LocationRequest mLocationRequest;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    LatLng[] MarkerPoints = new LatLng[2];
    Marker[] Markers = new Marker[2];
    String originAddress;
    String destinationAddress;
    static String totalDistanceString;
    static Double totalDistance;
    static String totalTimeString;
    static Double totalTime;
    static TextView rideDistance;
    static TextView rideTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_ride);

        checkLocationPermission();

        //Map
        MapFragment map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map));
        map.getMapAsync(this);

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

        rideDistance = (TextView) findViewById(R.id.ride_distance);
        rideTime = (TextView) findViewById(R.id.ride_time);

        //Origin textBox
        PlaceAutocompleteFragment originFragment = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.origin_fragment);
        originFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {

                originAddress = place.getAddress().toString();
                MarkerOptions currentMarker = new MarkerOptions();
                currentMarker.position(place.getLatLng());
                currentMarker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                if (MarkerPoints[0] == null) {
                    Markers[0] = mMap.addMarker(currentMarker);
                }
                else {
                    //Clear map
                    mMap.clear();

                    //Add selected place marker
                    Markers[0] = mMap.addMarker(currentMarker);

                    //If destination marker existed, add it back
                    if (Markers[1] != null) {
                        MarkerOptions previousMarker = new MarkerOptions();
                        previousMarker.position(MarkerPoints[1]);
                        previousMarker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                        mMap.addMarker(previousMarker);
                    }
                }
                MarkerPoints[0] = place.getLatLng();
                mMap.moveCamera(CameraUpdateFactory.newLatLng(MarkerPoints[0]));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(11));

                if (MarkerPoints[1] != null){
                    LatLng origin = MarkerPoints[0];
                    LatLng dest = MarkerPoints[1];

                    String url = getUrl(origin, dest);
                    Log.d("onMapClick", url.toString());
                    FetchUrl FetchUrl = new FetchUrl();

                    // Start downloading json data from Google Directions API
                    FetchUrl.execute(url);
                    rideDistance.setText("Distance = " + totalDistanceString);
                    rideTime.setText("Time = " + totalTimeString);
                }
            }

            @Override
            public void onError(Status status) {
                //Print to Error Message
            }
        });

        //Destination textBox
        PlaceAutocompleteFragment destinationFragment = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.destination_fragment);
        destinationFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {

                destinationAddress = place.getAddress().toString();
                MarkerOptions currentMarker = new MarkerOptions();
                currentMarker.position(place.getLatLng());
                currentMarker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                if (MarkerPoints[1] == null) {
                    Markers[1] = mMap.addMarker(currentMarker);
                }
                else {
                    //Clear map
                    mMap.clear();

                    //Add selected place marker
                    Markers[1] = mMap.addMarker(currentMarker);

                    //If destination marker existed, add it back
                    if (Markers[0] != null) {
                        MarkerOptions previousMarker = new MarkerOptions();
                        previousMarker.position(MarkerPoints[0]);
                        previousMarker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                        mMap.addMarker(previousMarker);
                    }
                }
                MarkerPoints[1] = place.getLatLng();
                mMap.moveCamera(CameraUpdateFactory.newLatLng(MarkerPoints[1]));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(11));

                if (MarkerPoints[0] != null){
                    LatLng origin = MarkerPoints[0];
                    LatLng dest = MarkerPoints[1];

                    String url = getUrl(origin, dest);
                    Log.d("onMapClick", url.toString());
                    FetchUrl FetchUrl = new FetchUrl();

                    // Start downloading json data from Google Directions API
                    FetchUrl.execute(url);

                    //Set TextViews
                    rideDistance.setText("Distance = " + totalDistanceString);
                    rideTime.setText("Time = " + totalTimeString);
                }
            }

            @Override
            public void onError(Status status) {
                //Print to Error Message
            }
        });

        //Driver Button
        Button driver = (Button)findViewById(R.id.driver);
        driver.setOnClickListener(new View.OnClickListener() {
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
                Double totalCost = 3 + totalDistance*.5 + totalTime*.15;
                DecimalFormat decimalFormat = new DecimalFormat("$#.##");
                decimalFormat.format(totalCost);
                text4.setText(totalCost.toString());

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
                                editTextFromDate.getEditableText().toString(), totalTimeString,MainActivity.testUser.getFeedbackValue(),0,0,
                                originAddress,destinationAddress,descriptionBox.getEditableText().toString());
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
                Double totalCost = totalDistance * 7;
                text4.setText(totalCost.toString());

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
                                editTextFromDate.getEditableText().toString(), totalTimeString,MainActivity.testUser.getFeedbackValue(),0,0,
                                originAddress,destinationAddress,descriptionBox.getEditableText().toString());
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

        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        }
        else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng point) {
                //if 2 locations selected
                if (MarkerPoints[0] != null && MarkerPoints[1] != null) {
                    MarkerPoints[0] = null;
                    MarkerPoints[1] = null;
                    Markers[0] = null;
                    Markers[1] = null;
                    mMap.clear();
                }

                //add point
                //add origin point
                if (MarkerPoints[0] == null) {
                    MarkerPoints[0] = point;
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(point);
                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                    Markers[0] = mMap.addMarker(markerOptions);
                }
                //add destination point
                else {
                    MarkerPoints[1] = point;
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(point);
                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                    Markers[1] = mMap.addMarker(markerOptions);
                }

                if (MarkerPoints[0] != null && MarkerPoints[1] != null) {
                    LatLng origin = MarkerPoints[0];
                    LatLng dest = MarkerPoints[1];

                    String url = getUrl(origin, dest);
                    Log.d("onMapClick", url.toString());
                    FetchUrl FetchUrl = new FetchUrl();

                    // Start downloading json data from Google Directions API
                    FetchUrl.execute(url);
                    //move map camera
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(origin));
                    mMap.animateCamera(CameraUpdateFactory.zoomTo(11));
                }
            }
        });
    }

    private static String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();
            Log.d("downloadUrl", data.toString());
            br.close();

        } catch (Exception e) {
            Log.d("Exception", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    private static class FetchUrl extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... url) {

            // For storing data from web service
            String data = "";

            try {
                // Fetching the data from web service
                data = downloadUrl(url[0]);
                Log.d("Background Task data", data);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();

            // Invokes the thread for parsing the JSON data
            parserTask.execute(result);

        }
    }

    private static class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                //Log.d("ParserTask",jsonData[0].toString());
                DataParser parser = new DataParser();
                //Log.d("ParserTask", parser.toString());

                // Starts parsing data
                routes = parser.parse(jObject);
                //Log.d("ParserTask","Executing routes");
                //Log.d("ParserTask",routes.toString());

                totalDistanceString = parser.totalDistanceString;
                totalDistance = parser.totalDistance;
                totalTime = parser.totalTime;
                totalTimeString = parser.totalTimeString;

            } catch (Exception e) {
                //Log.d("ParserTask",e.toString());
                e.printStackTrace();
            }
            return routes;
        }


        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points;
            PolylineOptions lineOptions = null;

            // Traversing through all the routes
            for (int i = 0; i < result.size(); i++) {
                points = new ArrayList<>();
                lineOptions = new PolylineOptions();

                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);

                // Fetching all the points in i-th route
                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width(10);
                lineOptions.color(Color.BLUE);

                Log.d("onPostExecute","onPostExecute lineoptions decoded");
            }

            //final TextView rideDistance = (TextView) findViewById(R.id.ride_distance);
            //final TextView rideTime = (TextView) findViewById(R.id.ride_time);
            rideDistance.setText("Distance = " + totalDistanceString);
            rideTime.setText("Time = " + totalTimeString);

            // Drawing polyline in the Google Map for the i-th route
            if(lineOptions != null) {
                mMap.addPolyline(lineOptions);
            }
            else {
                Log.d("onPostExecute","without Polylines drawn");
            }
        }
    }

    //writes URL
    private String getUrl(LatLng origin, LatLng dest) {

        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        String sensor = "sensor=false";
        String parameters = str_origin + "&" + str_dest + "&" + sensor;
        String output = "json";
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + getString(R.string.google_maps_key);

        return url;
    }

    public synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    // Added Google maps implementations
    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }

        //Place current location marker
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        //MarkerOptions markerOptions = new MarkerOptions();
        //markerOptions.position(latLng);
        //markerOptions.title("Current Position");
        //markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        //mCurrLocationMarker = mMap.addMarker(markerOptions);

        //move map camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(11));

        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    //permissions for location
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public boolean checkLocationPermission(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }


    //Permissions for location
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // Permission was granted.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }

                } else {

                    // Permission denied, Disable the functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
            }

            // other 'case' lines to check for other permissions this app might request.
            //You can add here other case statements according to your requirement.
        }
    }
}
