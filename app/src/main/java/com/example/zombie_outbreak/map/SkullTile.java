package com.example.zombie_outbreak.map;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.zombie_outbreak.graphics.Sprite;
import com.example.zombie_outbreak.graphics.SpriteSheet;

public class SkullTile extends Tile {
    private final Sprite grassSprite;
    private final Sprite SkullSprite;

    public SkullTile(SpriteSheet spriteSheet, Rect mapLocationRect) {
        super(mapLocationRect);
        grassSprite = spriteSheet.getGrassSprite();
        SkullSprite = spriteSheet.getSkullSprite();
    }

    public void draw(Canvas canvas) {
        grassSprite.draw(canvas, mapLocationRect.left, mapLocationRect.top);
        SkullSprite.draw(canvas, mapLocationRect.left, mapLocationRect.top);
    }
}
