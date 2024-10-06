package com.example.zombie_outbreak.gameobject;

import android.content.Context;
import android.media.MediaPlayer;

import androidx.core.content.ContextCompat;

import com.example.zombie_outbreak.GameLoop;
import com.example.zombie_outbreak.R;

public class Bullet extends Circle {
    public static final double SPEED_PIXELS_PER_SECOND = 1200.0;
    private static final double MAX_SPEED =SPEED_PIXELS_PER_SECOND/ GameLoop.MAX_UPS;

    //Sounds
    private MediaPlayer gunshot;
    private final Player shooter;

    public Bullet(Context context, Player shooter) {
        super(context,
                ContextCompat.getColor(context, R.color.bullet),
                shooter.getPositionX(),
                shooter.getPositionY(),
                15
        );
        this.shooter = shooter;
        velocityX = shooter.getDirectionX() * MAX_SPEED;
        velocityY = shooter.getDirectionY() * MAX_SPEED;
        gunshot = MediaPlayer.create(context, R.raw.gunshot);
    }

    public void Gunshot()
    {
        gunshot.start();
    }


    public void update() {
        positionX += velocityX;
        positionY += velocityY;

    }
}
