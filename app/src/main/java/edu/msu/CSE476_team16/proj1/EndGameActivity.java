package edu.msu.CSE476_team16.proj1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class EndGameActivity extends AppCompatActivity {

    String greenPlayerName = "";
    String whitePlayerName = "";

    private final static String WINNER = "game.winnerIsGreen";

    private final static String GREEN_NAME = "MainActivity.nameOfGreen";
    private final static String WHITE_NAME = "MainActivity.nameOfWhite";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            boolean isGreen = extras.getBoolean(WINNER);
            greenPlayerName = extras.getString(GREEN_NAME);
            whitePlayerName = extras.getString(WHITE_NAME);

            TextView playerText = (TextView)this.findViewById(R.id.winnerIndicator);
            if (isGreen) {
                playerText.setText(greenPlayerName + getString(R.string.winner) + whitePlayerName + getString(R.string.loser));
            } else {
                playerText.setText(whitePlayerName + getString(R.string.winner) + greenPlayerName + getString(R.string.loser));
            }
        }

    }

    @Override
    public void onBackPressed() {
    }

    public void onMainMenu(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(GREEN_NAME, greenPlayerName);
        intent.putExtra(WHITE_NAME, whitePlayerName);
        startActivity(intent);
    }
}
