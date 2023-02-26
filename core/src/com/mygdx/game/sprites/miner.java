package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Constants;
import com.mygdx.game.MyGdxGame;

public class miner {
    private static int GRAVITY = -15;
    private static final int MOVEMENT = 100;
    private static int GRAVITYFLIP = 15;
    private Vector3 position;
    private Vector3 velocity;
    private Polygon boundsNew;
    private animation minerAnimation;
    private Texture texture;
    private Constants constants;


    public miner(int x, int y) {
        position = new Vector3(x, y, 0);
        velocity = new Vector3(0, 0, 0);
        constants = new Constants();
        texture = constants.getMinerTexture();
        boundsNew = new Polygon(vertices());
        boundsNew.setOrigin(getPolygonCenter().x, getPolygonCenter().y);
        minerAnimation = new animation(new TextureRegion(texture), 2, 0.5f);
    }

    public void update(float dt) {
        minerAnimation.update(dt);
        if ((position.y > -1) & (position.y < (MyGdxGame.height / 2))) {
            velocity.add(0, GRAVITY, 0);
            velocity.scl(dt);
            position.add(MOVEMENT * dt, velocity.y, 0);
            boundsNew.setPosition(position.x, position.y);

            if (position.y < 0) {
                position.y = 0;
                if (GRAVITY > 0) {
                    velocity.add(0, GRAVITY, 0);
                    velocity.scl(dt);
                    position.add(MOVEMENT * dt, velocity.y, 0);
                    boundsNew.setPosition(position.x, position.y);
                }
            } else if (position.y > (MyGdxGame.height / 2) - (texture.getHeight() / 3)) {
                position.y = (MyGdxGame.height / 2) - (texture.getHeight() / 3);
                if (GRAVITY < 0) {
                    velocity.add(0, GRAVITY, 0);
                    velocity.scl(dt);
                    position.add(MOVEMENT * dt, velocity.y, 0);
                    boundsNew.setPosition(position.x, position.y);
                }
            }
            velocity.scl(1 / dt);
        }
        boundsNew.setPosition(position.x, position.y);
    }

    public Vector3 getPosition() {
        return position;
    }

    public TextureRegion getTexture() {
        return minerAnimation.getFrame();
    }

    public float[] vertices() {
        float scale = 0.4f;
        float[] vertices = new float[]{
                40, 0,
                40, 30,
                20, 30,
                20, 70,
                10, 70,
                10, 130,
                0, 130,
                0, 140,
                10, 140,
                10, 160,
                20, 160,
                20, 170,
                30, 170,
                30, 180,
                70, 180,
                70, 170,
                90, 170,
                90, 150,
                100, 150,
                100, 140,
                110, 140,
                110, 130,
                100, 130,
                100, 20,
                110, 20,
                110, 30,
                120, 30,
                120, 20,
                110, 20,
                110, 10,
                70, 10,
                70, 0

        };

        for (int i = 0; i < vertices.length; i += 2) {
            vertices[i] *= scale; // Scale x-coordinate
            vertices[i + 1] *= scale; // Scale y-coordinate
        }

        return vertices;
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

    public void flip() {
        int temp = GRAVITY;
        GRAVITY = GRAVITYFLIP;
        GRAVITYFLIP = temp;
    }

    public Polygon getBounds() {
        return boundsNew;
    }

    public void dispose() {
        texture.dispose();
    }
}
