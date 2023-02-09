package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.sprites.stalactite;
import com.mygdx.game.sprites.miner;

public class playState extends State {

    private static final int OBJECT_SPACING = 125;
    private static final int OBJECT_COUNT = 6;
    private miner miner;
    private Texture background;
    private Array<stalactite> stalegtites;
    private Label highscore;
    private Skin skin;
    private Stage stage;
    private Table root;
    public playState(GameStateManager gsm) {
        super(gsm);
        skin = new Skin(Gdx.files.internal("Skins/neutralizerSkins/neutralizer-ui.json"));
        miner = new miner(50, 100);
        camera.setToOrtho(false, MyGdxGame.width / 2, MyGdxGame.height / 2);
        background = new Texture("Textures/caveBackground7.jpg");
        highscore = new Label("", skin);
        root = new Table(skin);
        root.add(highscore);
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        stage.addActor(root);

        stalegtites = new Array<>();
        for (int i = 1; i < OBJECT_COUNT; i++) {
            stalegtites.add(new stalactite(i * (OBJECT_SPACING + stalactite.STALACTITE_WIDTH)));
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
        for (int i = 0; i < stalegtites.size; i++) {
            stalactite stalactite = stalegtites.get(i);

            if (camera.position.x - (camera.viewportWidth / 2) > (stalactite.getPosTop().x + stalactite.getStalectite().getWidth())) {
                stalactite.reposition(stalactite.getPosTop().x + ((stalactite.STALACTITE_WIDTH + OBJECT_SPACING) * OBJECT_COUNT));
            }
            if (stalactite.collides(miner.getBounds())) {
                gsm.set(new playState(gsm));
            }
        }
        camera.update();
        int score = 0;
        for (int i = 0; i < dt; i++) {
            highscore.setText(score);
            score ++;
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        sb.draw(background, camera.position.x - (camera.viewportWidth / 2), 0);
        sb.draw(miner.getTexture(), miner.getPos().x, miner.getPos().y);
        for (stalactite stalactite : stalegtites) {
            sb.draw(stalactite.getStalecmite(), stalactite.getPosTop().x, stalactite.getPosTop().y);
            sb.draw(stalactite.getStalectite(), stalactite.getPosBot().x, stalactite.getPosBot().y);
        }
        sb.end();

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void dispose() {
        background.dispose();
        miner.dispose();
        for (stalactite stalactite : stalegtites) {
            stalactite.dispose();
        }
    }
}
