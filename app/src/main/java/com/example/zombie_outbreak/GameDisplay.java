package com.example.zombie_outbreak;

import android.graphics.Rect;

import com.example.zombie_outbreak.gameobject.GameObject;

public class GameDisplay {
    public final Rect DISPLAY_RECT;
    private final int widthPixels;
    private final int heightPixels;
    private double gameToDisplayCoordinatesOffsetX;
    private double gameToDisplayCoordinatesOffsetY;
    private double displayCenterX;
    private double displayCenterY;
    private double gameCenterX;
    private double gameCenterY;
    private GameObject centerObject;

    public GameDisplay(int widthPixel, int heightPixel, GameObject centerObject)
    {
        this.widthPixels = widthPixel;
        this.heightPixels = heightPixel;
        DISPLAY_RECT = new Rect(0,0, widthPixel, heightPixels);
        this.centerObject = centerObject;

        displayCenterX = widthPixel/2.0;
        displayCenterY = heightPixel/2.0;
    }

    public void update()
    {
        gameCenterX = centerObject.getPositionX();
        gameCenterY = centerObject.getPositionY();

        gameToDisplayCoordinatesOffsetX = gameCenterX;
        gameToDisplayCoordinatesOffsetY = gameCenterY;
    }

    public double gameToDisplayCoordinatesX(double positionX) {
        return positionX + gameToDisplayCoordinatesOffsetX;
    }

    public double gameToDisplayCoordinatesY(double positionY) {
        return positionY + gameToDisplayCoordinatesOffsetY;
    }

    public Rect getGameRect() {
        return  new Rect(
                0,
                0,
                (int)(gameCenterX + widthPixels),
                (int)(gameCenterY + heightPixels)

        );
    }
}
