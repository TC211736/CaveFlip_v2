package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.sprites.miner;

public class playState extends State{

    private miner miner;
    private Texture background;

    public playState(GameStateManager gsm) {
        super(gsm);
        miner = new miner(50,100);
        camera.setToOrtho(false, MyGdxGame.width / 2, MyGdxGame.height / 2);
        background = new Texture("caveBackground7.jpg");
    }

    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched()) {
            miner.flip();
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        miner.update(dt);
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        sb.draw(background, camera.position.x - (camera.viewportWidth / 2), 0);
        sb.draw(miner.getTexture(), miner.getPos().x, miner.getPos().y);
        sb.end();
    }

    @Override
    public void dispose() {

    }
}
