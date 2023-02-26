package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Constants;
import com.mygdx.game.MyGdxGame;

public class gameOverState extends State {
    private Rectangle restartBounds;
    private Rectangle exitBounds;
    private Texture restartButton;
    private Texture exitButton;
    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter2;
    private BitmapFont font1;
    private BitmapFont font2;
    private Texture background;
    private OrthographicCamera camera;
    private Constants constants;

    private Rectangle setExitBounds() {
        exitBounds = new Rectangle();
        exitBounds.width = 512;
        exitBounds.height = 256;
        exitBounds.x = (MyGdxGame.width / 2) + 50;
        exitBounds.y = (MyGdxGame.height / 2) - (exitBounds.height / 2) - 50;
        return exitBounds;
    }

    private Rectangle setRestartBounds() {
        restartBounds = new Rectangle();
        restartBounds.width = 512;
        restartBounds.height = 256;
        restartBounds.x = 50;
        restartBounds.y = (MyGdxGame.height / 2) - (restartBounds.height / 2) - 50;
        return restartBounds;
    }

    public gameOverState(GameStateManager gsm) {
        super(gsm);
        background = new Texture("pxArt (1).png");
        restartButton = new Texture("Textures/restartButton.png");
        exitButton = new Texture("exitButton.png");
        restartBounds = setRestartBounds();
        exitBounds = setExitBounds();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, MyGdxGame.width, MyGdxGame.height);
        constants = new Constants();
        generator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/Cloude_Regular_Bold_1.02.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter2 = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 200;
        parameter2.size = 50;
        font1 = generator.generateFont(parameter);
        font2 = generator.generateFont(parameter2);
    }

    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched()) {
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            if (restartBounds.contains(touchPos.x, touchPos.y)) {
                gsm.set(new gameState(gsm));
            } else if (exitBounds.contains(touchPos.x, touchPos.y)) { //CREATE A STAGE AND ADD ALL VARIABLES TO STAGE - more efficient (you got this bb cakes)
                gsm.set(new menuState(gsm));
            }
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        sb.draw(background, 0, 0, MyGdxGame.width, MyGdxGame.height);
        sb.draw(restartButton, restartBounds.x, restartBounds.y);
        sb.draw(exitButton, exitBounds.x, exitBounds.y);
        font1.draw(sb, "GAMEOVER!", (MyGdxGame.width / 2) - 250, (MyGdxGame.height / 4) * 3);
        font2.draw(sb, "Coins Collected: " + constants.getCoinTally(), (MyGdxGame.width / 3) - 50, ((MyGdxGame.height / 4) * 3) - 60);
        font2.draw(sb, "Score: " + constants.getScore(), ((MyGdxGame.width / 3) * 2) - 50, ((MyGdxGame.height / 4) * 3) - 60);
        sb.end();
    }

    @Override
    public void dispose() {
        exitButton.dispose();
        restartButton.dispose();
        background.dispose();
    }
}
