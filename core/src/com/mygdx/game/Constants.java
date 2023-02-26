package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.SQL.LoginData;
import com.mygdx.game.SQL.MyJDBC;
import com.mygdx.game.sprites.minerTextures;

public class Constants {
    public static int CoinTally;
    public static int score;

    private Texture minerTexture;
    private minerTextures minerTexturesClass;
    private LoginData loginData;

    public Constants() {
        loginData = new LoginData();
        String username = loginData.getUsername();
        minerTexturesClass = new minerTextures();
        int selectedItem = MyJDBC.getSelectedItem(username);
        if (selectedItem == 1) {
            minerTexture = minerTexturesClass.getMinerOriginal();
        } else if (selectedItem == 2) {
            minerTexture = minerTexturesClass.getMinerBlue();
        } else if (selectedItem == 3) {
            minerTexture = minerTexturesClass.getMinerGreen();
        } else if (selectedItem == 4) {
            minerTexture = minerTexturesClass.getMinerKing();
        } else {
            System.out.println("An error has occurred.");
        }

    }

    public void setCoinTally(int coinTally) {
        CoinTally = coinTally;
    }

    public int getCoinTally() {
        return CoinTally;
    }


    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Texture getMinerTexture() {
        return minerTexture;
    }

    public void setMinerTexture(Texture minerTexture) {
        this.minerTexture = minerTexture;
    }


}
