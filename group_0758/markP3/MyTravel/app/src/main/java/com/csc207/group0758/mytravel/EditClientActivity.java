package com.csc207.group0758.mytravel;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import info.NoSuchClientException;
import users.Administrator;
import users.Client;

public class EditClientActivity extends AppCompatActivity {
    // Assume a client to be edited with a admin to track changes
    private Client client;
    private Administrator admin;
    private String usedBy;

    // Client attributes to display/edit
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String creditCard;
    private String expiryDate;

    // EditTexts to take input
    private EditText editFirstName;
    private EditText editLastName;
    private EditText editEmail;
    private EditText editAddress;
    private EditText editCreditCard;
    private EditText editExpiryDate;

    /**
     * Gets a client and admin. Displays all client attributes in the EditTexts.
     *
     * @param savedInstanceState
     */
    @Override
    /**
     * Creates a new AddFlightActivity, initializes Views, unpackages Intents.
     * @param savedInstanceState
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_client);

        editFirstName = (EditText) findViewById(R.id.first_name);
        editLastName = (EditText) findViewById(R.id.last_name);
        editEmail = (EditText) findViewById(R.id.email);
        editAddress = (EditText) findViewById(R.id.address);
        editCreditCard = (EditText) findViewById(R.id.credit_card);
        editExpiryDate = (EditText) findViewById(R.id.expiry_date);

        Intent intent = getIntent();
        client = (Client) intent.getSerializableExtra("EXTRA_CLIENT");
        admin = (Administrator) intent.getSerializableExtra("EXTRA_ADMIN");
        usedBy = intent.getStringExtra("EXTRA_USED_BY");

        populateClientFields();
    }

    /**
     * Populate the EditTexts with the current client information.
     */
    private void populateClientFields() {
        firstName = client.getFirstName();
        lastName = client.getLastName();
        email = client.getEmail();
        address = client.getAddress();
        creditCard = client.getCreditCardNum();
        expiryDate = client.getExpiryDate();

        editFirstName.setText(firstName);
        editLastName.setText(lastName);
        editEmail.setText(email);
        editAddress.setText(address);
        editCreditCard.setText(creditCard);
        editExpiryDate.setText(expiryDate);
    }

    /**
     * If there are any changes made in the EditTexts, modify the client
     * and save it to the admin.
     * @param view
     */
    public void saveChanges(View view) {
        // Get entered information
        String enteredFirstName = editFirstName.getText().toString();
        String enteredLastName = editLastName.getText().toString();
        String enteredEmail = editEmail.getText().toString();
        String enteredAddress = editAddress.getText().toString();
        String enteredCreditCard = editCreditCard.getText().toString();
        String enteredExpiryDate = editExpiryDate.getText().toString();
        boolean changesMade = false;

        // Check and make changes
        if (!enteredFirstName.equals(firstName)){
            try {
                admin.editClientInfo(email, "firstname", enteredFirstName);
                changesMade = true;}
            catch (NoSuchClientException e) {};
        }
        if (!enteredLastName.equals(lastName)){
            try {
                admin.editClientInfo(email, "lastname", enteredLastName);
                changesMade = true;}
            catch (NoSuchClientException e) {};
        }
        if (!enteredAddress.equals(address)){
            try {
                admin.editClientInfo(email, "address", enteredAddress);
                changesMade = true;}
            catch (NoSuchClientException e) {};
        }
        if (!enteredCreditCard.equals(creditCard)){
            try {
                admin.editClientInfo(email, "creditcardnum", enteredCreditCard);
                changesMade = true;}
            catch (NoSuchClientException e) {};
        }
        if (!enteredExpiryDate.equals(expiryDate)){
            try {
                admin.editClientInfo(email, "expirydate", enteredExpiryDate);
                changesMade = true;}
            catch (NoSuchClientException e) {};
        }

        // Must be last since email changes how client is retrieved
        if (!enteredEmail.equals(email)){
            try {
                admin.editClientInfo(email, "email", enteredEmail);
                changesMade = true;}
            catch (NoSuchClientException e) {};
        }

        TextView title = (TextView) findViewById(R.id.title);
        title.setTextSize(15);
        // If changes were made, given an indication, update the client within
        // admin and move back to the appropriate menu, depending on which user
        // is accessing this activity.
        if (changesMade) {
            title.setText("Saved changes");
            title.setTextColor(Color.GREEN);
            try{client = admin.getClient(enteredEmail);}
            catch (NoSuchClientException e) {}

            Intent intent = new Intent(this, ClientMenuActivity.class);
            if (usedBy.equals("admin")) {
                intent = new Intent(this, AdminMenuActivity.class);
            }

            intent.putExtra("EXTRA_CLIENT", client);
            intent.putExtra("EXTRA_ADMIN", admin);
            startActivity(intent);
        }
        // If no changes were made, indicate it.
        else {
            title.setText("No changes entered");
            title.setTextColor(Color.RED);
        }

    }
}
