package com.gdx.game.tools;

import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.gdx.game.sprites.Coin;
import com.gdx.game.sprites.Ground;

public class B2WorldCreator {

    public B2WorldCreator(World world, TiledMap map) {

        // create ground bodies/fixtures
        for(RectangleMapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = object.getRectangle();

            new Ground(world, map, rect);
        }

        // create coin bodies/fixtures
        for(RectangleMapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = object.getRectangle();

            new Coin(world, map, rect);
        }
    }
}
