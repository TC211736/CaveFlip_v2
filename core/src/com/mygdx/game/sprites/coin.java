package com.mygdx.game.sprites;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.MyGdxGame;

import java.util.Random;

public class coin {
    private Texture coinTexture;
    private animation coinAnimation;
    private Rectangle bounds;
    private Vector2 position;
    private Random rand;
    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    private BitmapFont font12;
    private Vector2 coinTextPosition;

    private final int FLUCTUATION = 140;
    private final int COINGAP = 50;
    public static final int COINWIDTH = 5;

    public TextureRegion getTexture() {
        return coinAnimation.getFrame();
    }

    public Vector2 getPos() {
        return position;
    }

    public coin(float x) {
        rand = new Random();
        generator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/Cloude_Regular_Bold_1.02.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 40;
        font12 = generator.generateFont(parameter);
        position = new Vector2(x + rand.nextInt(COINGAP), rand.nextInt(FLUCTUATION));
        coinTexture = new Texture("Textures/coinAnimation.png");
        bounds = new Rectangle(position.x, position.y, coinTexture.getWidth() / 7, coinTexture.getHeight());
        coinAnimation = new animation(new TextureRegion(coinTexture), 7, 0.5f);
        coinTextPosition = new Vector2(x, (MyGdxGame.height / 2) - 20);
    }

    public void update(float dt) {
        coinAnimation.update(dt);
    }

    public void reposition(float x) {
        position.set(x + rand.nextInt(COINGAP), rand.nextInt(FLUCTUATION));
        bounds.setPosition(position.x, position.y);
    }

    public boolean collides(Rectangle player) {
        return player.overlaps(bounds);
    }


    public BitmapFont getFont() {
        return font12;
    }

    public Vector2 getCoinTextPosition() {
        return coinTextPosition;
    }

    public void dispose() {
        coinTexture.dispose();
    }
}
