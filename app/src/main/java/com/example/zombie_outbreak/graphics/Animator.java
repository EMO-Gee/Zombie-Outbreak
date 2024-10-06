package com.example.zombie_outbreak.graphics;

import android.graphics.Canvas;

import com.example.zombie_outbreak.GameDisplay;
import com.example.zombie_outbreak.gameobject.Player;

public class Animator {
/*
* this animates the player sprite in its 3 states
*/
    private Sprite[] arrPlayerSprite;
    private int idxFrame = 1;
    private int updatesBeforeNextMoveFrame;
    private double prevXPosition;

    public Animator(Sprite[] arrPlayerSprite) {
        this.arrPlayerSprite = arrPlayerSprite;
    }

    public void draw(Canvas canvas, GameDisplay gameDisplay, Player player) {
        switch (player.getPlayerState().getState())
        {
            case NOT_MOVING:
                drawFrame(canvas, gameDisplay, player, arrPlayerSprite[0]);
                prevXPosition = player.getPositionX();
                break;
            case STARTED_MOVING:
                updatesBeforeNextMoveFrame = 5;
                drawFrame(canvas, gameDisplay, player, arrPlayerSprite[idxFrame]);
                break;
            case IS_MOVING:
                updatesBeforeNextMoveFrame --;
                if(updatesBeforeNextMoveFrame == 0)
                {
                    updatesBeforeNextMoveFrame = 5;
                    if(player.getPositionX() < prevXPosition ) {
                        toggleIndxMovingLeftFrame();
                    }else
                    {
                        toggleIndxMovingRightFrame();
                    }
                }
                drawFrame(canvas, gameDisplay, player, arrPlayerSprite[idxFrame]);
                break;
            default:
                break;
        }
    }

    private void toggleIndxMovingRightFrame()
    {
        /*
        *switches/ flips the right looking sprite frames
        */
        if(idxFrame == 1)
        {
            idxFrame = 2;
        }else
        {
            idxFrame = 1;
        }
    }
    private void toggleIndxMovingLeftFrame()
    {
        /*
         *switches/ flips the left looking sprite frames
         */
        if (idxFrame == 3)
        {
            idxFrame = 4;
        }else
        {
            idxFrame = 3;
        }
    }

    public void drawFrame(Canvas canvas, GameDisplay gameDisplay, Player player, Sprite sprite)
    {
        sprite.draw(canvas,
                (int)gameDisplay.gameToDisplayCoordinatesX(player.getPositionX()) - sprite.getWidth()/2,
                (int)gameDisplay.gameToDisplayCoordinatesY(player.getPositionY()) - sprite.getHeight()/2
        );
    }
}
