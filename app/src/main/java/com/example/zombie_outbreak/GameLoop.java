package com.example.zombie_outbreak;

import android.graphics.Canvas;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;

public class GameLoop  extends  Thread{
    public static final double MAX_UPS = 30.0;
    private static final double UPS_PERIOD = 1000/MAX_UPS;
    private Game game;
    private boolean isRunning = false;
    private SurfaceHolder surfaceHolder;

    public GameLoop(Game game, SurfaceHolder surfaceHolder) {
        this.game = game;
        this.surfaceHolder = surfaceHolder;
    }


    public void startLoop() {
        isRunning = true;
        start();
    }

    @Override
    public void run() {
        super.run();
        // declare time and cycle count variables
        int updateCount = 0;
        int frameCount = 0;

        long startTime;
        long elapsedTime;
        long sleepTime;

        //Game Loop
        Canvas canvas = null;
        startTime = System.currentTimeMillis();
        while(isRunning){
            //try to update and load the game
            try{
                canvas = surfaceHolder.lockCanvas();
                //
                synchronized (surfaceHolder)
                {
                    game.update();
                    updateCount ++;
                    game.draw(canvas);
                    }
            } catch (Exception e){
                e.printStackTrace();
            }finally {
                if (canvas != null) {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                        frameCount++;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            //Pause game loop to exceed target UPS
            elapsedTime = System.currentTimeMillis()- startTime;
            sleepTime = (long)(updateCount * UPS_PERIOD - elapsedTime);
            if(sleepTime > 0)
            {
                try {
                    sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //Skips frames to keep up with the target UPS
            while(sleepTime < 0 && updateCount< MAX_UPS - 1)
            {
                game.update();
                updateCount++;
                elapsedTime = System.currentTimeMillis()- startTime;
                sleepTime = (long)(updateCount * UPS_PERIOD - elapsedTime);
            }

            if(!game.playerAlive)
            {
                stopLoop();
            }

            elapsedTime = System.currentTimeMillis() - startTime;
            if(elapsedTime >= 1000)
            {
                updateCount = 0;
                frameCount = 0;
                startTime = System.currentTimeMillis();
            }

        }
    }

    public void stopLoop() {
        //Log.d("GameLoop.java", "stopLoop()");
        isRunning = false;
        //Wait for thread to join
        try{
            join();
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }
}
