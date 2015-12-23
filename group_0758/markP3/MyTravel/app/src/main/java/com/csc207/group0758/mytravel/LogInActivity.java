package com.csc207.group0758.mytravel;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import org.w3c.dom.Text;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import info.ClientManager;
import info.NoSuchClientException;
import trips.Flight;
import users.Administrator;
import users.Client;
import users.User;

public class LogInActivity extends AppCompatActivity {

    private ClientManager loginCM = new ClientManager();

    private String clientFilePath;
    private String passwordsFilePath;

    /** A Map to store login information for users */
    Map<String, String[]> usernameToLoginData;

    @Override
    /**
     * Creates a new LogInActivity, initializes Views.
     * @param savedInstanceState
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        clientFilePath = getApplication().getFilesDir() + "/clients.txt";
        passwordsFilePath = getApplication().getFilesDir() + "/passwords.txt";

        try {
            loginCM.readFromClientFile(clientFilePath);
            usernameToLoginData = getUserLoginData(passwordsFilePath);
            setHelpMessage("Welcome! If you are a new Client, your entered password will be set as your password for future use.");
        } catch (IOException e) {
            e.printStackTrace();
            setHelpMessage("There was an problem obtaining a clients or passwords text file"
            + "\nThe files should be placed in " + getApplication().getFilesDir().toString());
        }
    }

    /**
     * Logs a client into their account if their username and password match. If
     * a new user is logging in for the first time, the entered password is set
     * as the user's official password.
     * @param view
     */
    public void logIntoApp(View view)
    {
        EditText editTextUser = (EditText) findViewById(R.id.editTextUsername);
        EditText editTextPass = (EditText) findViewById(R.id.editTextPassword);
        String usernameInput = editTextUser.getText().toString();
        String passwordInput = editTextPass.getText().toString();

        // checking for authentication of username via on-phone text files
        if (usernameToLoginData.containsKey(usernameInput))
        {
            attemptLogin(usernameInput, passwordInput);
        } else if (loginCM.getClients().keySet().contains(usernameInput)){
            setClientPassword(usernameInput, passwordInput);
        }
        else if (loginCM.getClients().keySet().contains(usernameInput)){
            setClientPassword(usernameInput, passwordInput);
        }
    }

    /**
     * Obtains a Map data structure featuring Usernames and passwords, as well
     * as Administrator or Client status from a text file. Only accepts
     * usernames that exist in the login ClientManager's clients if the
     * username is supposedly that of a Client.
     * @param filepath the pathway to the passwords.txt file
     * @return a Map<String, String[]> with the details of the Usernames
     * capable of logging into the application with passwords.
     * @throws FileNotFoundException
     */
    private Map<String, String[]> getUserLoginData(String filepath) throws FileNotFoundException
    {
        Map<String, String[]> userToLogin = new HashMap<String, String[]>();
        Scanner scanner = new Scanner(new FileInputStream(filepath));
        String userLogin[];
        while(scanner.hasNext()) {
            userLogin = scanner.nextLine().split(",");
            if (userLogin[2].equals("C"))
            {
                if (loginCM.getClients().containsKey(userLogin[0])) {
                    String[] identification = {userLogin[1], userLogin[2]};
                    userToLogin.put(userLogin[0], identification);
                }
            } else
            {
                String[] identification = {userLogin[1], "A"};
                userToLogin.put(userLogin[0], identification);
            }
        }
        return userToLogin;
    }

    /**
     * Sets the help message above the log in button.
     * @param message the String message to be set on the page
     */
    private void setHelpMessage(String message) {
        TextView help = (TextView) findViewById(R.id.textViewLogInHelp);
        help.setTextColor(Color.RED);
        help.setText(message);
    }

    /**
     * Attempts logging in given the username and password.
     * @param usernameInput the username input String
     * @param passwordInput the password input String
     */
    public void attemptLogin(String usernameInput, String passwordInput){
        String[] userstatus = usernameToLoginData.get(usernameInput);
        if (passwordInput.equals(userstatus[0])) {
            if (userstatus[1].equals("C")){
                try{
                    Client c = loginCM.retrieveClientInfo(usernameInput);
                    launchClientMenu(c);
                } catch (NoSuchClientException e) {
                    setHelpMessage("No such client error");
                }
            } else {
                Administrator admin = new Administrator();
                launchAdminMenu(admin);
            }
        } else {
            setHelpMessage("Your username or password is incorrect.");
        }
    }
    
    /**
     * Updates the stored passwords with the new Client. Writes the new information to the
     * passwords.txt file.
     * @param username the String username of the new Client
     * @param password the String password of the new Client
     */
    private void setClientPassword(String username, String password)
    {
        String[] passwordStatus = {password, "C"};
        usernameToLoginData.put(username, passwordStatus);
        writePasswordsToFile();
    }

    /**
     * Updates the stored passwords text file.
     */
    public void writePasswordsToFile()
    {
        String passwordsText = new String();
        for(String email : usernameToLoginData.keySet())
        {
            String[] userAry = usernameToLoginData.get(email);
            passwordsText += email + "," + userAry[0] + "," + userAry[1] + "\n";
        }
        try{
            FileWriter fw = new FileWriter(getApplication().getFilesDir() + "/passwords.txt");
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(passwordsText);
            bw.close();
        } catch (IOException e)
        {
            setHelpMessage("There was a problem writing your new password to file.");
        }
    }

    /**
     * Redirects the user to the administrator menu and initializes a new
     * AdminMenuActivity.
     * @param admin
     */
    public void launchAdminMenu(Administrator admin)
    {
        Intent logInIntent = new Intent(this, AdminMenuActivity.class);
        try{
            admin.uploadClientInfoFromFile(getApplication().getFilesDir() + "/clients.txt");
            admin.uploadFlightInfoFromFile(getApplication().getFilesDir() + "/flights.txt");
            admin.uploadClientItinerariesFromFile(getApplication().getFilesDir() + "/trips.txt");
            logInIntent.putExtra("EXTRA_ADMIN", admin);
            startActivity(logInIntent);
        } catch (IOException e){
            setHelpMessage("There was a problem reading the Flights from file");
        } catch (NoSuchClientException e)
        {
            setHelpMessage("There was a problem reading Itineraries");
        }
    }

    /**
     * Redirects the user to the client menu and initializes a new
     * ClientMenuActivity.
     * @param admin
     */
    public void launchClientMenu(Client client)
    {
        Intent logInIntent = new Intent(this, ClientMenuActivity.class);
        try{
            Administrator admin = new Administrator();
            admin.uploadClientInfoFromFile(getApplication().getFilesDir() + "/clients.txt");
            admin.uploadFlightInfoFromFile(getApplication().getFilesDir() + "/flights.txt");
            client.uploadFlightInfoFromFile(getApplication().getFilesDir()+ "/flights.txt");
            logInIntent.putExtra("EXTRA_CLIENT", client);
            logInIntent.putExtra("EXTRA_ADMIN", admin);
            startActivity(logInIntent);
        } catch (IOException e){
            setHelpMessage("There was an error reading from file!\n"
                    + "Login aborted.");
        }
    }
}
