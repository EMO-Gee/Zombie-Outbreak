package com.example.zombie_outbreakers;

import android.view.SurfaceHolder;

public class GameLoop {
    private boolean isRunning = false;

    public GameLoop(Game game, SurfaceHolder surfaceHolder) {
    }

    public double getAverageUPS() {
        return 0;
    }

    public double getAverageFPS() {
        return 0;
    }

    public void startLoop() {
        isRunning = true;
        start();
    }
}
