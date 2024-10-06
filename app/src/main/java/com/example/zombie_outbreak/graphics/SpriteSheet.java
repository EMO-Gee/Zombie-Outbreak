package com.example.zombie_outbreak.graphics;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import com.example.zombie_outbreak.R;

public class SpriteSheet {
    private static final int SPRITE_WIDTH_PIXELS = 64;
    private static final int SPRITE_HEIGHT_PIXELS = 64;
    private Bitmap bitmap;

    public SpriteSheet(Context context)
    {
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inScaled = false;
        bitmap= BitmapFactory.decodeResource(context.getResources(), R.drawable.sprite_v2_1, bitmapOptions);

    }

    public  Sprite[] getPlayerSpriteArray()
    {
        /*
        * loads the player frames i
        * */
        Sprite[] spriteArray = new Sprite[6];
        spriteArray[0] = new Sprite(this, new Rect(0*64,0, 1*64,64));
        spriteArray[1] = new Sprite(this, new Rect(1*64,0, 2*64,64));
        spriteArray[2] = new Sprite(this, new Rect(2*64,0, 3*64,64));
        spriteArray[3] = new Sprite(this, new Rect(3*64,0, 4*64,64));
        spriteArray[4] = new Sprite(this, new Rect(4*64,0, 5*64,64));
        spriteArray[5] = new Sprite(this, new Rect(5*64,0, 6*64,64));
        return spriteArray;
    } // this cover the sprite of a 64*64 bit sprite

    public Sprite[] getEnemySpriteArray()
    {
        Sprite[] spriteArray = new Sprite[2];
        spriteArray[0] = null;
        return spriteArray;
    }
    public Bitmap getBitmap() {
        return bitmap;
    }

    public Sprite getGrassPatchSprite() {
        return getSpriteByIndex(1, 0);
    }

    public Sprite getGroundSprite() {
        return getSpriteByIndex(1, 1);
    }

    public Sprite getGrassSprite() {
        return getSpriteByIndex(1, 2);
    }

    public Sprite getBushSprite() {
        return getSpriteByIndex(1, 3);
    }

    public Sprite getSkullSprite() {
        return getSpriteByIndex(1, 4);
    }

    private Sprite getSpriteByIndex(int idxRow, int idxCol) {
        return new Sprite(this,new Rect(
            idxCol * SPRITE_WIDTH_PIXELS,
                idxRow * SPRITE_HEIGHT_PIXELS,
                (idxCol + 1) * SPRITE_WIDTH_PIXELS  ,
                (idxRow + 1) * SPRITE_HEIGHT_PIXELS
        ));
    }
}
