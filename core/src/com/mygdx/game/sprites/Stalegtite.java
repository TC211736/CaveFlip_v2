package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.MyGdxGame;

import java.util.Random;

public class Stalegtite {
    public static final int STALACTITE_WIDTH = 100;
    private Texture stalecmite;
    private Texture stalectite;
    private Vector2 posTop,posBot;
    private Random rand;
    private Rectangle boundsTop, boundsBot;
    private static final int FLUCTUATION = 50;
    private static final int ROCK_GAP = 130;
    private static final int LOWEST_OPENING = 60;
    private Polygon bounds2, bounds3;

    public Stalegtite(float x) {
        stalecmite = new Texture("Stalactite.png");
        stalectite = new Texture("Stalacmite.png");
        rand = new Random();

        posTop = new Vector2(x, rand.nextInt(FLUCTUATION) + ROCK_GAP + LOWEST_OPENING);
        posBot = new Vector2(x, posTop.y - ROCK_GAP - stalectite.getHeight());

        boundsTop = new Rectangle(posTop.x, posTop.y, stalectite.getWidth(), stalectite.getHeight());
        boundsBot = new Rectangle(posBot.x , posBot.y, stalecmite.getWidth(), stalecmite.getHeight());
        bounds2 = new Polygon();
        bounds2.setOrigin(posTop.x, posTop.y);
        bounds2.setVertices(new float[] {0, MyGdxGame.height / 2, 112, MyGdxGame.height / 2, 66, (MyGdxGame.height / 2) - 179});
        bounds3 = new Polygon();
        bounds3.setOrigin(posBot.x, posTop.y);
        bounds3.setVertices(new float[] {0,0,112,0,66,179});
    }

    public Texture getStalecmite() {
        return stalecmite;
    }

    public Texture getStalectite() {
        return stalectite;
    }

    public Vector2 getPosTop() {
        return posTop;
    }

    public Vector2 getPosBot() {
        return posBot;
    }
    public void reposition(float x) {
        posTop.set(x, rand.nextInt(FLUCTUATION) + ROCK_GAP + LOWEST_OPENING);
        posBot.set(x, posTop.y - ROCK_GAP - stalectite.getHeight());
        boundsTop.setPosition(posTop.x, posTop.y);
        boundsBot.setPosition(posBot.x, posBot.y);
        bounds2.setPosition(posTop.x, posTop.y);
        bounds3.setPosition(posBot.x,posBot.y);
    }

    public boolean collides(Polygon player) {
        boolean collides = false;
        if (Intersector.overlapConvexPolygons(player, bounds2) || (Intersector.overlapConvexPolygons(player, bounds3))) {
            collides = true;
        }
        return collides;
    }
}
