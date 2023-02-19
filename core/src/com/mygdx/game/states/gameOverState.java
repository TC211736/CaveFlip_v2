package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.MyGdxGame;

public class gameOverState extends State {
    private Rectangle restartBounds;
    private Rectangle exitBounds;
    private Texture restartButton;
    private Texture exitButton;
    Texture background;
    OrthographicCamera camera;

    private Rectangle setExitBounds() {
        exitBounds = new Rectangle();
        exitBounds.width = 512;
        exitBounds.height = 256;
        exitBounds.x = (MyGdxGame.width / 4);
        exitBounds.y = (MyGdxGame.height / 4) - (exitBounds.width / 2);
        return exitBounds;
    }

    private Rectangle setRestartBounds() {
        restartBounds = new Rectangle();
        restartBounds.width = 512;
        restartBounds.height = 256;
        restartBounds.x = (MyGdxGame.width / 4) * 3;
        restartBounds.y = (MyGdxGame.height / 2) - (restartBounds.height / 2);
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
        sb.begin();
        sb.draw(background, 0, 0, MyGdxGame.width, MyGdxGame.height);
        sb.draw(restartButton, restartBounds.x, restartBounds.y, restartButton.getWidth() / 2, restartButton.getHeight() / 2);
        sb.draw(exitButton, exitBounds.x, exitBounds.y, exitButton.getWidth() / 2, exitButton.getHeight() / 2 );
        sb.end();
    }

    @Override
    public void dispose() {
        exitButton.dispose();
        restartButton.dispose();
        background.dispose();
    }
}
