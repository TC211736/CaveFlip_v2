package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.MyGdxGame;

public class miner {
    private static int GRAVITY = -15;
    private static final int MOVEMENT = 100;
    private static int GRAVITYFLIP = 15;
    private Vector3 pos;
    private Vector3 vel;
    private Rectangle bounds;
    private animation minerAnimation;
    private Texture texture;

    public Vector3 getPos() {
        return pos;
    }


    public TextureRegion getTexture() {
        return minerAnimation.getFrame();
    }

    public miner(int x, int y) {
        pos = new Vector3(x, y, 0);
        vel = new Vector3(0, 0, 0);
        texture = new Texture("Textures/minerAnimation.png");
        bounds = new Rectangle(x, y, (texture.getWidth() / 2) / 3, (texture.getHeight()) / 3);
        minerAnimation = new animation(new TextureRegion(texture), 2, 0.5f);
    }

    public void update(float dt) {
        minerAnimation.update(dt);
        if ((pos.y > -1) & (pos.y < (MyGdxGame.height / 2))) {
            vel.add(0, GRAVITY, 0);
            vel.scl(dt);
            pos.add(MOVEMENT * dt, vel.y, 0);

            if (pos.y < 0) {
                pos.y = 0;
                if (GRAVITY > 0) {
                    vel.add(0, GRAVITY, 0);
                    vel.scl(dt);
                    pos.add(MOVEMENT * dt, vel.y, 0);
                }
            } else if (pos.y > (MyGdxGame.height / 2) - (texture.getHeight() / 3)) {
                pos.y = (MyGdxGame.height / 2) - (texture.getHeight() / 3);
                if (GRAVITY < 0) {
                    vel.add(0, GRAVITY, 0);
                    vel.scl(dt);
                    pos.add(MOVEMENT * dt, vel.y, 0);
                }
            }
            vel.scl(1 / dt);
        }
        bounds.setPosition(pos.x, pos.y);
    }

    public void flip() {
        int temp = GRAVITY;
        GRAVITY = GRAVITYFLIP;
        GRAVITYFLIP = temp;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void dispose() {
        texture.dispose();
    }
}
