package com.example.zombie_outbreak.map;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.zombie_outbreak.graphics.Sprite;
import com.example.zombie_outbreak.graphics.SpriteSheet;

public class GrassTile extends Tile {
    private final Sprite sprite;

    public GrassTile(SpriteSheet spriteSheet, Rect mapLocationRect) {
        super(mapLocationRect);
        sprite = spriteSheet.getGrassSprite();
    }

    public void draw(Canvas canvas) {
        sprite.draw(canvas, mapLocationRect.left, mapLocationRect.top);
    }
}
