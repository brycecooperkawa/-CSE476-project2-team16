package edu.msu.CSE476_team16.proj1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class CreateAccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
    }

    /**
     * Handles the create account button press
     * @param view the view
     */
    public void onCreateAccount2(View view){
        //get username and password
        TextView user=this.findViewById(R.id.usernameEditor);
        TextView pass1=this.findViewById(R.id.passwordEditor);
        TextView pass2=this.findViewById(R.id.passwordEditor);

        String u=user.getText().toString();
        String x=pass1.getText().toString();
        String y=pass2.getText().toString();

        //check password match
        if(x.equals(y)){
            //php stuff
            createUser(u,x);

            //go back to main screen
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }else{
            //toast error saying passwords must match
            Toast.makeText(this,"Passwords must match",Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * Handles cancel button press
     * @param view the view
     */
    public void onCancel(View view){
        //go back to main screen
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    /**
     * Handles saving the user to the cloud
     * @param username the username
     * @param password the password
     */
    public void createUser(final String username, final String password){

    }
}