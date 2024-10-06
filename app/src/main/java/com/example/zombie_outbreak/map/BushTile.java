package com.example.zombie_outbreak.map;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.zombie_outbreak.graphics.Sprite;
import com.example.zombie_outbreak.graphics.SpriteSheet;

public class BushTile extends Tile {
    private final Sprite bushSprite;
    private final Sprite grassPatchSprite;

    public BushTile(SpriteSheet spriteSheet, Rect mapLocationRect) {
        super(mapLocationRect);
        grassPatchSprite = spriteSheet.getGrassPatchSprite();
        bushSprite = spriteSheet.getBushSprite();
    }

    public void draw(Canvas canvas) {
        grassPatchSprite.draw(canvas,mapLocationRect.left, mapLocationRect.top);
        bushSprite.draw(canvas, mapLocationRect.left, mapLocationRect.top);
    }
}
