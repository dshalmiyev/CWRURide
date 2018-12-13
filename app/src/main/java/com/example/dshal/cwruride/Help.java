package com.example.dshal.cwruride;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class Help extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private String value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        Spinner helpSpinner = (Spinner) findViewById(R.id.helpSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.helpList, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        helpSpinner.setAdapter(adapter);
        helpSpinner.setOnItemSelectedListener(this);
        spinnerChoice(value);

    }

    public Help() {
        super();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        value = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void spinnerChoice(String v){
        TextView postHelp = findViewById(R.id.postRideHelp);
        TextView acceptHelp = findViewById(R.id.acceptRideHelp);
        TextView startEndHelp = findViewById(R.id.startEndHelp);
        if(v.equals("Posting a Ride")){
            postHelp.setVisibility(View.VISIBLE);
            acceptHelp.setVisibility(View.GONE);
            startEndHelp.setVisibility(View.GONE);
        }
        if(v.equals("Accepting a Ride")){
            acceptHelp.setVisibility(View.VISIBLE);
            startEndHelp.setVisibility(View.GONE);
            postHelp.setVisibility(View.GONE);
        }
        if(v.equals("Starting and Ending a Ride")){
            startEndHelp.setVisibility(View.VISIBLE);
            acceptHelp.setVisibility(View.GONE);
            postHelp.setVisibility(View.GONE);
        }
    }

}
