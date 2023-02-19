package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.sprites.coin;
import com.mygdx.game.sprites.highscore;
import com.mygdx.game.sprites.miner;
import com.mygdx.game.sprites.stalactite;

public class gameState extends State {
    private static final int OBJECT_SPACING = 125;
    private static final int OBJECT_COUNT = 8;
    private static final int COINCOUNT = 20;
    private com.mygdx.game.sprites.miner miner;
    private Texture background;
    private com.mygdx.game.sprites.highscore highscore;
    private Array<stalactite> stalegtites;
    private Array<coin> coins;
    private coin coinTally;
    private int coinTallyNum;


    public gameState(GameStateManager gsm) {
        super(gsm);
        miner = new miner(50, 100);
        highscore = new highscore((MyGdxGame.width / 2) - 180);
        coinTally = new coin((MyGdxGame.width / 2) - 180);
        coinTallyNum = 0;
        camera.setToOrtho(false, MyGdxGame.width / 2, MyGdxGame.height / 2);
        background = new Texture("Textures/caveBackground7.jpg");
        stalegtites = new Array<>();
        for (int i = 1; i < OBJECT_COUNT; i++) {
            stalegtites.add(new stalactite(i * (OBJECT_SPACING + stalactite.STALACTITE_WIDTH)));
        }

        coins = new Array<>();
        for (int i = 1; i < COINCOUNT; i++) {
            coins.add(new coin(i * (OBJECT_SPACING + coin.COINWIDTH)));
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
        camera.position.x = (miner.getPos().x + 80);
        highscore.getPosition().x = camera.position.x + 220;
        coinTally.getCoinTextPosition().x = camera.position.x + 180;
        highscore.update(dt);

        for (int i = 0; i < stalegtites.size; i++) {
            stalactite stalactite = stalegtites.get(i);

            if (camera.position.x - (camera.viewportWidth / 2) > (stalactite.getPosTop().x + stalactite.getStalectite().getWidth())) {
                stalactite.reposition(stalactite.getPosTop().x + ((stalactite.STALACTITE_WIDTH + OBJECT_SPACING) * OBJECT_COUNT));

            }
            if (stalactite.collides(miner.getBounds())) {
                gsm.set(new gameOverState(gsm));
            }
        }

        for (int i = 0; i < coins.size; i++) {
            coin coin = coins.get(i);
            coin.update(dt);
            if (camera.position.x - (camera.viewportWidth / 2) > (coin.getPos().x + coin.getTexture().getRegionWidth() / 7)) {
                coin.reposition(coin.getPos().x + ((coin.COINWIDTH + OBJECT_SPACING) * COINCOUNT));
            }

            if (coin.collides(miner.getBounds())) {
                coin.reposition(coin.getPos().x + ((coin.COINWIDTH + OBJECT_SPACING) * COINCOUNT));
                coinTallyNum++;
            }

        }
        camera.update();


    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        sb.draw(background, camera.position.x - (camera.viewportWidth / 2), 0);
        sb.draw(miner.getTexture(), miner.getPos().x, miner.getPos().y, Gdx.graphics.getWidth() / 25, Gdx.graphics.getHeight() / 10);
        for (stalactite stalactite : stalegtites) {
            sb.draw(stalactite.getStalecmite(), stalactite.getPosTop().x, stalactite.getPosTop().y);
            sb.draw(stalactite.getStalectite(), stalactite.getPosBot().x, stalactite.getPosBot().y);
        }
        for (coin coin : coins) {
            sb.draw(coin.getTexture(), coin.getPos().x, coin.getPos().y);
        }
        highscore.getFont().draw(sb, Integer.toString(highscore.getTally()), highscore.getPosition().x, highscore.getPosition().y);
        coinTally.getFont().draw(sb, Integer.toString(coinTallyNum), coinTally.getCoinTextPosition().x, coinTally.getCoinTextPosition().y);
        sb.end();

    }

    @Override
    public void dispose() {
        background.dispose();
        miner.dispose();
        for (stalactite stalactite : stalegtites) {
            stalactite.dispose();
        }

        for (coin coin : coins) {
            coin.dispose();
        }
    }
}
