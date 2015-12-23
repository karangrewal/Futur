package com.csc207.group0758.mytravel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import trips.Itinerary;
import trips.Trip;
import users.Client;
import users.User;

public class ViewBookedItinActivity extends AppCompatActivity {
	
    private Client client;

    @Override
    /**
     * Creates a new ViewBookedItinActivity, initializes Views, unpackages Intents.
     * @param savedInstanceState
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_booked_itin);

        Intent intent = getIntent();
        client = (Client) intent.getSerializableExtra("EXTRA_CLIENT");

        // Personalized header
        TextView header = (TextView) findViewById(R.id.header);
        header.setText("Booked itineraries for " + client.getFirstName() + " "
                + client.getLastName());

        // List all of the booked itineraries
        String[] bookedItineraries = bookedItinerariesAry();

        ListAdapter bookedItinAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, bookedItineraries);
        ListView bookedItinListView = (ListView) findViewById(R.id.bookedItinListView);
        bookedItinListView.setAdapter(bookedItinAdapter);
    }

    /**
     * Return all of the booked itineraries in an array of strings to be displayed.
     * @return
     */
    public String[] bookedItinerariesAry() {
        // If no booked itineraries, default message.
        String[] arrayBookedItin = {"No booked itineraries found"};
        List<Itinerary> bookedItineraries = client.getBookedItineraries();

        if (bookedItineraries.size() > 0) {
            arrayBookedItin = new String[bookedItineraries.size()];
            int index = 0;
            for (Trip itinerary: bookedItineraries) {
                arrayBookedItin[index] = itinerary.displayString();
                index += 1;
            }
        }
        return arrayBookedItin;
    }
}
