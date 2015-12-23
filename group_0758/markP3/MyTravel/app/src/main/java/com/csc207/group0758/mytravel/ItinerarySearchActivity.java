package com.csc207.group0758.mytravel;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import info.NoSuchClientException;
import trips.Itinerary;
import trips.Trip;
import users.Administrator;
import users.Client;
import users.User;

public class ItinerarySearchActivity extends AppCompatActivity {
    // Assume a generic user and adminBuddy (if Client)
    private User user;
    private Administrator adminBuddy;

    private TextView header;
    private int selectedPosition = -1;
    private int numberOfItineraries;

    @Override
    /**
     * Creates a new ItinerarySearchActivity, initializes Views, unpackages Intents.
     * @param savedInstanceState
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itinerary_search);
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("EXTRA_USER");
        if (user instanceof Client) {
            adminBuddy = (Administrator) intent.getSerializableExtra("EXTRA_ADMIN_BUDDY");
        }
        numberOfItineraries = user.getRecentlySearchedTrips().size();

        String[] searchCriteria = intent.getStringArrayExtra("search_information");

        String origin = searchCriteria[0];
        String destination = searchCriteria[1];
        String date = searchCriteria[2];

        // Search information has been gathered
        header = (TextView) findViewById(R.id.header);
        header.setText("Itinerary from " + origin + " to " + destination);

        String[] searchResults = this.searchResults(origin, destination, date);

        // Populate the list with itinerary search results
        ListAdapter itinerariesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, searchResults);
        ListView itinerariesListView = (ListView) findViewById(R.id.itinerariesListView);
        itinerariesListView.setAdapter(itinerariesAdapter);
        // If a list item is clicked, update the selected itinerary position
        // and header message.
        itinerariesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (numberOfItineraries == 0) {return;}
                selectedPosition = position;
                header.setText("Itinerary " + (position + 1) + " selected");
                header.setTextColor(0xFF088A4B);
            }
        });
    }

    /**
     * Return the string representation of the search results to populate the
     * list of itineraries.
     * @param origin
     * @param destination
     * @param date
     * @return
     */
    public String[] searchResults(String origin, String destination, String date) {
        String[] results = {"No itineraries found"};
        if (numberOfItineraries > 0) {
            results = new String[numberOfItineraries];
            int index = 0;
            for (Trip itinerary: user.getRecentlySearchedTrips()) {
                results[index] = (index + 1) + ". " + itinerary.displayString();
                index += 1;
            }
        }
        return results;
    }

    /**
     * Book an itinerary if selected, otherwise indicate to select one.
     *
     * @param view
     */
    public void bookItinerary(View view) {
        if (selectedPosition == -1) {
            header.setText("Select an itinerary to book");
            header.setTextColor(Color.RED);
            return;
        }

        Itinerary selectedItinerary = (Itinerary) user.getRecentlySearchedTrips().get(selectedPosition);
        user.selectSearchedItinerary(selectedPosition);
        if (user instanceof Administrator) {
            // If admin, move to the AdminBooking menu with the itinerary selected
            // since a client has not been indicated yet
            Intent intent = new Intent(this, AdminBookingActivity.class);
            intent.putExtra("EXTRA_ADMIN", (Administrator) user);
            startActivity(intent);
        }
        else if (user instanceof Client) {
            // If client, get the adminBuddy to book for the stored client
            // and return to the Client Main menu.
            Client updatedClient = (Client) user;
            try {updatedClient = adminBuddy.getClient(((Client) user).getEmail());}
            catch (NoSuchClientException e) {};

            adminBuddy.bookItineraryForClient(updatedClient, user.getSelectedItinerary());
            header.setText(updatedClient.getFirstName() + " booked itinerary "
                    + (selectedPosition + 1));

            Intent intent = new Intent(this, ClientMenuActivity.class);
            intent.putExtra("EXTRA_CLIENT", updatedClient);
            intent.putExtra("EXTRA_ADMIN", adminBuddy);
            startActivity(intent);
        }
        // Reset selection
        selectedPosition = -1;
    }
}
