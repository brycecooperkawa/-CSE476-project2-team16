package edu.msu.CSE476_team16.proj1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class GameBoardView extends View {
    private Paint linePaint;

    /**
     * The actual game board
     */
    private GameBoard gameBoard;


    public GameBoardView(Context context) {
        super(context);
        init(null, 0);
    }

    public GameBoardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public GameBoardView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gameBoard.onTouchEvent(this, event);
    }

    private void init(AttributeSet attrs, int defStyle) {
        gameBoard = new GameBoard(getContext());

        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setColor(0xff008000);
        linePaint.setStrokeWidth(3);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        gameBoard.draw(canvas);
    }

    /**
     * Save the game to a bundle
     * @param bundle The bundle we save to
     */
    public void saveInstanceState(Bundle bundle) {
        gameBoard.saveInstanceState(bundle);
    }

    /**
     * Load the game from a bundle
     * @param bundle The bundle we save to
     */
    public void loadInstanceState(Bundle bundle) {
        gameBoard.loadInstanceState(bundle);
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }
}
