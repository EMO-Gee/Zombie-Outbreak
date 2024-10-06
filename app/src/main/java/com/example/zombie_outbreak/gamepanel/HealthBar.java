package com.example.zombie_outbreak.gamepanel;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

import com.example.zombie_outbreak.GameDisplay;
import com.example.zombie_outbreak.R;
import com.example.zombie_outbreak.gameobject.Player;

/**
 * HealthBar displays the players health to the screen
 */
public class HealthBar {

    private final Paint boarderPaint, healthPaint;
    private Player player;
    private int width, height, margin;

    public  HealthBar(Context context, Player player){
        this.player = player;
        this.width = 100;
        this.height = 20;
        this.margin = 5;

        this.boarderPaint = new Paint();
        int boarderColour = ContextCompat.getColor(context, R.color.healthBarBoarder);
        boarderPaint.setColor(boarderColour);

        this.healthPaint = new Paint();
        int healthColour = ContextCompat.getColor(context, R.color.healthBarHealth);
        healthPaint.setColor(healthColour);

    }

    public void draw(Canvas canvas, GameDisplay gameDisplay){
        float x = (float) player.getPositionX();
        float y = (float) player.getPositionY();
        float distanceToPlayer = 50;
        float healthPointsPercentage = (float) player.getHealthPoints()/player.MAX_HEALTH_POINTS;
        // draw boarder
        float boarderLeft, boarderTop, boarderRight, boarderBottom;
        boarderLeft  = x - width/2;
        boarderRight = x + width/2;
        boarderBottom = y - distanceToPlayer;
        boarderTop = boarderBottom - height;

        canvas.drawRect(
                (float) gameDisplay.gameToDisplayCoordinatesX(boarderLeft),
                (float) gameDisplay.gameToDisplayCoordinatesY(boarderTop),
                (float) gameDisplay.gameToDisplayCoordinatesX(boarderRight),
                (float) gameDisplay.gameToDisplayCoordinatesY(boarderBottom),
                boarderPaint);

        //draw health meter
        float healthLeft, healthTop, healthRight, healthBottom, healthWidth, healthHeight;
        healthWidth = width - 2 * margin;
        healthHeight = height - 2* margin;
        healthLeft = boarderLeft + margin;
        healthRight = healthLeft + healthWidth * healthPointsPercentage;
        healthBottom = boarderBottom - margin;
        healthTop = healthBottom - healthHeight;

        canvas.drawRect(
                (float) gameDisplay.gameToDisplayCoordinatesX(healthLeft),
                (float) gameDisplay.gameToDisplayCoordinatesY(healthTop),
                (float) gameDisplay.gameToDisplayCoordinatesX(healthRight),
                (float) gameDisplay.gameToDisplayCoordinatesY(healthBottom),
                healthPaint);
    }
}
