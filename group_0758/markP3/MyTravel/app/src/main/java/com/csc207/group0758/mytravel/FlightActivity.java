package com.csc207.group0758.mytravel;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import users.Administrator;

public class FlightActivity extends AppCompatActivity {
	
    Administrator admin;

    @Override
    /**
     * Creates a new FlightActivity, initializes Views, unpackages Intents.
     * @param savedInstanceState
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        admin = (Administrator) intent.getSerializableExtra("EXTRA_ADMIN");
    }
    
    /**
     * Enter the admin-specific retrieveFlight menu.
     *
     * @param view
     */
    public void EditFlight(View view) {
        Intent intent = new Intent(this, RetrieveFlightActivity.class);
        intent.putExtra("EXTRA_ADMIN", admin);
        startActivity(intent);
    }

    /**
     * Enter the user-generic flight menu for conducting searches.
     * @param view
     */
    public void openFlightMenu(View view) {
        Intent intent = new Intent(this, FlightMenuActivity.class);
        intent.putExtra("EXTRA_USER", admin);
        startActivity(intent);
    }

    /**
     * Enter the admin-specific uploadFlight menu.
     * @param view
     */
    public void uploadFlight(View view){
        Intent intent = new Intent(this, UploadFlightActivity.class);
        intent.putExtra("EXTRA_ADMIN", admin);
        startActivity(intent);
    }
}
