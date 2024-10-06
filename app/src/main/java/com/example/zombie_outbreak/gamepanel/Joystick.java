package com.example.zombie_outbreak.gamepanel;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.zombie_outbreak.gameobject.GameObject;

public class Joystick {

    private Paint smallCirclePaint;
    private Paint bigCirclePaint;
    private int smallCircleRadius;
    private int bigCircleRadius;
    private int bigCircleCenterPositionX;
    private int bigCircleCenterPositionY;
    private int smallCircleCenterPositionX;
    private int smallCircleCenterPositionY;
    private double joystickCenterToTouchDistance;
    private boolean isPressed;
    private double actuatorX;
    private double actuatorY;

    public Joystick(int centerPositionX, int centerPositionY, int outerRadii, int innerRadii){
        //outer and inner circle makes up the joystick
        bigCircleCenterPositionX = centerPositionX;
        bigCircleCenterPositionY = centerPositionY;
        smallCircleCenterPositionX = centerPositionX;
        smallCircleCenterPositionY = centerPositionY;

        //radii of each circle
        this.bigCircleRadius = outerRadii;
        this.smallCircleRadius = innerRadii;

        //paint of Circle
        bigCirclePaint = new Paint();
        bigCirclePaint.setColor(Color.GRAY);
        bigCirclePaint.setStyle(Paint.Style.FILL_AND_STROKE);

        smallCirclePaint = new Paint();
        smallCirclePaint.setColor(Color.BLUE);
        smallCirclePaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }
    public void draw(Canvas canvas) {
        canvas.drawCircle(bigCircleCenterPositionX,
                bigCircleCenterPositionY,
                bigCircleRadius,
                bigCirclePaint);

        canvas.drawCircle(smallCircleCenterPositionX,
                smallCircleCenterPositionY,
                smallCircleRadius,
                smallCirclePaint);
    }

    public void update() {
        updateInnerCirclePosition();
    }

    private void updateInnerCirclePosition() {
        smallCircleCenterPositionY = (int) (bigCircleCenterPositionY + actuatorY * bigCircleRadius);
        smallCircleCenterPositionX = (int) (bigCircleCenterPositionX + actuatorX * bigCircleRadius);

    }

    public boolean isPressed(double touchX, double touchY) {
        joystickCenterToTouchDistance = GameObject.getDistanceBetweenPoints(
                bigCircleCenterPositionX,
                bigCircleCenterPositionY,
                touchX,
                touchY);
        return joystickCenterToTouchDistance < bigCircleRadius;
    }

    public void setIsPressed(boolean isPressed) {
        this.isPressed = isPressed;
    }

    public boolean getIsPressed() {
        return isPressed;
    }

    public void setActuator(double touchX, double touchY) {
        double changeInX = touchX - bigCircleCenterPositionX;
        double changeInY = touchY - bigCircleCenterPositionY;
        double changeDistance = GameObject.getDistanceBetweenPoints(0,0, changeInX, changeInY);

        if(changeDistance < bigCircleRadius){
            actuatorX = changeInX / bigCircleRadius;
            actuatorY = changeInY / bigCircleRadius;
        }else {
            actuatorX = changeInX / changeDistance;
            actuatorY = changeInY / changeDistance;
        }
    }

    public void resetActuator() {
        actuatorX = 0.0;
        actuatorY = 0.0;
    }

    public double getActuatorX() {
        return actuatorX;
    }
    public double getActuatorY() {
        return actuatorY;
    }
}
