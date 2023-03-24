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

        for (int i = 0; i < 7; i++) {
            // Load the game pieces
            columns.add(new GameColumn(context,
                    R.drawable.empty_space,
                    R.drawable.spartan_green,
                    R.drawable.spartan_white,
                    i
            ));
        }


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

        for(GameColumn piece : columns) {
            piece.draw(canvas, marginX, marginY, gameWidth);
        }
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

        if (justPlaced == null) {
            if (touching != null) {
                touching.removeFloatingTile();
                touching = null;
            }
            // Check each piece to see if it has been hit
            // We do this in reverse order so we find the pieces in front
            for (int p = columns.size() - 1; p >= 0; p--) {
                if (columns.get(p).hit(x, y)) {
                    // We hit a piece!
                    touching = columns.get(p);
                    touching.displayFloatingTile(isGreensTurn);
                    view.invalidate();
                    return true;
                }
            }
            view.invalidate();
        }
        return false;
    }

    /**
     * Handle a release of a touch message.
     * @return true if the touch is handled
     */
    private boolean onReleased(View view) {

        if(touching != null) {

            if (touching.addTile(isGreensTurn)) {
                justPlaced = touching;
            } else {
                Toast.makeText(view.getContext(), R.string.invalid_move,
                        Toast.LENGTH_SHORT).show();
            }
            view.invalidate();
            touching.removeFloatingTile();
            touching = null;
            return true;
        }

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
        if (justPlaced != null) {
            justPlaced.removeTopTile();
            justPlaced = null;
            view.invalidate();
        } else {
            Toast.makeText(view.getContext(), R.string.tile_error,
                    Toast.LENGTH_SHORT).show();
        }
    }

    public boolean finishMove(View view, String greenName, String whiteName) {
        if (justPlaced != null) {
            isGreensTurn = !isGreensTurn;

            if (isDone(justPlaced)) {
                finishGame(view, greenName, whiteName);
            }
            justPlaced = null;
        } else {
            Toast.makeText(view.getContext(), R.string.tile_error,
                    Toast.LENGTH_SHORT).show();
        }
        return isGreensTurn;
    }

    /**
     * Determine if the connect 4 is done!
     * @return true if connect 4 is done
     */
    public boolean isDone(GameColumn justAdded) {
        int rowAdded = justAdded.getSpotsFilled()-1;
        int columnAdded = justAdded.getColumnId();

        boolean greenAdded = !isGreensTurn;

        int l = 0;
        int r = 0;

        int d = 0;

        int ul = 0;
        int dr = 0;

        int ur = 0;
        int dl = 0;

        for(int i=1; i<4; i++){
            if (rowAdded-i >= 0 && isColor(columnAdded, rowAdded-i, greenAdded)) {
                d += 1;
            } else {
                break;
            }
        }

        for(int i=1; i<4; i++){
            if (columnAdded-i >= 0 && isColor(columnAdded-i, rowAdded, greenAdded)) {
                l += 1;
            } else {
                break;
            }
        }
        for(int i=1; i<4; i++){
            if (columnAdded+i < 7 && isColor(columnAdded+i, rowAdded, greenAdded)) {
                r += 1;
            } else {
                break;
            }
        }

        for(int i=1; i<4; i++){
            if (columnAdded+i < 7 && rowAdded-i >= 0 && isColor(columnAdded+i, rowAdded-i, greenAdded)) {
                dr += 1;
            } else {
                break;
            }
        }
        for(int i=1; i<4; i++){
            if (rowAdded+i < 6 && columnAdded-i >= 0 && isColor(columnAdded-i, rowAdded+i, greenAdded)) {
                ul += 1;
            } else {
                break;
            }
        }

        for(int i=1; i<4; i++){
            if (columnAdded+i < 7 && rowAdded+i < 6 && isColor(columnAdded+i, rowAdded+i, greenAdded)) {
                ur += 1;
            } else {
                break;
            }
        }
        for(int i=1; i<4; i++){
            if (rowAdded-i >= 0 && columnAdded-i >= 0 && isColor(columnAdded-i, rowAdded-i, greenAdded)) {
                dl += 1;
            } else {
                break;
            }
        }



        if (d == 3) {
            return true;
        }
        if (l+r >= 3) {
            return true;
        }
        if (ul+dr == 3) {
            return true;
        }
        if (ur+dl >= 3) {
            return true;
        }

        return false;
    }

    private boolean isColor(int column, int row, boolean isGreen) {
        GameColumn gameColumn = columns.get(0);

        for(int q=0; q<7; q++) {
            if (columns.get(q).getColumnId()==column) {
                gameColumn = columns.get(q);
            }
        }

        if (gameColumn.getSpotsFilled() < row+1) {
            return false;
        }

        return gameColumn.getTileArray().get(row) == isGreen;
    }

    /**
     * Reset the board game
     */
    public void restart() {
        for(int q=0; q<7; q++) {
            columns.get(q).setTileArray(new ArrayList<>());
            columns.get(q).setSpotsFilled(0);
        }
        isGreensTurn = true;
    }

    /**
     * Save the game to a bundle
     * @param bundle The bundle we save to
     */
    public void saveInstanceState(Bundle bundle) {
        boolean [] tileArrays = new boolean[columns.size() * 6];
        int [] spotsFilled = new int[columns.size()];
        int [] columnIds = new int[columns.size()];

        for(int i = 0; i< columns.size(); i++) {
            GameColumn piece = columns.get(i);
            int j = 0;
            ArrayList<Boolean> tileArray = piece.getTileArray();
            int spotsFilledInColn = piece.getSpotsFilled();

            for (int k = 0; k < spotsFilledInColn; k++) {
                tileArrays[i*6+j] = tileArray.get(k);
                j += 1;
            }

            while (j < 6) {
                tileArrays[i*6+j] = false;
                j += 1;
            }
            spotsFilled[i] = piece.getSpotsFilled();
            columnIds[i] = piece.getColumnId();
        }

        bundle.putBoolean(TURN, isGreensTurn);
        if (justPlaced != null) {
            bundle.putInt(JUST_PLACED, justPlaced.getColumnId());
        } else {
            bundle.putInt(JUST_PLACED, -1);
        }
        bundle.putBooleanArray(TILES, tileArrays);
        bundle.putIntArray(SPOTS_FILLED,  spotsFilled);
        bundle.putIntArray(COLUMN_IDS,  columnIds);
    }

    /**
     * Read the game from a bundle
     * @param bundle The bundle we save to
     */
    public void loadInstanceState(Bundle bundle) {
        boolean [] tileArrays = bundle.getBooleanArray(TILES);
        int [] spotsFilled = bundle.getIntArray(SPOTS_FILLED);
        int [] columnIds = bundle.getIntArray(COLUMN_IDS);
        int justPlacedId = bundle.getInt(JUST_PLACED);
        isGreensTurn = bundle.getBoolean(TURN);

        for(int i=0; i<7; i++) {
            if (columns.get(i).getColumnId() == justPlacedId) {
                justPlaced = columns.get(i);
            }
        }

        for(int i=0; i<7; i++) {
            ArrayList<Boolean> tileArrayForColn = new ArrayList<>();
            int spotsFilledForColn = spotsFilled[i];

            for (int k = 0; k < spotsFilledForColn; k++) {
                tileArrayForColn.add(tileArrays[6*i+k]);
            }

            GameColumn workingColn = columns.get(0);
            for(int q=0; q<7; q++) {
                if (columns.get(q).getColumnId()==columnIds[i]) {
                    workingColn = columns.get(q);
                }
            }
            workingColn.setSpotsFilled(spotsFilledForColn);
            workingColn.setTileArray(tileArrayForColn);
        }
    }

}
