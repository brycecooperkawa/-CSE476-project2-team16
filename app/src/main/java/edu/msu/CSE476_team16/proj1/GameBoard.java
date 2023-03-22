package edu.msu.CSE476_team16.proj1;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
import java.util.ArrayList;

public class GameBoard {
    private final static String GREEN_NAME = "MainActivity.nameOfGreen";
    private final static String WHITE_NAME = "MainActivity.nameOfWhite";


    /**
     * Percentage of the display width or height that
     * is occupied by the game.
     */
    final static float SCALE_IN_VIEW = 0.9f;

    /**
     * Paint for filling the area the gamr is in
     */
    private Paint fillPaint;

    /**
     * Array of columns
     */
    public ArrayList<GameColumn> columns = new ArrayList<GameColumn>();

    /**
     * The size of the game board in pixels
     */
    private int gameWidth;

    /**
     * The size of the game board in pixels
     */
    private int gameHeight;

    /**
     * Left margin in pixels
     */
    private int marginX;

    /**
     * Top margin in pixels
     */
    private int marginY;

    /**
     * This variable is set to a column we are touching
     */
    private GameColumn touching = null;

    /**
     * This variable is set to a column we placed a tile in without pressing done
     */
    private GameColumn justPlaced = null;

    private boolean isGreensTurn = true;

    /**
     * The name of the bundle keys to save the game
     */
    private final static String TILES = "game.tiles";
    private final static String SPOTS_FILLED = "game.spotsFilled";
    private final static String COLUMN_IDS = "game.columnIds";
    private final static String JUST_PLACED = "game.justPlaced";
    private final static String TURN = "game.isGreensTurn";

    private final static String WINNER = "game.winnerIsGreen";

    public GameBoard(Context context) {
        // Create paint for filling the area the game will
        // be solved in.
        fillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        fillPaint.setColor(0xffcccccc);





        restart();

    }

    public void draw(Canvas canvas) {

        float wid = (float) canvas.getWidth();
        float hit = (float) canvas.getHeight();

        // Determine the minimum of the two dimensions
        boolean widMin = wid < hit;

        gameWidth = widMin ? (int)(wid * SCALE_IN_VIEW) : (int)(hit * SCALE_IN_VIEW );
        gameHeight = widMin ? (int)(wid * SCALE_IN_VIEW ) : (int)(hit * SCALE_IN_VIEW);

        // Compute the margins so we center the game
        marginX = ((int) wid - gameWidth) / 2;
        marginY = ((int) hit - gameHeight) / 2;

        //
        // Draw the outline of the game
        //
        canvas.drawRect(marginX, marginY,
                marginX + gameWidth, marginY + gameHeight, fillPaint);

    }

    /**
     * Handle a touch event from the view.
     * @param view The view that is the source of the touch
     * @param event The motion event describing the touch
     * @return true if the touch is handled.
     */
    public boolean onTouchEvent(View view, MotionEvent event) {
        //
        // Convert an x,y location to a relative location in the
        // game board.
        //

        float relX = (event.getX() - marginX) / gameWidth;
        float relY = (event.getY() - marginY) / gameHeight;

        switch (event.getActionMasked()) {

            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                return onTouched(view, relX, relY);

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                return onReleased(view);
        }

        return false;
    }

    /**
     * Handle a touch message. This is when we get an initial touch
     * @param x x location for the touch, relative to the game - 0 to 1 over the game
     * @param y y location for the touch, relative to the game - 0 to 1 over the game
     * @return true if the touch is handled
     */
    private boolean onTouched(View view, float x, float y) {
        return false;
    }

    /**
     * Handle a release of a touch message.
     * @return true if the touch is handled
     */
    private boolean onReleased(View view) {


        return false;
    }

    public void finishGame(View view, String greenName, String whiteName) {
        Intent intent = new Intent(view.getContext(), EndGameActivity.class);
        intent.putExtra(WINNER, !isGreensTurn);
        intent.putExtra(GREEN_NAME, greenName);
        intent.putExtra(WHITE_NAME, whiteName);
        view.getContext().startActivity(intent);
    }

    public void undoMove(View view) {

    }

    public boolean finishMove(View view, String greenName, String whiteName) {

        return true;
    }

    /**
     * Determine if the connect 4 is done!
     * @return true if connect 4 is done
     */
    public boolean isDone(GameColumn justAdded) {
        return false;
    }

    private boolean isColor(int column, int row, boolean isGreen) {
        return false;
    }

    /**
     * Reset the board game
     */
    public void restart() {

    }

    /**
     * Save the game to a bundle
     * @param bundle The bundle we save to
     */
    public void saveInstanceState(Bundle bundle) {

    }

    /**
     * Read the game from a bundle
     * @param bundle The bundle we save to
     */
    public void loadInstanceState(Bundle bundle) {

    }
}
