package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class Stalegtite {
    public static final int STALACTITE_WIDTH = 200;
    private Texture stalecmite;
    private Texture stalectite;
    private Vector2 posTop,posBot;
    private Random rand;
    private static final int FLUCTUATION = 50;
    private static final int ROCK_GAP = 130;
    private static final int LOWEST_OPENING = 60;

    public Stalegtite(float x) {
        stalecmite = new Texture("Stalactite.png");
        stalectite = new Texture("Stalacmite.png");
        rand = new Random();

        posTop = new Vector2(x, rand.nextInt(FLUCTUATION) + ROCK_GAP + LOWEST_OPENING);
        posBot = new Vector2(x, posTop.y - ROCK_GAP - stalectite.getHeight());

    }

    public Texture getStalecmite() {
        return stalecmite;
    }

    public Texture getStalectite() {
        return stalectite;
    }

    public Vector2 getPosTop() {
        return posTop;
    }

    public Vector2 getPosBot() {
        return posBot;
    }
    public void reposition(float x) {
        posTop.set(x, rand.nextInt(FLUCTUATION) + ROCK_GAP + LOWEST_OPENING);
        posBot.set(x, posTop.y - ROCK_GAP - stalectite.getHeight());
    }
}
