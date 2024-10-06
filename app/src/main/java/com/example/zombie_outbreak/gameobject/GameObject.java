package com.example.zombie_outbreak.gameobject;

import android.graphics.Canvas;

import com.example.zombie_outbreak.GameDisplay;

public class GameObject {
    public double positionX;
    public double positionY;
    public double velocityX; 
    public double velocityY; 
    public double directionX;
    public double directionY;

    public GameObject(double positionX, double positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public double getPositionX() {
        return positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public static double getDistanceBetweenObjects(GameObject obj1, GameObject obj2) {
        return Math.sqrt(
                Math.pow(obj2.getPositionX() - obj1.getPositionX(), 2) +
                        Math.pow(obj2.getPositionY() - obj1.getPositionY(), 2)
        );
    }

    public static double getDistanceBetweenPoints(double p1x, double p1y, double p2x, double p2y) {
        return Math.sqrt(
                Math.pow(p1x - p2x, 2) +
                        Math.pow(p1y - p2y, 2)
        );
    }

    public double getDirectionX() {
        return directionX;
    }
    public double getDirectionY() {
        return directionY;
    }
}