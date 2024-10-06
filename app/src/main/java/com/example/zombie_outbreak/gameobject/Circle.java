package com.example.zombie_outbreak.gameobject;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.zombie_outbreak.GameDisplay;
import com.example.zombie_outbreak.R;

public abstract class Circle extends GameObject {
    protected double radius;
    protected Paint paint;
    //private GameDisplay gameDisplay;

    public Circle(Context context,int color, double positionX, double positionY, double radius) {
        super(positionX, positionY);

        this.radius = radius;

        paint = new Paint();
        paint.setColor(color);
    }

    /**
     * isColliding checks if two circle objects are colliding, based on their position and radii
     * @param obj1
     * @param obj2
     * @return
     */
    public static boolean isColliding(Circle obj1, Circle obj2) {
        double distance = getDistanceBetweenObjects(obj1,obj2);
        double distanceToCollision = obj1.getRadius() + obj2.getRadius();
        if(distance < distanceToCollision)
        {
            return true;
        } else
        {
            return false;
        }
    }

    public static void knockBack(Circle obj1, Circle obj2, double widthPixels, double heightPixels)
    {
        /*
        *Obj1 is the object that will receive the knock-back based on the the X-position of obj2(moving the object left or right)
         */
        if (obj1.positionX < obj2.positionX)
        {
            if((obj1.positionX - 50) > 0.0 )
            {
                obj1.positionX -= 50;
                obj2.positionX += 10;
            }
            else if((obj1.positionX + 50) > widthPixels)
            {
                obj1.positionY -= 50;
                obj2.positionX  +=10;
            }
            else
            {
                obj1.positionY  += 50;
                obj2.positionX  -=10;
            }
        }else
        {
            if((obj1.positionX + 50) > widthPixels ) {
                //obj1.positionX -= 50;
                obj2.positionX -= 150;
            }else if((obj1.positionY + 50) > heightPixels)
            {
                obj1.positionY += 50;
                obj2.positionX  -=10;
            }
            else
            {
                obj1.positionY  -= 50;
                obj2.positionX  +=10;
            }
        }

    }
    private double getRadius() {
        return radius;
    }

    public void draw(Canvas canvas, GameDisplay gameDisplay) {
        canvas.drawCircle(
                (float) gameDisplay.gameToDisplayCoordinatesX(positionX),
                (float) gameDisplay.gameToDisplayCoordinatesY(positionY),
                (float)radius,
                paint);
    }

}
