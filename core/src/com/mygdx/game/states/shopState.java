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
import com.mygdx.game.SQL.LoginData;
import com.mygdx.game.SQL.MyJDBC;

public class shopState extends State {

    private Rectangle originalBounds;
    private Rectangle blueBounds;
    private Rectangle greenBounds;
    private Rectangle kingBounds;
    private Rectangle exitBounds;
    private Texture originalCrate;
    private Texture blueCrate;
    private Texture greenCrate;
    private Texture kingCrate;
    private Texture exitButton;
    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    private BitmapFont font1;
    private Texture background;
    private OrthographicCamera camera;
    private Constants constants;
    private int itemID;
    private MyJDBC myJDBC;
    private LoginData loginData;
    String username;

    private Rectangle setOriginalBounds() {
        originalBounds = new Rectangle();
        originalBounds.width = 200;
        originalBounds.height = 210;
        originalBounds.x = (originalBounds.width / 2);
        originalBounds.y = (MyGdxGame.height / 2) - (originalBounds.height / 2) - 50;
        return originalBounds;
    }

    private Rectangle setBlueBounds() {
        blueBounds = new Rectangle();
        blueBounds.width = 200;
        blueBounds.height = 210;
        blueBounds.x = (MyGdxGame.width / 4) + (originalBounds.width / 2);
        blueBounds.y = (MyGdxGame.height / 2) - (blueBounds.height / 2) - 50;
        return blueBounds;
    }

    private Rectangle setGreenBounds() {
        greenBounds = new Rectangle();
        greenBounds.width = 200;
        greenBounds.height = 210;
        greenBounds.x = (MyGdxGame.width / 2) + (originalBounds.width / 2);
        greenBounds.y = (MyGdxGame.height / 2) - (greenBounds.height / 2) - 50;
        return greenBounds;
    }

    private Rectangle setKingBounds() {
        kingBounds = new Rectangle();
        kingBounds.width = 200;
        kingBounds.height = 210;
        kingBounds.x = ((MyGdxGame.width / 4) * 3) + (originalBounds.width / 2);
        kingBounds.y = (MyGdxGame.height / 2) - (kingBounds.height / 2) - 50;
        return kingBounds;
    }

    private Rectangle setExitBounds() {
        exitBounds = new Rectangle();
        exitBounds.width = 256;
        exitBounds.height = 128;
        exitBounds.x = (MyGdxGame.width / 4) * 3;
        exitBounds.y = (MyGdxGame.height / 2) - ((exitBounds.height) * 2) - 50;
        return exitBounds;
    }

    public shopState(GameStateManager gsm) {
        super(gsm);
        background = new Texture("pxArt (1).png");
        originalCrate = new Texture("Textures/minerOriginalCrate.png");
        blueCrate = new Texture("Textures/minerBlueCrate.png");
        greenCrate = new Texture("Textures/minerGreenCrate.png");
        kingCrate = new Texture("Textures/minerKingCrate.png");
        exitButton = new Texture("Textures/exitButton_SMALL.png");

        originalBounds = setOriginalBounds();
        blueBounds = setBlueBounds();
        greenBounds = setGreenBounds();
        kingBounds = setKingBounds();
        exitBounds = setExitBounds();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, MyGdxGame.width, MyGdxGame.height);
        constants = new Constants();
        generator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/Cloude_Regular_Bold_1.02.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 200;
        font1 = generator.generateFont(parameter);
        itemID = 0;
        myJDBC = new MyJDBC();
        loginData = new LoginData();
        username = loginData.getUsername();
    }

    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched()) {
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            if (originalBounds.contains(touchPos.x, touchPos.y)) {
                itemID = 1;
                myJDBC.buyItem(username, itemID);
            } else if (blueBounds.contains(touchPos.x, touchPos.y)) {
                itemID = 2;
                myJDBC.buyItem(username, itemID);
            } else if (greenBounds.contains(touchPos.x, touchPos.y)) {
                itemID = 3;
                myJDBC.buyItem(username, itemID);
            } else if (kingBounds.contains(touchPos.x, touchPos.y)) {
                itemID = 4;
                myJDBC.buyItem(username, itemID);
            } else if (exitBounds.contains(touchPos.x, touchPos.y)) {
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
        sb.draw(originalCrate, originalBounds.x, originalBounds.y);
        sb.draw(blueCrate, blueBounds.x, blueBounds.y);
        sb.draw(greenCrate, greenBounds.x, greenBounds.y);
        sb.draw(kingCrate, kingBounds.x, kingBounds.y);
        sb.draw(exitButton, exitBounds.x, exitBounds.y);
        font1.draw(sb, "Welcome to the shop!", (MyGdxGame.width / 2) - 450, (MyGdxGame.height / 4) * 3);
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
    }
}
