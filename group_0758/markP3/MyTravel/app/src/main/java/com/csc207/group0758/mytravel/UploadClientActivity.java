package com.csc207.group0758.mytravel;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;

import users.Administrator;

public class UploadClientActivity extends AppCompatActivity {
    // Assume a given Administrator
    private Administrator admin;

    /**
     * Creates a new UploadClientActivity, initializes Views, unpackages Intents.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_client);

        Intent intent = getIntent();
        admin = (Administrator) intent.getSerializableExtra("EXTRA_ADMIN");
    }

    /**
     * Check if the given file exists and, if so, read the csv file and
     * upload all stored clients to the admin.
     *
     * @param view
     */
    public void uploadClients(View view) {
        EditText editText = (EditText) findViewById(R.id.client_file);
        TextView header = (TextView) findViewById(R.id.error_message);
        String flightPath = "/" + editText.getText().toString();
        String appPath = this.getApplicationContext().getFilesDir().getAbsolutePath();
        String fullPath = appPath + flightPath;

        try {
            admin.uploadClientInfoFromFile(fullPath);
            Intent intent = new Intent(this, AdminMenuActivity.class);
            intent.putExtra("EXTRA_ADMIN", admin);
            startActivity(intent);
        }
        catch (IOException e1) {
            // If no file found, raise an error message
            header.setText("No such file found.");
            header.setTextColor(Color.RED);
        }
    }

}
