package com.example.zombie_outbreak.gameobject;

import android.content.Context;
import android.graphics.Canvas;
import android.media.MediaPlayer;


import androidx.core.content.ContextCompat;

import com.example.zombie_outbreak.GameDisplay;
import com.example.zombie_outbreak.GameLoop;
import com.example.zombie_outbreak.gamepanel.Joystick;
import com.example.zombie_outbreak.R;
import com.example.zombie_outbreak.gamepanel.HealthBar;
import com.example.zombie_outbreak.graphics.Animator;

/**
 * player is the main character of the game, which the user can control with a touch joystick
 * The player class is an extension of a circle, which is an extension of a GameObject
 */
public class Player extends Circle{

    public static final double SPEED_PIXELS_PER_SECOND = 200.0;
    public static final int MAX_HEALTH_POINTS = 20 ;
    public static final double MAX_SPEED =SPEED_PIXELS_PER_SECOND/ GameLoop.MAX_UPS;
    private final Joystick joystick;
    private HealthBar healthBar;
    private int healthPoints;
    private Animator animator;
    private PlayerState playerState;
    private MediaPlayer playerDeathSound;

    public Player(Context context, Joystick joystick, double positionX, double positionY, double radius, Animator animator){
        super(context,ContextCompat.getColor(context, R.color.player), positionX,positionY, radius );
        this.healthBar = new HealthBar(context, this);
        this.joystick = joystick;
        this.healthPoints = MAX_HEALTH_POINTS;
        this.animator = animator;
        this.playerState = new PlayerState(this);
        this.playerDeathSound = MediaPlayer.create(context, R.raw.player_death);
    }

    public void update() {
        //update velocity based on actuator of joystick
        velocityX = joystick.getActuatorX() * MAX_SPEED;
        velocityY = joystick.getActuatorY() * MAX_SPEED;

        //update position
        positionX += velocityX;
        positionY += velocityY;

        //update direction
        if(velocityX != 0 || velocityY != 0 )
        {//Normalize velocity to get direction(unit vector of velocity)
            double  distance = GameObject.getDistanceBetweenPoints(0,0,velocityX, velocityY);
            directionX = velocityX/distance;
            directionY = velocityY/distance;
        }

        playerState.update();
    }

    public void setPosition(double positionX, double positionY) {
        this.positionX = positionX;
        this.positionY = positionY;

    }

    public void draw(Canvas canvas, GameDisplay gameDisplay){
        animator.draw(canvas, gameDisplay, this);

        //super.draw(canvas, gameDisplay);
        healthBar.draw(canvas, gameDisplay);
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public void setHealthPoints(int healthPoints) {
        if (healthPoints >= 0) {
            this.healthPoints = healthPoints;
        }
    }

    public PlayerState getPlayerState()
    {
        return playerState;
    }
    public void PlayerDeathSound(){ playerDeathSound.start();}
}
