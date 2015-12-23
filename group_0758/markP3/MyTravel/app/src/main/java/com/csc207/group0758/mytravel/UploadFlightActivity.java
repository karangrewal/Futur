package com.csc207.group0758.mytravel;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import users.Administrator;

public class UploadFlightActivity extends AppCompatActivity {
    // Assume a given admin
    private Administrator admin;

    /**
     * Creates a new UploadFlightActivity, initializes Views, unpackages Intents.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_flight);

        Intent intent = getIntent();
        admin = (Administrator) intent.getSerializableExtra("EXTRA_ADMIN");
    }

    /**
     * Check if a given file exists and, if so, read the csv file to upload
     * all given flights to the admin.
     *
     * @param view
     */
    public void uploadFlights(View view) {
        EditText editText = (EditText) findViewById(R.id.flight_file);
        TextView header = (TextView) findViewById(R.id.error_message);
        String flightPath = "/" + editText.getText().toString();
        String appPath = this.getApplicationContext().getFilesDir().getAbsolutePath();
        String fullPath = appPath + flightPath;

        try {
            admin.uploadFlightInfoFromFile(fullPath);
            Intent intent = new Intent(this, AdminMenuActivity.class);
            intent.putExtra("EXTRA_ADMIN", admin);
            startActivity(intent);
        }
        catch (IOException e1) {
            // If no file, raise an error message
            header.setText("No such file found.");
            header.setTextColor(Color.RED);
        }
    }

}
