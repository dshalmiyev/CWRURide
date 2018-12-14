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
        TextView help = findViewById(R.id.helpText);
        if(v.equals("Posting a Ride")){
            help.setText(R.string.posting_ride);
        }
        if(v.equals("Accepting a Ride")){
            help.setText(R.string.accepting_ride);
        }
        if(v.equals("Starting and Ending a Ride")){
            help.setText(R.string.start_end);
        }
    }

}
