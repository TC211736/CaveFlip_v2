package com.mygdx.game.Login;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mygdx.game.SQL.LoginData;
import com.mygdx.game.SQL.MyJDBC;
import com.mygdx.game.SQL.PasswordHash;
import com.mygdx.game.states.GameStateManager;
import com.mygdx.game.states.State;
import com.mygdx.game.states.menuState;

public class LoginScreen extends State {
    Stage stage;
    Label label;
    Label error;
    Label usernameLabel;
    Label passwordLabel;
    TextField username;
    TextField password;
    Skin skin = new Skin(Gdx.files.internal("Skins/neutralizerSkins/neutralizer-ui.json"));

    private TextButton login() {
        TextButton login = new TextButton("Login", skin);
        final String hashedPassword = hashPassword();
        login.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (verifyUser() == true) {
                    LoginData setUser = new LoginData();
                    setUser.setUsername(username.getText());
                    setUser.setPassword(hashedPassword);
                    System.out.println(setUser.getPassword());
                    gsm.set(new menuState(gsm));
                } else {
                    error.setText("Username or Password is incorrect");
                }

            }
        });
        return login;
    }

    private TextButton back() {
        TextButton back = new TextButton("Back", skin);
        back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gsm.set(new StartScreen(gsm));
            }
        });
        return back;
    }

            public String[] getLogin(String usernameInput, String passwordInput) {
                String login[] = null;
                try {
                    login = MyJDBC.login(usernameInput, passwordInput);
                } catch (Exception e) {
                    e.printStackTrace();

                }
                return login;
            }

            public String hashPassword() {
                String hashedPassword = null;

                try {
                    hashedPassword = PasswordHash.hash(password.getText());
                } catch (RuntimeException e) {
                    error.setText("Something went wrong. Please try again.");
                }
                return hashedPassword;
            }


            public boolean verifyUser() {
                String hashedPassword = hashPassword();
                String loginVerify[] = getLogin(username.getText(), hashedPassword);
                boolean userVerify = false;

                try {
                    if (loginVerify[1].equals(hashedPassword)) {
                        userVerify = true;
                        LoginData username2 = new LoginData();
                        username2.setUsername(username.getText());
                        username2.setPassword(hashedPassword);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return userVerify;
            }

            private Table setRoot() {
                label = new Label("Please set a Username and Password", skin);
                error = new Label("", skin);
                usernameLabel = new Label("Please enter a Username", skin);
                passwordLabel = new Label("Please enter a Password", skin);

                username = new TextField("", skin);
                password = new TextField("", skin);
                password.setPasswordMode(true);
                password.setPasswordCharacter('*');
                Table root = new Table(skin);
                root.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
                root.setBackground(skin.getDrawable("custom"));
                root.setFillParent(true);
                //root.debug().defaults().space(6);
                TextButton login = login();
                TextButton back = back();
                root.add(label).height(100).row();
                root.add(usernameLabel).left().row();
                root.add(username).left().row();
                root.add(passwordLabel).left().row();
                root.add(password).left().row();
                root.add(login).right();
                root.add(back).left().row();
                root.add(error);
                return root;
            }

            public LoginScreen(GameStateManager gsm) {
                super(gsm);
                Table root = setRoot();
                stage = new Stage();
                Gdx.input.setInputProcessor(stage);
                stage.addActor(root);
            }

            @Override
            protected void handleInput() {

            }

            @Override
            public void update(float dt) {

            }

            @Override
            public void render(SpriteBatch sb) {

                Gdx.gl.glClearColor(0, 0.25f, 0, 1);
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

                stage.act(Gdx.graphics.getDeltaTime());
                stage.draw();
            }

            @Override
            public void dispose() {
                stage.dispose();
            }
        }