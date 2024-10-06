package com.example.zombie_outbreak;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import com.example.zombie_outbreak.gameobject.Bullet;
import com.example.zombie_outbreak.gameobject.Circle;
import com.example.zombie_outbreak.gameobject.Enemy;
import com.example.zombie_outbreak.gameobject.Player;
import com.example.zombie_outbreak.gamepanel.GameText;
import com.example.zombie_outbreak.gamepanel.Joystick;
import com.example.zombie_outbreak.graphics.Animator;
import com.example.zombie_outbreak.graphics.SpriteSheet;
import com.example.zombie_outbreak.map.Tilemap;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Game manage all objects in the game and is responsible for updating all states and render
 * object to the screen
 */
public class Game extends SurfaceView implements SurfaceHolder.Callback {

    private static final long LEVEL_COMPLETE_DELAY = 5000; //Delays for 1 sec

    private final Player player;
    private final Joystick joystick;
    private final Tilemap tilemap;
    private GameLoop gameLoop;
    //private Button backButton; // Make sure this is accessible
    private List<Enemy> enemyList = new ArrayList<Enemy>();
    private List<Bullet> ammo = new ArrayList<Bullet>();
    private int joystickPointerID = 0;
    private int numberOfBulletsToShoot = 0;
    private GameText gameText;
    private GameDisplay gameDisplay;
    private int level = 1;
    private int spawnRate = 5;
    private int enemyHP = level;
    private boolean levelCompleted;
    private int numEnemy = spawnRate * level;
    private Bullet bullet;
    public boolean playerAlive;
    private long textDisplayTime = -1;

