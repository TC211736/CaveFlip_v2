package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.sprites.Stalegtite;
import com.mygdx.game.sprites.miner;

public class playState extends State{

    private static final int OBJECT_SPACING = 125;
    private static final int OBJECT_COUNT = 4;
    private miner miner;
    private Texture background;
    private Array<Stalegtite> stalegtites;

    public playState(GameStateManager gsm) {
        super(gsm);
        miner = new miner(50,100);
        camera.setToOrtho(false, MyGdxGame.width / 2, MyGdxGame.height / 2);
        background = new Texture("caveBackground7.jpg");
        stalegtites = new Array<Stalegtite>();
        for (int i = 0; i < OBJECT_COUNT; i++) {
            stalegtites.add(new Stalegtite(i * (OBJECT_SPACING + Stalegtite.STALACTITE_WIDTH)));
        }
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
        camera.position.x = miner.getPos().x + 80;

        for(Stalegtite stalegtite : stalegtites) {
            if(camera.position.x - (camera.viewportWidth / 2) > (stalegtite.getPosTop().x + stalegtite.getStalectite().getWidth()));
            stalegtite.reposition(stalegtite.getPosTop().x + ((stalegtite.STALACTITE_WIDTH + OBJECT_SPACING) * OBJECT_COUNT));
        }
        camera.update();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        sb.draw(background, camera.position.x - (camera.viewportWidth / 2), 0);
        sb.draw(miner.getTexture(), miner.getPos().x, miner.getPos().y);
        sb.draw(stalegtite.getStalecmite(), stalegtite.getPosTop().x, stalegtite.getPosTop().y);
        sb.draw(stalegtite.getStalectite(), stalegtite.getPosBot().x, stalegtite.getPosBot().y);
        sb.end();
    }

    @Override
    public void dispose() {

    }
}
