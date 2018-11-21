package com.example.dshal.cwruride;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class AccountDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_details);

        MainActivity.testUser = new User();

        final TextView firstName = (TextView) findViewById(R.id.firstName);
        firstName.setText(MainActivity.testUser.getFirstName());

        final TextView lastName = (TextView) findViewById(R.id.lastName);
        lastName.setText(MainActivity.testUser.getLastName());

        final TextView phoneNumber = (TextView) findViewById(R.id.phoneNumber);
        phoneNumber.setText(MainActivity.testUser.getPhoneNumber());

        final TextView userRating = (TextView) findViewById(R.id.userRating);
        userRating.setText("Rating: " + Double.toString(MainActivity.testUser.getFeedbackValue()));

        final TextView carYear = (TextView) findViewById(R.id.carYear);
        final TextView carMake = (TextView) findViewById(R.id.carMake);
        final TextView carModel = (TextView) findViewById(R.id.carModel);
        final TextView carPlate = (TextView) findViewById(R.id.carPlate);
        final TextView userLicense = (TextView) findViewById(R.id.userLicense);

        final Button addCar = (Button) findViewById(R.id.addCar);
        addCar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final Dialog dialog = new Dialog(AccountDetails.this);
                dialog.setContentView(R.layout.add_car_dialog);

                final EditText editTextCarYear = (EditText) dialog.findViewById(R.id.editTextCarYear);
                final EditText editTextCarMake = (EditText) dialog.findViewById(R.id.editTextCarMake);
                final EditText editTextCarModel = (EditText) dialog.findViewById(R.id.editTextCarModel);
                final EditText editTextCarPlate = (EditText) dialog.findViewById(R.id.editTextCarPlate);
                final EditText editTextUserLicense = (EditText) dialog.findViewById(R.id.editTextUserLicense);

                final Button confirmAddCar = (Button) dialog.findViewById(R.id.confirmAddCar);
                confirmAddCar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        carYear.setText(editTextCarYear.getEditableText().toString());
                        carMake.setText(editTextCarMake.getEditableText().toString());
                        carModel.setText(editTextCarModel.getEditableText().toString());
                        carPlate.setText(editTextCarPlate.getEditableText().toString());
                        userLicense.setText(editTextUserLicense.getEditableText().toString());
                        addCar.setText("Remove Car");
                        dialog.dismiss();
                    }
                });
                if (carMake.getText().equals(""))
                    dialog.show();
                else {
                    carYear.setText("No Car Available");
                    carMake.setText("");
                    carModel.setText("");
                    carPlate.setText("");
                    userLicense.setText("");
                    addCar.setText("Add Car");
                }
            }
        });
    }
}
