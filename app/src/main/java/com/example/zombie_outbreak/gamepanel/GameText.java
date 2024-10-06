package com.example.zombie_outbreak.gamepanel;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

import com.example.zombie_outbreak.R;


/**
 * GameOver is a text which draws the text Game Over to screen.
 */
public class GameText {

    private Context context;

    public GameText(Context context) {
        this.context = context;
    }

    public void drawGameOverText(Canvas canvas) {
        String message = "Game Over";

        float x = 800;
        float y = 200;

        Paint paint = new Paint();

        int colour = ContextCompat.getColor(context, R.color.gameOver);
        paint.setColor(colour);
        float textSize = 150;
        paint.setTextSize(textSize);

        canvas.drawText(message, x, y, paint);
    }
    public void drawLevelText(Canvas canvas, String text)
    {
        String message = text;

        float x = 800;
        float y = 200;

        Paint paint = new Paint();
            int colour = ContextCompat.getColor(context, R.color.levelUp);
            paint.setColor(colour);
            float textSize = 150;
            paint.setTextSize(textSize);

            canvas.drawText(message, x, y, paint);
    }
}
