package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class stalactite {
    public static final int STALACTITE_WIDTH = 180;
    private Texture stalagmite;
    private Texture stalactite;
    private Vector2 posTop, posBot;
    private Random rand;
    private Polygon boundsBotNew;
    private Polygon boundsTopNew;

    private static final int FLUCTUATIONY = 50;
    private static final int FLUCTUATIONX = 150;
    private static final int ROCK_GAP = 130;
    private static final int LOWEST_OPENING = 60;

    public stalactite(float x) {
        stalagmite = new Texture("Textures/StalactiteV2.png");
        stalactite = new Texture("Textures/StalagmiteV2.png");
        rand = new Random();

        posTop = new Vector2(x - rand.nextInt(FLUCTUATIONX), rand.nextInt(FLUCTUATIONY) + ROCK_GAP + LOWEST_OPENING);
        posBot = new Vector2(x + rand.nextInt(FLUCTUATIONX), posTop.y - ROCK_GAP - stalactite.getHeight());
        float[] verticesStalagmite = verticesStalagmite();
        float[] verticesStalactite = verticesStalactite();
        boundsBotNew = new Polygon(verticesStalagmite);
        boundsBotNew.setOrigin(getPolygonCenterBot().x, getPolygonCenterBot().y);
        boundsTopNew = new Polygon(verticesStalactite);
        boundsTopNew.setOrigin(getPolygonCenterTop().x, getPolygonCenterTop().y);

        boundsTopNew.setRotation(180);
        boundsTopNew.translate(7, -7);

    }

    private float[] verticesStalagmite() {
        float[] vertices = new float[]{
                8 + posBot.x, 0 + posBot.y, //bottom left of stalagmite
                12 + posBot.x, 22 + posBot.y,
                9 + posBot.x, 22 + posBot.y,
                0 + posBot.x, 27 + posBot.y,
                0 + posBot.x, 47 + posBot.y,  //vertices around stalagmite
                10 + posBot.x, 47 + posBot.y,
                10 + posBot.x, 74 + posBot.y,
                13 + posBot.x, 83 + posBot.y,
                28 + posBot.x, 99 + posBot.y,
                30 + posBot.x, 98 + posBot.y,
                30 + posBot.x, 111 + posBot.y,
                36 + posBot.x, 116 + posBot.y,
                36 + posBot.x, 129 + posBot.y,
                44 + posBot.x, 138 + posBot.y,
                45 + posBot.x, 153 + posBot.y,
                50 + posBot.x, 153 + posBot.y,
                50 + posBot.x, 161 + posBot.y,
                55 + posBot.x, 165 + posBot.y,
                55 + posBot.x, 170 + posBot.y,
                58 + posBot.x, 175 + posBot.y,
                64 + posBot.x, 175 + posBot.y,
                64 + posBot.x, 160 + posBot.y,
                68 + posBot.x, 147 + posBot.y,
                72 + posBot.x, 145 + posBot.y,
                72 + posBot.x, 133 + posBot.y,
                82 + posBot.x, 90 + posBot.y,
                86 + posBot.x, 87 + posBot.y,
                86 + posBot.x, 61 + posBot.y,
                82 + posBot.x, 61 + posBot.y,
                82 + posBot.x, 39 + posBot.y,
                90 + posBot.x, 39 + posBot.y,
                93 + posBot.x, 42 + posBot.y,
                100 + posBot.x, 42 + posBot.y,
                110 + posBot.x, 37 + posBot.y,
                103 + posBot.x, posBot.y

        };
        return vertices;
    }

    private float[] verticesStalactite() {

        float[] vertices = new float[]{
                8 + posTop.x, 0 + posTop.y, //bottom left of stalagmite
                12 + posTop.x, 22 + posTop.y,
                9 + posTop.x, 22 + posTop.y,
                0 + posTop.x, 27 + posTop.y,
                0 + posTop.x, 47 + posTop.y,  //vertices around stalagmite
                10 + posTop.x, 47 + posTop.y,
                10 + posTop.x, 74 + posTop.y,
                13 + posTop.x, 83 + posTop.y,
                28 + posTop.x, 99 + posTop.y,
                30 + posTop.x, 98 + posTop.y,
                30 + posTop.x, 111 + posTop.y,
                36 + posTop.x, 116 + posTop.y,
                36 + posTop.x, 129 + posTop.y,
                44 + posTop.x, 138 + posTop.y,
                45 + posTop.x, 153 + posTop.y,
                50 + posTop.x, 153 + posTop.y,
                50 + posTop.x, 161 + posTop.y,
                55 + posTop.x, 165 + posTop.y,
                55 + posTop.x, 170 + posTop.y,
                58 + posTop.x, 175 + posTop.y,
                64 + posTop.x, 175 + posTop.y,
                64 + posTop.x, 160 + posTop.y,
                68 + posTop.x, 147 + posTop.y,
                72 + posTop.x, 145 + posTop.y,
                72 + posTop.x, 133 + posTop.y,
                82 + posTop.x, 90 + posTop.y,
                86 + posTop.x, 87 + posTop.y,
                86 + posTop.x, 61 + posTop.y,
                82 + posTop.x, 61 + posTop.y,
                82 + posTop.x, 39 + posTop.y,
                90 + posTop.x, 39 + posTop.y,
                93 + posTop.x, 42 + posTop.y,
                100 + posTop.x, 42 + posTop.y,
                110 + posTop.x, 37 + posTop.y,
                103 + posTop.x, posTop.y
        };

        return vertices;
    }


    public Texture getStalagmite() {
        return stalagmite;
    }

    public Texture getStalactite() {
        return stalactite;
    }

    public Vector2 getPosTop() {
        return posTop;
    }

    public Vector2 getPosBot() {
        return posBot;
    }

    public Polygon getBoundsBotNew() {
        return boundsBotNew;
    }

    public Polygon getBoundsTopNew() {
        return boundsTopNew;
    }

    public void reposition(float x) {
        posTop.set(x, rand.nextInt(FLUCTUATIONY) + ROCK_GAP + LOWEST_OPENING);
        posBot.set(x, posTop.y - ROCK_GAP - stalactite.getHeight());
        boundsTopNew.setPosition(posTop.x, posTop.y);
        boundsBotNew.setPosition(posBot.x, posBot.y);
    }

    private Vector2 getPolygonCenterTop() {
        float[] vertices = boundsTopNew.getTransformedVertices();
        float x = 0, y = 0;
        for (int i = 0; i < vertices.length; i += 2) {
            x += vertices[i];
            y += vertices[i + 1];
        }
        x /= vertices.length / 2;
        y /= vertices.length / 2;
        return new Vector2(x, y);
    }

    private Vector2 getPolygonCenterBot() {
        float[] vertices = boundsBotNew.getTransformedVertices();
        float x = 0, y = 0;
        for (int i = 0; i < vertices.length; i += 2) {
            x += vertices[i];
            y += vertices[i + 1];
        }
        x /= vertices.length / 2;
        y /= vertices.length / 2;
        return new Vector2(x, y);
    }

    public boolean collides(Polygon player) {

        boolean collisionDetected = false;
        if (Intersector.overlapConvexPolygons(boundsTopNew, player)) {
            collisionDetected = true;
        }
        // Check for collision with stalagmite polygons
        else if (Intersector.overlapConvexPolygons(boundsBotNew, player)) {
            collisionDetected = true;

        }
        return collisionDetected;
    }

    public void dispose() {
        stalagmite.dispose();
        stalactite.dispose();
    }
}
