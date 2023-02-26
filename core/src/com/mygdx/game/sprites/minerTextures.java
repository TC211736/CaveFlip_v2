package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.Texture;

public class minerTextures {
    public Texture minerOriginal;
    public Texture minerBlue;
    public Texture minerGreen;
    public Texture minerKing;

    public minerTextures() {
        minerOriginal = new Texture("Textures/minerAnimationV2.png");
        minerBlue = new Texture("Textures/minerAnimationV2_BLUE.png");
        minerGreen = new Texture("Textures/minerAnimationV2_GREEN.png");
        minerKing = new Texture("Textures/minerAnimationV2_KING.png");
    }

    public Texture getMinerOriginal() {
        return minerOriginal;
    }

    public Texture getMinerBlue() {
        return minerBlue;
    }

    public Texture getMinerGreen() {
        return minerGreen;
    }

    public Texture getMinerKing() {
        return minerKing;
    }
}
