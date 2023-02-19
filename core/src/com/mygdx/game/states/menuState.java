package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.MyGdxGame;

public class menuState extends State {
    Texture playButton;
    Texture shopButton;
    Texture exitButton;
    Texture background;
    Rectangle playButtonR;
    Rectangle shopButtonR;
    Rectangle exitButtonR;
    OrthographicCamera camera;

    private Rectangle setPlayButtonR() {
        playButtonR = new Rectangle();
        playButtonR.width = 512;
        playButtonR.height = 256;
        playButtonR.x = 0;
        playButtonR.y = (MyGdxGame.height / 2) - (playButton.getHeight());
        return playButtonR;
    }

    private Rectangle setShopButtonR() {
        shopButtonR = new Rectangle();
        shopButtonR.width = 512;
        shopButtonR.height = 256;
        shopButtonR.x = (MyGdxGame.width / 2) - (shopButton.getWidth() / 2);
        shopButtonR.y = (MyGdxGame.height / 2) - (shopButton.getHeight());
        return shopButtonR;
    }

    private Rectangle setExitButtonR() {
        exitButtonR = new Rectangle();
        exitButtonR.width = 512;
        exitButtonR.height = 256;
        exitButtonR.x = (MyGdxGame.width) - (exitButton.getWidth());
        exitButtonR.y = (MyGdxGame.height / 2) - (exitButton.getHeight());
        return exitButtonR;
    }

    public menuState(GameStateManager gsm) {
        super(gsm);
        background = new Texture("pxArt (1).png");
        playButton = new Texture("PlayButton2.png");
        shopButton = new Texture("ShopButton2.png");
        exitButton = new Texture("ExitButton2.png");
        playButtonR = setPlayButtonR();
        shopButtonR = setShopButtonR();
        exitButtonR = setExitButtonR();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, MyGdxGame.width, MyGdxGame.height);
    }


    @Override
    public void handleInput() {
        if (Gdx.input.justTouched()) {
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            if (playButtonR.contains(touchPos.x, touchPos.y)) {
                gsm.set(new gameState(gsm));
            } else if (shopButtonR.contains(touchPos.x, touchPos.y)) {
                gsm.set(new shopState(gsm));
            } else if (exitButtonR.contains(touchPos.x, touchPos.y)) { //CREATE A STAGE AND ADD ALL VARIABLES TO STAGE - more efficient (you got this bb cakes)
                System.exit(0);
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
        sb.draw(playButton, playButtonR.x, playButtonR.y);
        sb.draw(shopButton, shopButtonR.x, shopButtonR.y);
        sb.draw(exitButton, exitButtonR.x, exitButtonR.y);
        sb.end();

    }

    @Override
    public void dispose() {
        background.dispose();
        playButton.dispose();
        shopButton.dispose();
        exitButton.dispose();
    }
}
