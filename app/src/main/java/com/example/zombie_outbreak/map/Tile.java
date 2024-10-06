package com.example.zombie_outbreak.map;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.zombie_outbreak.graphics.SpriteSheet;

abstract public class Tile {

    protected final Rect mapLocationRect;

    public Tile(Rect mapLocationRect) {
        this.mapLocationRect = mapLocationRect;
    }

    public abstract void draw(Canvas mapCanvas);

    public enum TileType{
        GRASS_PATCH_TILE,
        BUSH_TILE,
        GROUND_TILE,
        GRASS_TILE,
        SKULL_TILE,

    }

    public static Tile getTile(int idxTileType, SpriteSheet spriteSheet, Rect mapLocationRect) {

        switch(TileType.values()[idxTileType]){

            case GRASS_PATCH_TILE:
                return new GrassPatchTile(spriteSheet, mapLocationRect);
            case BUSH_TILE:
                return new BushTile(spriteSheet, mapLocationRect);
            case GROUND_TILE:
                return new GroundTile(spriteSheet, mapLocationRect);
            case GRASS_TILE:
                return new GrassTile(spriteSheet, mapLocationRect);
            case SKULL_TILE:
                return new SkullTile(spriteSheet, mapLocationRect);
            default:
                return null;
        }
    }
    
}
