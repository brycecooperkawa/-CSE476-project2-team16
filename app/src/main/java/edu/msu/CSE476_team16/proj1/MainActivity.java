package edu.msu.CSE476_team16.proj1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private final static String GREEN_NAME = "MainActivity.nameOfGreen";
    private final static String WHITE_NAME = "MainActivity.nameOfWhite";

    private SharedPreferences settings = null;
    private final static String USERNAME = "username";
    private final static String PASSWORD = "password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            playerOneText.setText(extras.getString(GREEN_NAME));
            playerTwoText.setText(extras.getString(WHITE_NAME));
        }
        */

        //preferences
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String username = prefs.getString(USERNAME, "");
        String password = prefs.getString(PASSWORD, "");

        //set inital values for username and password if they were remembered
        TextView usernameText = (TextView)this.findViewById(R.id.usernameEditor);
        TextView passwordText = (TextView)this.findViewById(R.id.passwordEditor);
        usernameText.setText(username);
        passwordText.setText(password);
    }

    @Override
    public void onBackPressed() {
    }

    /**
     * Handles the create account option
     * @param view the view
     */
    public void onCreateAccount(View view){
        Intent intent = new Intent(this, CreateAccountActivity.class);
        startActivity(intent);
    }

    /**
     * Handles the login option
     * @param view the view
     */
    public void onLogin(View view){
        //get the username and password entered
        TextView user=findViewById(R.id.usernameEditor);
        TextView pass=findViewById(R.id.passwordEditor);

        //convert to strings
        String u = user.getText().toString();
        String x = pass.getText().toString();

        //php stuff that checks if user information is valid
        if(checkUser(u,x)){
            //check if checkbox is checked
            CheckBox remember=(CheckBox)findViewById(R.id.rememberCheck);
            //if checkbox is checked then save username and password in preferences
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor ed = prefs.edit();
            if(remember.isChecked()){
                ed.putString(USERNAME, u);
                ed.putString(PASSWORD, x);
                ed.apply();
            }else{
                //if login without checked set to empty
                ed.putString(USERNAME, "");
                ed.putString(PASSWORD, "");
                ed.apply();
            }

            //go to start activity
            Intent intent = new Intent(this, StartGameActivity.class);
            startActivity(intent);
        }else{
            //toast error saying invalid user
            Toast.makeText(this,"Username or Password is incorrect",Toast.LENGTH_SHORT).show();
        }

        Intent intent = new Intent(this, StartGameActivity.class);
        startActivity(intent);

    }

    /**
     * Checks if the username and password exist in the database
     * @param username the username
     * @param password the password
     * @return returns true if the information is found in the database, false if not
     */
    public boolean checkUser(final String username, final String password){
        return Boolean.TRUE;
    }

}