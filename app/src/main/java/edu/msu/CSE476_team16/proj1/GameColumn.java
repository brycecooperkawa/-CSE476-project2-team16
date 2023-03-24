package edu.msu.CSE476_team16.proj1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import java.util.ArrayList;

public class GameColumn {
    /**
     * The image for the actual piece.
     */
    private Bitmap emptySquare;

    /**
     * The image for the actual piece.
     */
    private Bitmap greenTile;
    private Bitmap whiteTile;


    private int columnId;

    private ArrayList<Boolean> isGreenList = new ArrayList<>();

    private int spotsFilled = 0;

    private boolean floatingTile = false;
    private boolean isFloatingColorGreen = false;

    public GameColumn(Context context, int emptySquareId, int greenSquareId, int whiteSquareId, int columnId) {
        this.columnId = columnId;

        emptySquare = BitmapFactory.decodeResource(context.getResources(), emptySquareId);
        greenTile = BitmapFactory.decodeResource(context.getResources(), greenSquareId);
        whiteTile = BitmapFactory.decodeResource(context.getResources(), whiteSquareId);
    }

    /**
     * Draw the column
     * @param canvas Canvas we are drawing on
     * @param marginX Margin x value in pixels
     * @param marginY Margin y value in pixels
     * @param gameWidth Size we draw the game in pixels
     */
    public void draw(Canvas canvas, int marginX, int marginY,
                     int gameWidth) {
        canvas.save();

        // Convert x,y to pixels and add the margin, then draw
        canvas.translate(marginX, marginY);

        float squareWidth = (float) (1.0/7.0*gameWidth/emptySquare.getWidth());

        // Scale it to the right size
        canvas.scale(squareWidth, squareWidth);

        canvas.translate(columnId * emptySquare.getHeight(), 0);
        canvas.translate(0 , emptySquare.getHeight() );

        if (floatingTile) {
            canvas.translate(0 , -emptySquare.getHeight() );
            Bitmap tileToAdd = isFloatingColorGreen ? greenTile : whiteTile;
            canvas.drawBitmap(tileToAdd, 0, 0, null);
            canvas.translate(0 , emptySquare.getHeight() );
        }

        // Draw the bitmap
        canvas.drawBitmap(emptySquare, 0, 0, null);

        canvas.translate(0 , emptySquare.getHeight() );
        // Draw the bitmap
        canvas.drawBitmap(emptySquare, 0, 0, null);

        canvas.translate(0 , emptySquare.getHeight() );
        // Draw the bitmap
        canvas.drawBitmap(emptySquare, 0, 0, null);

        canvas.translate(0 , emptySquare.getHeight() );
        // Draw the bitmap
        canvas.drawBitmap(emptySquare, 0, 0, null);

        canvas.translate(0 , emptySquare.getHeight() );
        // Draw the bitmap
        canvas.drawBitmap(emptySquare, 0, 0, null);

        canvas.translate(0 , emptySquare.getHeight() );
        // Draw the bitmap
        canvas.drawBitmap(emptySquare, 0, 0, null);

        for (int i = 0; i < spotsFilled; i++) {
            Bitmap tileToAdd = isGreenList.get(i) ? greenTile : whiteTile;
            canvas.drawBitmap(tileToAdd, 0, 0, null);
            canvas.translate(0 , -emptySquare.getHeight() );
        }

        canvas.restore();
    }

    /**
     * Test to see if we have touched the column
     * @param testX X location as a normalized coordinate (0 to 1)
     * @param testY Y location as a normalized coordinate (0 to 1)
     * @return true if we hit the piece
     */
    public boolean hit(float testX, float testY) {

        if (testX < 1.0/7.0 * columnId || testX > 1.0/7.0 * columnId + 1.0/7.0) {
            return false;
        }

        if (testY < 0 || testY > 1) {
            return false;
        }

        return true;
    }


    public boolean addTile(boolean isGreen) {
        if (spotsFilled < 6) {
            spotsFilled += 1;
            isGreenList.add(isGreen);
            return true;
        }
        return false;
    }

    public void removeTopTile() {
        spotsFilled -= 1;
        isGreenList.remove(spotsFilled);
    }

    public void displayFloatingTile(boolean isGreen) {
        isFloatingColorGreen = isGreen;
        floatingTile = true;
    }

    public void removeFloatingTile() {
        floatingTile = false;
    }

    public ArrayList<Boolean> getTileArray() {
        return isGreenList;
    }
    public int getSpotsFilled() {
        return spotsFilled;
    }
    public int getColumnId() {
        return columnId;
    }

    public void setTileArray(ArrayList<Boolean> newIsGreenList) {
        isGreenList = newIsGreenList;
    }
    public void setSpotsFilled(int newSpotsFilled) {
        spotsFilled = newSpotsFilled;
    }
}
