package com.mygdx.game.SQL;

import com.badlogic.gdx.graphics.Texture;

public class LoginData {
    private static String username;
    private static String password;

    public static String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public static String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
