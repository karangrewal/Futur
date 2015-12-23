package com.csc207.group0758.mytravel;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import info.NoSuchClientException;
import users.Administrator;
import users.Client;

public class AdminBookingActivity extends AppCompatActivity {
    // Admin assumed since booking for a client
    Administrator admin;

    /**
     * Gets the serializable admin and displays the selected itinerary.
     * @param savedInstanceState
     */
    @Override
    /**
     * Creates a new AddFlightActivity, initializes Views, unpackages Intents.
     * @param savedInstanceState
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_booking);

        Intent intent = getIntent();
        admin = (Administrator) intent.getSerializableExtra("EXTRA_ADMIN");


        // Use listview to make the itinerary scrollable due to variable length
        String[] selectedItinerary = new String[]{"Selected "
                + admin.getSelectedItinerary().displayString()};
        ListView itineraryInfo = (ListView) findViewById(R.id.selected_itinerary);

        ListAdapter selectedItinAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, selectedItinerary);
        itineraryInfo.setAdapter(selectedItinAdapter);
    }

    /**
     * Books the selected itinerary for a given client. If the client does
     * not exist, show an error message.
     * @param view
     */
    public void bookForClient(View view) {
        EditText enteredEmail = (EditText) findViewById(R.id.client_email);
        String givenEmail = enteredEmail.getText().toString();
        TextView textView = (TextView) findViewById(R.id.help_booking);
        try {
            // If the client is found, give a message, book the itinerary,
            // and move back to the admin main menu
            Client client = admin.getClient(givenEmail);
            admin.bookItineraryForClient(client);
            textView.setTextColor(Color.parseColor("#FF088A4B"));
            textView.setText("Booked itinerary for " + client.getFirstName()
                    + " " + client.getLastName());
            Intent intent = new Intent(this, AdminMenuActivity.class);
            intent.putExtra("EXTRA_ADMIN", admin);
            startActivity(intent);

        } catch (NoSuchClientException ex) {
            // Error message
            textView.setTextColor(Color.RED);
            textView.setText("No such client");
        }
    }
}
