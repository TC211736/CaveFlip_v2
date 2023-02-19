package com.mygdx.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.MyGdxGame;

public class highscore {
    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    private BitmapFont font12;
    private Vector2 position;
    private int highscoreTally;

    public highscore(float x) {
        generator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/Cloude_Regular_Bold_1.02.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 40;
        font12 = generator.generateFont(parameter);
        position = new Vector2(x, (MyGdxGame.height / 2) - 20);
        highscoreTally = 0;
    }

    public void update(float dt) {
        highscoreTally++;
    }

    public BitmapFont getFont() {
        return font12;
    }

    public Vector2 getPosition() {
        return position;
    }

    public int getTally() {
        return highscoreTally;
    }

    public void dispose() {
        font12.dispose();
    }
}
