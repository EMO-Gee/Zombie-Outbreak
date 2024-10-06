package com.example.zombie_outbreak.gameobject;

import android.content.Context;
import android.graphics.Canvas;
import android.media.MediaPlayer;

import androidx.core.content.ContextCompat;

import com.example.zombie_outbreak.GameDisplay;
import com.example.zombie_outbreak.GameLoop;
import com.example.zombie_outbreak.R;

/**
 * Enemy is a character which always moves in the direction of the player
 * The enemy class is an extension of the circle, which is the extension of a GameObject
 */

public class Enemy extends Circle {

    //sound
    private MediaPlayer deathSound;

    private static final double SPEED_PIXELS_PER_SECOND = Player.SPEED_PIXELS_PER_SECOND *0.4;
    private static final double MAX_SPEED =SPEED_PIXELS_PER_SECOND/ GameLoop.MAX_UPS;
    private static final double SPAWNS_PER_MINUTE = 20;
    private static final double SPAWNS_PER_SECOND = SPAWNS_PER_MINUTE/60.0;
    private static final double UPDATES_PER_SPAWN = GameLoop.MAX_UPS / SPAWNS_PER_SECOND;
    private static double updatesUnitNextSpawn = UPDATES_PER_SPAWN;
    private  static int hp;

    private final Player player;
    private static MediaPlayer enemyDeathSound;
    private static MediaPlayer enemyAttackSound;

    public Enemy(Context context, Player player, int health) {
        super(context,
              ContextCompat.getColor(context, R.color.enemy),
              Math.random()*600 + player.getPositionX(),
              Math.random()*600 + player.getPositionY(),
              30
        );
        this.player = player;
        this.hp = health;

        enemyDeathSound = MediaPlayer.create(context, R.raw.zombie_death);
        enemyAttackSound = MediaPlayer.create(context, R.raw.zombie_attack);
    }

    /**
     * readyToSpawn checks if a new enemy should spawn, according to the decided number of spawns
     */
    public static boolean readyToSpawn()
    {
        if(updatesUnitNextSpawn <= 0)
        {
            updatesUnitNextSpawn += UPDATES_PER_SPAWN;
            return true;
        }else
        {
            updatesUnitNextSpawn --;
            return false;
        }
    }


    public void update() {
        // update the velocity of the enemy so that the velocity is in the direction of the player
        // calculate the vector from enemy to player
        double distanceToPlayerX = player.getPositionX() - positionX;
        double distanceToPlayerY = player.getPositionY() - positionY;

        //calculate (absolute) distance between enemy (this) and player
        double  distanceToPlayer = GameObject.getDistanceBetweenObjects(this, player);

        //Calculate direction from the enemy to player
        double directionX = distanceToPlayerX/distanceToPlayer;
        double directionY = distanceToPlayerY/distanceToPlayer;

        //Set velocity in the direction to the player
        if(distanceToPlayer > 0)
        { // prevent it being divided by zero
            velocityX = directionX * MAX_SPEED;
            velocityY = directionY * MAX_SPEED;
        }else
        {
            velocityX = 0;
            velocityY = 0;
        }

        //Update the position of the enemy
        positionX += velocityX;
        positionY += velocityY;
    }

    public void EnemyDeathSound()
    {
        enemyDeathSound.start();
    }
    public void EnemyAttackSound()
    {
        enemyAttackSound.start();
    }

    public int getHp()
    {
        return this.hp;
    }
    public void setHp(int Hp)
    {
        this.hp = Hp;
    }

    public void draw(Canvas canvas, GameDisplay gameDisplay){
        super.draw(canvas, gameDisplay);

    }
}
