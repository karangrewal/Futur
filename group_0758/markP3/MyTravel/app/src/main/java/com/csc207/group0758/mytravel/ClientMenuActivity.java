package com.csc207.group0758.mytravel;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;

import info.ClientManager;
import users.Administrator;
import users.Client;
import users.User;

public class ClientMenuActivity extends AppCompatActivity {
    // Assume a client and background admin to track changes
    private Client client;
    private Administrator adminBuddy;

    /**
     * Get the client and admin buddy and set up a personalized welcome screen
     * @param savedInstanceState
     */
    @Override
    /**
     * Creates a new AddFlightActivity, initializes Views, unpackages Intents.
     * @param savedInstanceState
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_menu);

        Intent intent = getIntent();
        client = (Client) intent.getSerializableExtra("EXTRA_CLIENT");
        adminBuddy = (Administrator) intent.getSerializableExtra("EXTRA_ADMIN");
        TextView welcomeMessage = (TextView) findViewById(R.id.welcome_text);
        welcomeMessage.setText(String.format("Welcome\n%s %s!",
                client.getFirstName(), client.getLastName()));
    }

    /**
     * Open the user-general flight menu.
     * @param view
     */
    public void openFlightMenu(View view) {
        Intent intent = new Intent(this, FlightMenuActivity.class);
        intent.putExtra("EXTRA_USER", client);
        intent.putExtra("EXTRA_ADMIN_BUDDY", adminBuddy);
        startActivity(intent);
    }

    /**
     * Redirects to ItineraryMenuActivity.
     * @param view
     */
    public void openItineraryMenu(View view) {
        Intent intent = new Intent(this, ItineraryMenuActivity.class);
        intent.putExtra("EXTRA_USER", client);
        intent.putExtra("EXTRA_ADMIN_BUDDY", adminBuddy);
        startActivity(intent);
    }

    /**
     * Open the booked itineraries activity.
     * @param view
     */
    public void viewBookedItineraries(View view) {
        Intent intent = new Intent(this, ViewBookedItinActivity.class);
        intent.putExtra("EXTRA_CLIENT", client);
        intent.putExtra("EXTRA_ADMIN_BUDDY", adminBuddy);
        startActivity(intent);
    }

    /**
     * Save all of the adminBuddy's data and log out. If there is an error,
     * indicate it.
     * @param view
     */
    public void logOut(View view) {
        try {
            adminBuddy = (Administrator) getIntent().getSerializableExtra("EXTRA_ADMIN");
            adminBuddy.saveData(getApplication().getFilesDir().toString());
            Intent intent = new Intent(this, LogInActivity.class);
            startActivity(intent);
        } catch (IOException e) {
            TextView welcomeMessage = (TextView) findViewById(R.id.welcome_text);
            welcomeMessage.setTextColor(Color.RED);
            welcomeMessage.setText("\n\nThere was an error saving data!" +
                    "\nYour changes have not been changed.");
        }

    }

    /**
     * Open the view/edit personal/billing info page for the logged in client.
     * @param view
     */
    public void viewEditInfo(View view) {
        Intent intent = new Intent(this, EditClientActivity.class);
        intent.putExtra("EXTRA_ADMIN", adminBuddy);
        intent.putExtra("EXTRA_CLIENT", client);
        intent.putExtra("EXTRA_USED_BY", "client");
        startActivity(intent);

    }

}
