package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.MyGdxGame;

public class miner {
    public Vector3 getPos() {
        return pos;
    }


    public Texture getTexture() {
        return miner;
    }

    private static  int GRAVITY = -15;
    private static final int MOVEMENT = 100;
    private static  int GRAVITYFLIP = 15;
    private Vector3 pos;
    private Vector3 vel;
    private Texture miner;

    public miner(int x, int y) {
        pos = new Vector3(x, y,0);
        vel = new Vector3(0,0,0);
        miner = new Texture("minerArtv2.png");
    }

    public void update(float dt) {
        if ((pos.y > -1) & (pos.y < (MyGdxGame.height / 2)))  {
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
            } else if (pos.y > (MyGdxGame.height / 2) - miner.getHeight()) {
                pos.y = (MyGdxGame.height / 2) - miner.getHeight();
                if (GRAVITY < 0) {
                    vel.add(0, GRAVITY, 0);
                    vel.scl(dt);
                    pos.add(MOVEMENT * dt, vel.y, 0);
                }
            }
            vel.scl(1 / dt);
        }
    }

    public void flip() {
        int temp = GRAVITY;
        GRAVITY = GRAVITYFLIP;
        GRAVITYFLIP = temp;
    }
}
