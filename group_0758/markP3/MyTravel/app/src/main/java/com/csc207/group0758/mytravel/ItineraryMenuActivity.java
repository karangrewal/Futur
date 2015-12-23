package com.csc207.group0758.mytravel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import users.Administrator;
import users.Client;
import users.User;

public class ItineraryMenuActivity extends AppCompatActivity {
    // Assume a generic user and an adminBuddy (if user is client)
    private User user;
    private Administrator adminBuddy;
    
    /** An ArrayAdapter to set a menu in this Activity. */
    private ArrayAdapter<String> adapter;
    private Spinner spinner;

    @Override
    /**
     * Creates a new ItineraryMenuActivity, initializes Views, unpackages Intents.
     *
     * @param savedInstanceState
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itinerary_menu);

        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("EXTRA_USER");
        if (user instanceof Client) {
            adminBuddy = (Administrator) intent.getSerializableExtra("EXTRA_ADMIN_BUDDY");
        }
        // Create and populate a dropdown menu
        spinner = (Spinner) findViewById(R.id.sort);
        adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, this.getSort());
        spinner.setAdapter(adapter);
    }

    /**
     * Returns a list of strings to populate the dropDown menu.
     * @return
     */
    public List<String> getSort(){
        List<String> list = new ArrayList<>();
        list.add("Time");
        list.add("Cost");
        return list;
    }

    /**
     * Conduct a search on itineraries given the filled out EditTexts and chosen
     * sort criteria in the dropdown menu.
     * @param view
     */
    public void searchItineraries(View view) {
        Intent intent = new Intent(this, ItinerarySearchActivity.class);

        // Get entered criteria
        EditText originText = (EditText) findViewById(R.id.origin);
        String origin = originText.getText().toString();
        EditText destinationText = (EditText) findViewById(R.id.destination);
        String destination = destinationText.getText().toString();
        EditText dateText = (EditText) findViewById(R.id.date);
        String date = dateText.getText().toString();

        // Conduct the search
        user.searchPossibleItineraries(date, origin, destination);

        // Check dropdown choice and sort accordingly
        if (spinner.getSelectedItem().equals("Cost")) {
            user.sortPossibleItineraries("cost");
        }
        else {
            user.sortPossibleItineraries("time");
        }
        // Package the next intent and move to the search results page.
        String[] searchCriteria = new String[] {origin, destination, date};
        intent.putExtra("search_information", searchCriteria); // Add name argument (1) to putExtra
        intent.putExtra("EXTRA_USER", user);
        if (user instanceof Client) {
            intent.putExtra("EXTRA_ADMIN_BUDDY", adminBuddy);
        }
        startActivity(intent);
    }
}
