package com.csc207.group0758.mytravel;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import info.FlightManager;
import info.SearchTool;
import trips.Flight;
import trips.Trip;
import users.Administrator;
import users.Client;
import users.User;

import java.util.List;

public class FlightSearchActivity extends AppCompatActivity {
    // Assume a generic user and a adminBuddy (if a client)
    private User user;
    private Administrator adminBuddy;

    @Override
    /**
     * Takes a generic user and an adminBuddy (if user is a Client). Display
     * a header specific to the search conducted. Populate a list of flights
     * found.
     *
     * @param savedInstanceState
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("EXTRA_USER");
        if (user instanceof Client) {
            adminBuddy = (Administrator) intent.getSerializableExtra("EXTRA_ADMIN_BUDDY");
        }

        String[] searchCriteria = intent.getStringArrayExtra("search_information");

        String origin = searchCriteria[0];
        String destination = searchCriteria[1];
        String date = searchCriteria[2];

        TextView header = (TextView) findViewById(R.id.header);
        header.setText("Flight from " + origin + " to " + destination);


        String[] searchResults = this.searchResults(origin, destination, date);

        ListAdapter flightsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, searchResults);
        ListView flightsListView = (ListView) findViewById(R.id.flightsListView);
        flightsListView.setAdapter(flightsAdapter);
    }

    /**
     * Return a list of strings to fill the list of flights found. If no flights
     * found, return an message string.
     *
     * @param origin
     * @param destination
     * @param date
     * @return
     */
    public String[] searchResults(String origin, String destination, String date) {
        // default to no flights found
        String[] results = {"No flights found"};
        int numberOfFlights = user.getRecentlySearchedTrips().size();

        // If there are flights found, replace the string and return it.
        if (numberOfFlights > 0) {
            results = new String[numberOfFlights];
            int index = 0;
            for (Trip flight: user.getRecentlySearchedTrips()) {
                results[index] = flight.displayString();
                index += 1;
            }
        }
        return results;
    }
}
