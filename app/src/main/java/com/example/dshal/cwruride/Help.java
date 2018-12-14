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

    }

    public Help() {
        super();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        value = parent.getItemAtPosition(position).toString();
        spinnerChoice(value);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void spinnerChoice(String v){
        TextView help = findViewById(R.id.helpText);
        switch(v) {
            case "Posting a Ride":
                help.setText(R.string.posting_ride);
                break;

            case "Accepting a Ride":
                help.setText(R.string.accepting_ride);
                break;

            case "Starting and Ending a Ride":
                help.setText(R.string.start_end);
                break;

            case "About":
                help.setText(R.string.about);
                break;

            case "Accounts":
                help.setText(R.string.accounts);
                break;

            case "Buzzwords":
                help.setText(R.string.buzzwords_help);
                break;
        }
    }

}
