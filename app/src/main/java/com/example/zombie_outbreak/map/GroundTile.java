package com.example.zombie_outbreak.map;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.zombie_outbreak.graphics.Sprite;
import com.example.zombie_outbreak.graphics.SpriteSheet;

public class GroundTile extends Tile {
    private final Sprite sprite;

    public GroundTile(SpriteSheet spriteSheet, Rect mapLocationRect) {
        super(mapLocationRect);
        sprite = spriteSheet.getGroundSprite();
    }

    public void draw(Canvas canvas) {
        sprite.draw(canvas, mapLocationRect.left, mapLocationRect.top);
    }
}
