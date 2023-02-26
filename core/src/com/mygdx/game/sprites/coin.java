package com.mygdx.game.sprites;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.MyGdxGame;

import java.util.Random;

public class coin {
    private Texture coinTexture;
    private Polygon boundsNew;
    private Vector2 position;
    private Random rand;
    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    private BitmapFont font12;
    private Vector2 coinTextPosition;

    private final int FLUCTUATION = 140;
    private final int COINGAP = 100;
    public static final int COINWIDTH = 5;


    public Vector2 getPos() {
        return position;
    }

    public coin(float x) {
        rand = new Random();
        generator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/Cloude_Regular_Bold_1.02.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 40;
        font12 = generator.generateFont(parameter);
        position = new Vector2(x + rand.nextInt(COINGAP), rand.nextInt(FLUCTUATION));
        coinTexture = new Texture("Textures/coinTexture.png");
        boundsNew = new Polygon(vertices());
        boundsNew.setOrigin(getPolygonCenter().x, getPolygonCenter().y);
        boundsNew.setPosition(position.x, position.y);
        coinTextPosition = new Vector2(x, (MyGdxGame.height / 2) - 20);
    }

    public void reposition(float x) {
        position.set(x + rand.nextInt(COINGAP), rand.nextInt(FLUCTUATION));
        boundsNew.setPosition(position.x, position.y);
    }

    private Vector2 getPolygonCenter() {
        float[] vertices = boundsNew.getTransformedVertices();
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

        if (Intersector.overlapConvexPolygons(boundsNew, player)) { // Check for collision with coin polygons
            collisionDetected = true;
        }

        return collisionDetected;
    }

    private float[] vertices() {
        float[] vertices = new float[]{
                5, 0,
                5, 5,
                0, 5,
                0, 20,
                5, 20,
                5, 25,
                20, 25,
                20, 20,
                25, 20,
                25, 5,
                20, 5,
                20, 0

        };
        return vertices;
    }

    public Polygon getBoundsNew() {
        return boundsNew;
    }

    public BitmapFont getFont() {
        return font12;
    }

    public Vector2 getCoinTextPosition() {
        return coinTextPosition;
    }

    public Texture getTexture() {
        return coinTexture;
    }

    public void dispose() {
        coinTexture.dispose();
    }
}