    public Game(Context context) {
        super(context);

        //Get surface holder and add callback
        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        gameLoop = new GameLoop(this, surfaceHolder);

        // initialize game panels
        gameText = new GameText(context);
        joystick = new Joystick(275,900, 140,80);

        //initialize game objects
        SpriteSheet spriteSheet = new SpriteSheet(context);
        Animator animator = new Animator(spriteSheet.getPlayerSpriteArray());
        player = new Player(context, joystick,750, 250,25, animator);
        playerAlive = true;
        bullet = new Bullet(context, player);

        //initialize game display and center it around the player
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        gameDisplay = new GameDisplay(displayMetrics.widthPixels, displayMetrics.heightPixels, player);
        levelCompleted = false;
        this.numEnemy = 0;

        // initialize our tilemap
        tilemap = new Tilemap(spriteSheet);

        setFocusable(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        //handle touch event action
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                if (joystick.getIsPressed())
                {// Joystick was pressed before this event -> shoot bullet
                    bullet.Gunshot();
                    numberOfBulletsToShoot ++;
                    bullet.Gunshot();

                }   else if(joystick.isPressed((double) event.getX(), (double) event.getY()))
                {//Joystick is pressed in this event -> setIsPressed(true) and store ID
                    joystickPointerID = event.getPointerId(event.getActionIndex());
                    joystick.setIsPressed(true);
                }else
                {//Joystick was not previously, and is not pressed  in this event -> shoot Bullet
                    numberOfBulletsToShoot ++;
                    bullet.Gunshot();
                }
                return true;
            case MotionEvent.ACTION_MOVE:
                //Joystick was pressed previously and is now moved
                if(joystick.getIsPressed()){
                    joystick.setActuator((double) event.getX(), (double) event.getY());
                }
                return true;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                if(joystickPointerID == event.getPointerId(event.getActionIndex()))
                {
                    //Joystick was let go of -> setIsPressed(false) and restActuator
                    joystick.setIsPressed(false);
                    joystick.resetActuator();
                }

                return true;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        Log.d("Game.java","surfaceCreated()");
        if(gameLoop.getState().equals(Thread.State.TERMINATED)){
            gameLoop = new GameLoop(this, surfaceHolder);
        }
        gameLoop.startLoop();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        //Log.d("Game.java","surfaceChanged()");
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
        //Log.d("Game.java","surfaceDestroy()");
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        //draw tilemap
        tilemap.draw(canvas, gameDisplay);

        player.draw(canvas, gameDisplay);

        for(Enemy enemy: enemyList)
        {
                enemy.draw(canvas, gameDisplay);
        }

        for(Bullet bullet : ammo)
        {
            bullet.draw(canvas, gameDisplay);
        }

        //draws game panels
        joystick.draw(canvas);

        // Draw Game Over if the player is dead
        if(player.getHealthPoints() <= 0)
        {
            gameText.drawGameOverText(canvas);
            long preTime = System.currentTimeMillis();
            gameText.drawGameOverText(canvas);

        }

        //Draw Game level if all zombie are "dead?"
        if(levelCompleted )
        {
            gameText.drawLevelText(canvas, "Level" + level + "Completed");
            levelCompleted = false;
            long preTime = System.currentTimeMillis();
            while (System.currentTimeMillis() - preTime < 2000)
            {
                // creates a pause=ish
                gameText.drawLevelText(canvas, "Level" + level + "Completed");

            }
        }
    }

    public void update() {
        //stop updating the game if the player is dead
        if(player.getHealthPoints() <= 0){
            if(playerAlive)
            {
                player.PlayerDeathSound();
                playerAlive = false;
            }
            return;
        }
        //Update game state
        joystick.update();
        //this will box the player into the phone's frame
        double newPlayerPositionX = player.getPositionX() + joystick.getActuatorX() * player.MAX_SPEED;
        double newPlayerPositionY = player.getPositionY() + joystick.getActuatorY() * player.MAX_SPEED;
        if(
                (0.0 <= newPlayerPositionX) && (newPlayerPositionX <= gameDisplay.getGameRect().width())
        && ((0.0 <=newPlayerPositionY) && (newPlayerPositionY <= gameDisplay.getGameRect().height()))
        ) {
            player.update();
        }
        //Spawn enemy if is time to spawn new enemies
        if(Enemy.readyToSpawn() && this.spawnRate > 0 )
        {
            enemyList.add(new Enemy(getContext(), player, this.enemyHP * level));
            spawnRate--;
        }
        else if(Enemy.readyToSpawn() &&  enemyList.size() == 0) {
            this.level += 1;
            this.spawnRate = 5 * this.level;
            enemyList.add(new Enemy(getContext(), player, this.enemyHP * level));
            levelCompleted = true;
        }
        //update state of each enemy

        for(Enemy enemy : enemyList)
        {
            enemy.update();
        }

        //update state of each bullet
        while(numberOfBulletsToShoot > 0){
            ammo.add(new Bullet(getContext(), player));
            numberOfBulletsToShoot --;

        }
        for(Bullet bullet : ammo)
        {
            bullet.update();
        }

        //iterate through enemyList and check for collision between each enemy and the player and all bullets
        //Bullets in ammo(magazine)

        Iterator<Enemy> iteratorEnemy = enemyList.iterator();
        while(iteratorEnemy.hasNext())
        {
            Circle enemy = iteratorEnemy.next();

            if(Circle.isColliding(enemy,player))
            {
                //remove current enemy if collides with player player hp lowers
                Circle.knockBack(player,enemy,gameDisplay.getGameRect().width(), gameDisplay.getGameRect().height());
                player.setHealthPoints(player.getHealthPoints() - 1);
                ((Enemy) enemy).EnemyAttackSound();
                continue;
            }
            Iterator<Bullet> iteratorBullet = ammo.iterator();
            while(iteratorBullet.hasNext())
            {
                Circle bullet = iteratorBullet.next();
                //remove bullet if it collides with an enemy
                if(Circle.isColliding(bullet, enemy))
                {

                    Circle.knockBack(enemy,bullet,gameDisplay.getGameRect().width(), gameDisplay.getGameRect().height());
                    if (((Enemy) enemy).getHp() == 0)
                    {

                        ((Enemy) enemy).EnemyDeathSound();
                        iteratorEnemy.remove();
                        numEnemy --;
                    }else
                    {
                        ((Enemy) enemy).setHp(((Enemy) enemy).getHp() - 1);
                    }
                    iteratorBullet.remove();

                }
            }
        }
        //gameDisplay.update();
    }



   /*
    public void pause() {
        gameLoop.stopLoop();
    }
    */
}

