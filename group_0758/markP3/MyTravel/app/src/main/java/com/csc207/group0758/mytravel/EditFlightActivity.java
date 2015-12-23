package com.csc207.group0758.mytravel;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.reflect.InvocationTargetException;

import info.NoSuchClientException;
import info.NoSuchFlightException;
import trips.Flight;
import trips.Trip;
import users.Administrator;
import users.Client;

public class EditFlightActivity extends AppCompatActivity {
    // Assume an admin and a given flight to edit
    private Administrator admin;
    private Flight flight;

    // Flight informationt to edit
    private String flightNumber;
    private String departureTime;
    private String arrivalTime;
    private String airline;
    private String origin;
    private String destination;
    private String price;

    // EditTexts to take changes
    private EditText editFlightNumber;
    private EditText editDepartureTime;
    private EditText editArrivalTime;
    private EditText editAirline;
    private EditText editOrigin;
    private EditText editDestination;
    private EditText editPrice;

    /**
     * Take an admin and given flight and display the flight's current information
     * in TextViews.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_flight);

        editFlightNumber = (EditText) findViewById(R.id.flight_number);
        editDepartureTime = (EditText) findViewById(R.id.departure_time);
        editArrivalTime = (EditText) findViewById(R.id.arrival_time);
        editAirline = (EditText) findViewById(R.id.airline);
        editOrigin = (EditText) findViewById(R.id.origin);
        editDestination = (EditText) findViewById(R.id.destination);
        editPrice = (EditText) findViewById(R.id.price);

        Intent intent = getIntent();
        admin = (Administrator) intent.getSerializableExtra("EXTRA_ADMIN");
        flight = (Flight) intent.getSerializableExtra("EXTRA_FLIGHT");

        populateFlightFields();
    }

    /**
     * Populate the EditTexts with the flight's current information
     */
    private void populateFlightFields() {
        flightNumber = String.valueOf(flight.getFlightNumber());
        departureTime = Trip.convertDateToString(flight.getDepartureDate());
        arrivalTime =  Trip.convertDateToString(flight.getArrivalDate());
        airline = flight.getAirline();
        origin = flight.getOrigin();
        destination = flight.getDestination();
        price = String.valueOf(flight.getCost());

        editFlightNumber.setText(flightNumber);
        editDepartureTime.setText(departureTime);
        editArrivalTime.setText(arrivalTime);
        editAirline.setText(airline);
        editOrigin.setText(origin);
        editDestination.setText(destination);
        editPrice.setText(price);
    }

    /**
     * Save any changes to the admin.
     * @param view
     */
    public void saveChanges(View view) {
        TextView title = (TextView) findViewById(R.id.title);
        title.setTextSize(15);

        // Take input. If the arrival date does not come after the departure date
        // or if the date's have an invalid amount of characters, throw an error.
        String enteredFlightNumber = editFlightNumber.getText().toString();
        String enteredDepartureTime = editDepartureTime.getText().toString();
        String enteredArrivalTime = editArrivalTime.getText().toString();
        if ((enteredDepartureTime.length() != 16) || (enteredArrivalTime.length() != 16)) {
            title.setText("Invalid date formats");
            title.setTextColor(Color.RED); return;
        }
        if (!Trip.convertStringToDate(enteredArrivalTime).after(
                Trip.convertStringToDate(enteredDepartureTime))){
            title.setText("Invalid date combination");
            title.setTextColor(Color.RED); return;
        }
        String enteredAirline = editAirline.getText().toString();
        String enteredOrigin = editOrigin.getText().toString();
        String enteredDestination = editDestination.getText().toString();
        String enteredPrice = editPrice.getText().toString();
        // If price is not a double, indicate an error
        try {Double.parseDouble(enteredPrice);}
        catch (NumberFormatException e2) {
            title.setText("Invalid price format");
            title.setTextColor(Color.RED); return;
        }
        boolean changesMade = false;
        int intFlightNumber = Integer.parseInt(flightNumber);

        // Make any changes
        if (!enteredDepartureTime.equals(departureTime)){
            try {
                admin.editFlightInfo(intFlightNumber, "departuredate", enteredDepartureTime);
                changesMade = true;}
            catch (NoSuchFlightException e) {};
        }
        if (!enteredArrivalTime.equals(arrivalTime)){
            try {
                admin.editFlightInfo(intFlightNumber, "arrivaldate", enteredArrivalTime);
                changesMade = true;}
            catch (NoSuchFlightException e) {};
        }
        if (!enteredAirline.equals(airline)){
            try {
                admin.editFlightInfo(intFlightNumber, "airline", enteredAirline);
                changesMade = true;}
            catch (NoSuchFlightException e) {};
        }
        if (!enteredOrigin.equals(origin)){
            try {
                admin.editFlightInfo(intFlightNumber, "origin", enteredOrigin);
                changesMade = true;}
            catch (NoSuchFlightException e) {};
        }
        if (!enteredDestination.equals(destination)){
            try {
                admin.editFlightInfo(intFlightNumber, "destination", enteredDestination);
                changesMade = true;}
            catch (NoSuchFlightException e) {};
        }
        if (!enteredPrice.equals(price)){
            try {
                admin.editFlightInfo(intFlightNumber, "cost", enteredPrice);
                changesMade = true;}
            catch (NoSuchFlightException e) {};
        }

        // Must be last since changes how flight is retrieved
        if (!enteredFlightNumber.equals(flightNumber)){
            try {
                admin.editFlightInfo(intFlightNumber, "flightNumber", enteredFlightNumber);
                changesMade = true;}
            catch (NoSuchFlightException e) {};
        }

        // If changes are made, indicate it and go back to the admin menu.
        // If not, indicate that none were made.
        if (changesMade) {
            title.setText("Saved changes");
            title.setTextColor(Color.GREEN);

            Intent intent = new Intent(this, AdminMenuActivity.class);
            intent.putExtra("EXTRA_ADMIN", admin);
            startActivity(intent);
        }
        else {
            title.setText("No changes entered");
            title.setTextColor(Color.RED);
        }
    }
}
