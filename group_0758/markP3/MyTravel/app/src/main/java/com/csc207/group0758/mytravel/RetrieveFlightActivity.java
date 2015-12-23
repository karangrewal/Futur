package com.csc207.group0758.mytravel;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import info.NoSuchFlightException;
import trips.Flight;
import users.Administrator;

public class RetrieveFlightActivity extends AppCompatActivity {
    // Assume a given administrator
    Administrator admin;
    String flightNum;

    @Override
    /**
     * Creates a new RetrieveFlightActivity, initializes Views, unpackages Intents.
     * @param savedInstanceState
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve_flight);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        admin = (Administrator) intent.getSerializableExtra("EXTRA_ADMIN");
    }

    /**
     * Check if the given flight exists and, if so, enter the EditFlight activity.
     * @param view
     */
    public void viewAndEditFlightInfo(View view) {
        EditText result = (EditText) findViewById(R.id.flight_Num);
        flightNum = result.getText().toString();

        try {
            Flight flight = admin.getFlight(Integer.parseInt(flightNum));
            Intent intent = new Intent(this, EditFlightActivity.class);
            intent.putExtra("EXTRA_ADMIN", admin);
            intent.putExtra("EXTRA_FLIGHT", flight);
            startActivity(intent);
        } catch (NoSuchFlightException ex) {
            // If no flight, raise error message
            TextView textView = (TextView) findViewById(R.id.help_retrieve);
            textView.setTextColor(Color.RED);
            textView.setText("No such flight");
        }catch (NumberFormatException ex) {
            // If string doesn't contain an integer, raise error message
            TextView textView = (TextView) findViewById(R.id.help_retrieve);
            textView.setTextColor(Color.RED);
            textView.setText("Enter a valid flight number");
        }
    }
}
