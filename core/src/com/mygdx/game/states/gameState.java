package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Constants;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.SQL.LoginData;
import com.mygdx.game.SQL.MyJDBC;
import com.mygdx.game.sprites.coin;
import com.mygdx.game.sprites.highscore;
import com.mygdx.game.sprites.miner;
import com.mygdx.game.sprites.stalactite;

public class gameState extends State {
    private static final int OBJECT_SPACING = 125;
    private static final int OBJECT_COUNT = 8;
    private static final int COINCOUNT = 20;
    private LoginData login;
    private MyJDBC myJDBC;
    private com.mygdx.game.sprites.miner miner;
    private Texture background;
    private ShapeRenderer shapeRenderer;
    private com.mygdx.game.sprites.highscore highscore;
    private Array<stalactite> stalegtites;
    private Array<coin> coins;
    private coin coinTally;
    private int coinTallyNum;


    public gameState(GameStateManager gsm) {
        super(gsm);

        login = new LoginData();
        myJDBC = new MyJDBC();
        miner = new miner(50, 100);
        highscore = new highscore((MyGdxGame.width / 2) - 180);
        coinTally = new coin((MyGdxGame.width / 2) - 180);
        coinTallyNum = 0;
        camera.setToOrtho(false, MyGdxGame.width / 2, MyGdxGame.height / 2);
        background = new Texture("Textures/caveBackground7.jpg");
        shapeRenderer = new ShapeRenderer();
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
        camera.position.x = (miner.getPosition().x + 80);
        highscore.getPosition().x = camera.position.x + 220;
        coinTally.getCoinTextPosition().x = camera.position.x + 180;
        highscore.update(dt);

        for (int i = 0; i < stalegtites.size; i++) {
            stalactite stalactite = stalegtites.get(i);

            for (int k = 0; k < coins.size; k++) {
                coin coin = coins.get(k);

                if (camera.position.x - (camera.viewportWidth / 2) > (coin.getPos().x)) {
                    coin.reposition(coin.getPos().x + ((coin.COINWIDTH + OBJECT_SPACING) * COINCOUNT));
                }

                if (coin.collides(miner.getBounds())) {
                    coin.reposition(coin.getPos().x + ((coin.COINWIDTH + OBJECT_SPACING) * COINCOUNT));
                    coinTallyNum++;
                }

                if (coin.collides(stalactite.getBoundsTopNew()) || coin.collides(stalactite.getBoundsBotNew())) {
                    coin.reposition(coin.getPos().x + ((coin.COINWIDTH + OBJECT_SPACING) * COINCOUNT));
                }
            }

            if (camera.position.x - (camera.viewportWidth / 2) > (stalactite.getPosTop().x + stalactite.getStalactite().getWidth())) {
                stalactite.reposition(stalactite.getPosTop().x + ((stalactite.STALACTITE_WIDTH + OBJECT_SPACING) * OBJECT_COUNT));
            }

            if (stalactite.collides(miner.getBounds())) {
                Constants constantChange = new Constants();
                constantChange.setCoinTally(coinTallyNum);
                constantChange.setScore(highscore.getTally());
                String username = login.getUsername();
                myJDBC.updateCoins(username, coinTallyNum);
                myJDBC.updateHighScore(username, highscore.getTally());
                gsm.set(new gameOverState(gsm));
            }

        }


        camera.update();

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        sb.begin();
        sb.draw(background, camera.position.x - (camera.viewportWidth / 2), 0);
        sb.draw(miner.getTexture(), miner.getPosition().x, miner.getPosition().y, Gdx.graphics.getWidth() / 25, Gdx.graphics.getHeight() / 10);
        shapeRenderer.polygon(miner.getBounds().getTransformedVertices());
        for (stalactite stalactite : stalegtites) {
            sb.draw(stalactite.getStalagmite(), stalactite.getPosTop().x, stalactite.getPosTop().y);
            sb.draw(stalactite.getStalactite(), stalactite.getPosBot().x, stalactite.getPosBot().y);
            shapeRenderer.polygon(stalactite.getBoundsBotNew().getTransformedVertices());
            shapeRenderer.polygon(stalactite.getBoundsTopNew().getTransformedVertices());
        }

        for (coin coin : coins) {
            sb.draw(coin.getTexture(), coin.getPos().x, coin.getPos().y);
            shapeRenderer.polygon(coin.getBoundsNew().getTransformedVertices());
        }

        highscore.getFont().draw(sb, Integer.toString(highscore.getTally()), highscore.getPosition().x, highscore.getPosition().y);
        coinTally.getFont().draw(sb, Integer.toString(coinTallyNum), coinTally.getCoinTextPosition().x, coinTally.getCoinTextPosition().y);
        sb.end();
        shapeRenderer.end();

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
