package edu.msu.CSE476_team16.proj1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private final static String GREEN_NAME = "MainActivity.nameOfGreen";
    private final static String WHITE_NAME = "MainActivity.nameOfWhite";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView playerOneText = (TextView)this.findViewById(R.id.usernameEditor);
        TextView playerTwoText = (TextView)this.findViewById(R.id.passwordEditor);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            playerOneText.setText(extras.getString(GREEN_NAME));
            playerTwoText.setText(extras.getString(WHITE_NAME));
        }
    }

    @Override
    public void onBackPressed() {
    }

    /**
     * Handles the start game button
     * @param view the view
     */
    public void onStartGame(View view) {
        TextView playerOneText = (TextView)this.findViewById(R.id.usernameEditor);
        //TextView playerTwoText = (TextView)this.findViewById(R.id.passwordEditor);


        //starts the GameBoardActivity
        Intent intent = new Intent(this, GameBoardActivity.class);
        intent.putExtra(GREEN_NAME, playerOneText.getText().toString());
        //intent.putExtra(WHITE_NAME, playerTwoText.getText().toString());
        intent.putExtra(WHITE_NAME, "Jamie");
        startActivity(intent);
    }

    /**
     * Handles the create account option
     * @param view the view
     */
    public void onCreateAccount(View view){

    }
}