package com.example.zombie_outbreak.map;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.zombie_outbreak.graphics.Sprite;
import com.example.zombie_outbreak.graphics.SpriteSheet;

public class GrassPatchTile extends Tile {
    private final Sprite sprite;

    public GrassPatchTile(SpriteSheet spriteSheet, Rect mapLocationRect) {
        super(mapLocationRect);
        sprite = spriteSheet.getGrassPatchSprite();
    }

    public void draw(Canvas canvas) {
        sprite.draw(canvas, mapLocationRect.left, mapLocationRect.top);
    }
}
