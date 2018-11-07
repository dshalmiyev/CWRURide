package com.example.dshal.cwruride;

import android.app.TimePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.TimePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class setTime implements View.OnFocusChangeListener, TimePickerDialog.OnTimeSetListener{

    private EditText editText;
    private Calendar myCalendar;

    public setTime (EditText editText, Context ctx){
        this.editText = editText;
        this.editText.setOnFocusChangeListener(this);
        myCalendar = Calendar.getInstance();
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(hasFocus){
            new TimePickerDialog(v.getContext(), this,
                    myCalendar.get(Calendar.HOUR_OF_DAY),
                    myCalendar.get(Calendar.MINUTE),
                    false).show();
        }
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String myFormat = "hh:mm aa"; //In which you need put here
        SimpleDateFormat sdformat = new SimpleDateFormat(myFormat, Locale.US);
        myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        myCalendar.set(Calendar.MINUTE, minute);
        editText.setText(sdformat.format(myCalendar.getTime()));
    }
}

