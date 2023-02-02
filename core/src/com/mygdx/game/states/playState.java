package com.mygdx.game.states;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class playState extends State{

    private Texture miner;

    public playState(GameStateManager gsm) {
        super(gsm);
        miner = new Texture("minerArt.png");
    }

    @Override
    protected void handleInput() {

    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(miner, 50, 50);
        sb.end();
    }

    @Override
    public void dispose() {

    }
}
