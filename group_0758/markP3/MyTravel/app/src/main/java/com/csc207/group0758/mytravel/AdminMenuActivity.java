package com.csc207.group0758.mytravel;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.IOException;

import trips.Flight;
import users.Administrator;
import users.Client;

public class AdminMenuActivity extends AppCompatActivity {
    // Assumes an admin
    private Administrator admin;

    /**
     * Gets the serializable admin.
     * @param savedInstanceState
     */
    @Override
    /**
     * Creates a new AddFlightActivity, initializes Views, unpackages Intents.
     * @param savedInstanceState
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_menu);

        Intent intent = getIntent();
        admin = (Administrator) intent.getSerializableExtra("EXTRA_ADMIN");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


    }

    /**
     * Inflate the menu; this adds items to the action bar if it is present.
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * Handle action bar item clicks here. The action bar will automatically
     * handle clicks on the Home/Up button, so long as you specify a parent
     * activity in AndroidManifest.xml.
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Open the admin flight menu.
     * @param view
     */
    public void openFlight(View view) {
        Intent intent = new Intent(this, FlightActivity.class);
        intent.putExtra("EXTRA_ADMIN", admin);
        startActivity(intent);
    }

    /**
     * Open the admin specific itinerary search menu
     * @param view
     */
    public void openItineraryMenu(View view) {
        Intent intent = new Intent(this, ItineraryMenuActivity.class);
        intent.putExtra("EXTRA_ADMIN", admin);
        startActivity(intent);
    }

    /**
     * Open the admin specific retrieveClient menu.
     * @param view
     */
    public void retrieveClient(View view){
        Intent intent = new Intent(this, RetrieveClientActivity.class);
        intent.putExtra("EXTRA_ADMIN", admin);
        startActivity(intent);
    }

    /**
     * Open the admin specific retrieveFlight menu.
     * @param view
     */
    public void retrieveFlight(View view) {
        Intent intent = new Intent(this, RetrieveFlightActivity.class);
        intent.putExtra("EXTRA_ADMIN", admin);
        startActivity(intent);
    }

    /**
     * Save all admin data from the flight and client managers and return to the
     * log in screen. If an error, indicate it.
     * @param view
     */
    public void logOut(View view) {
        try {
            admin.saveData(getApplication().getFilesDir().toString());
            Intent intent = new Intent(this, LogInActivity.class);
            startActivity(intent);
        } catch (IOException e) {
            TextView welcomeMessage = (TextView) findViewById(R.id.welcome_text);
            welcomeMessage.setTextColor(Color.RED);
            welcomeMessage.setText("There was an error saving data!" +
                    "\nYour changes have not been changed.");
        }
    }
}
