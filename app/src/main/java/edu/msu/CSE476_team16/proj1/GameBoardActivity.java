package edu.msu.CSE476_team16.proj1;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class GameBoardActivity extends AppCompatActivity {
    String greenPlayerName = "";
    String whitePlayerName = "";

    private final static String GREEN_NAME = "MainActivity.nameOfGreen";
    private final static String WHITE_NAME = "MainActivity.nameOfWhite";

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_game_board);

        if(bundle != null) {
            getGameView().loadInstanceState(bundle);
        }

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            greenPlayerName = extras.getString(GREEN_NAME);
            whitePlayerName = extras.getString(WHITE_NAME);
            if (greenPlayerName.equals("")) {
                greenPlayerName = getString(R.string.default_green);
            }

            if (whitePlayerName.equals("")) {
                whitePlayerName = getString(R.string.default_white);
            }
        }

        TextView playerText = (TextView)this.findViewById(R.id.playerIndicator);
        playerText.setText(greenPlayerName + getString(R.string.turn_indicator));
    }

    /**
     * Save the instance state into a bundle
     * @param bundle the bundle to save into
     */
    @Override
    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);

        getGameView().saveInstanceState(bundle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_shuffle:
                getGameView().getGameBoard().restart();
                getGameView().invalidate();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Get the game view
     * @return GameBoardView reference
     */
    private GameBoardView getGameView() {
        return (GameBoardView)this.findViewById(R.id.gameView);
    }

    public void finishGame(View view) {
        getGameView().getGameBoard().finishGame(getGameView(), greenPlayerName, whitePlayerName);
    }

    public void undoMove(View view) {
        getGameView().getGameBoard().undoMove(getGameView());

    }

    public void finishMove(View view) {
        TextView playerText = (TextView)this.findViewById(R.id.playerIndicator);
        boolean isGreen = getGameView().getGameBoard().finishMove(getGameView(), greenPlayerName, whitePlayerName);

        if (isGreen) {
            playerText.setText(greenPlayerName + getString(R.string.turn_indicator));
        } else {
            playerText.setText(whitePlayerName + getString(R.string.turn_indicator));
        }
    }
}
