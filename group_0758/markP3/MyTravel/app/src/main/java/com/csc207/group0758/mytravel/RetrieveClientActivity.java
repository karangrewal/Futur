package com.csc207.group0758.mytravel;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import info.NoSuchClientException;
import users.Administrator;
import users.Client;

public class RetrieveClientActivity extends AppCompatActivity {

    Administrator admin;
    String email;


    /**
     * Creates a new RetrieveClientActivity, initializes Views, unpackages Intents.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve_client);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        admin = (Administrator) intent.getSerializableExtra("EXTRA_ADMIN");
    }

    /**
     * Check if the given email exists as a client and move on to the EditClient
     * menu.
     * @param view
     */
    public void viewAndEditInfo(View view) {
        EditText result = (EditText) findViewById(R.id.client_email);
        email = result.getText().toString();

        try {
            Client client = admin.getClient(email);
            Intent intent = new Intent(this, EditClientActivity.class);
            intent.putExtra("EXTRA_ADMIN", admin);
            intent.putExtra("EXTRA_CLIENT", client);
            intent.putExtra("EXTRA_USED_BY", "admin");
            startActivity(intent);
        } catch (NoSuchClientException ex) {
            // If no such client, raise an error message
            TextView textView = (TextView) findViewById(R.id.help_retrieve);
            textView.setTextColor(Color.RED);
            textView.setText("No such client");
        }
    }

    /**
     * Check if the given email exists as a client and move on to the
     * viewBookedItineraries menu.
     * @param view
     */
    public void viewBookedItineraries(View view) {
        //implement
        EditText result = (EditText) findViewById(R.id.client_email);
        email = result.getText().toString();

        try {
            Client client = admin.getClient(email);
            Intent intent = new Intent(this, ViewBookedItinActivity.class);
            intent.putExtra("EXTRA_CLIENT", client);
            intent.putExtra("EXTRA_ADMIN", admin);
            startActivity(intent);
            ;
        } catch (NoSuchClientException ex) {
            // If no such email, raise an error message
            TextView textView = (TextView) findViewById(R.id.help_retrieve);
            textView.setTextColor(Color.RED);
            textView.setText("No such client");
        }
    }

    /**
     * Move to the UploadClient activity without checking the given email.
     * @param view
     */
    public void uploadNewClients(View view) {
        Intent intent = new Intent(this, UploadClientActivity.class);
        intent.putExtra("EXTRA_ADMIN", admin);
        startActivity(intent);
    }
}
