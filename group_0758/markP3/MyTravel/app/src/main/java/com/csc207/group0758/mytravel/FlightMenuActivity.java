package com.csc207.group0758.mytravel;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import users.Administrator;
import users.Client;
import users.User;

public class FlightMenuActivity extends AppCompatActivity {
    // Assume a generic user and a possible adminBuddy (if client)
    private User user;
    private Administrator adminBuddy;
    
    @Override
    /**
     * Creates a new FlightMenuActivity, initializes Views, unpackages Intents.
     * @param savedInstanceState
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("EXTRA_USER");
        if (user instanceof Client) {
            adminBuddy = (Administrator) intent.getSerializableExtra("EXTRA_ADMIN_BUDDY");
        }
    }

    /**
     * Conducts a search on flights by the user. Pass on the relevant users to a
     * search results menu.
     * @param view
     */
    public void searchFlights(View view) {
        Intent intent = new Intent(this, FlightSearchActivity.class);

        // Get search criteria
        EditText originText = (EditText) findViewById(R.id.origin);
        String origin = originText.getText().toString();
        EditText destinationText = (EditText) findViewById(R.id.destination);
        String destination = destinationText.getText().toString();
        EditText dateText = (EditText) findViewById(R.id.date);
        String date = dateText.getText().toString();

        // Conduct the search and start the next activity.
        user.searchAvailableFlights(date, origin, destination);
        String[] searchCriteria = new String[] {origin, destination, date};
        intent.putExtra("search_information", searchCriteria); // Add name argument (1) to putExtra
        intent.putExtra("EXTRA_USER", user);
        if (user instanceof Client) {
           intent.putExtra("EXTRA_ADMIN_BUDDY", adminBuddy);
        }
        startActivity(intent);
    }



}
