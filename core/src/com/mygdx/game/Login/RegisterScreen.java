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


public class RegisterScreen extends State {
    Stage stage;
    Label label;
    Label error;
    Label usernameLabel;
    Label passwordLabel;
    Label password2Label;
    TextField username;
    TextField password;
    TextField password2;
    Skin skin = new Skin(Gdx.files.internal("Skins/neutralizerSkins/neutralizer-ui.json"));

    private TextButton login() {
        TextButton login = new TextButton("Login", skin);
        login.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                final boolean usernameExists = usernameExists(username.getText());
                final boolean passwordEqual = passwordEqual();
                String hashPassword = hashedPassword(password.getText());
                if ((!usernameExists) && (passwordEqual)) {
                    MyJDBC.register(username.getText(), hashPassword);
                    LoginData setUser = new LoginData();
                    setUser.setUsername(username.getText());
                    setUser.setPassword(hashPassword);
                    System.out.println(setUser.getPassword());
                    gsm.set(new menuState(gsm));
                }
            }
        });
        return login;
    }

    public boolean usernameExists(String usernameInput) {
        boolean usernameExists = false;
        try {
            usernameExists = MyJDBC.verifyUsername(usernameInput);
            if (usernameExists == true) {
                error.setText("Sorry this username is taken already");
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return usernameExists;
    }

    public String hashedPassword(String password) {
        String hashedPassword = null;
        try {
            hashedPassword = PasswordHash.hash(password);
        } catch (RuntimeException e) {
            error.setText("Something went wrong. Please try again.");
        }
        return hashedPassword;
    }

    public boolean passwordEqual() {
        boolean passwordEqual = false;
        String hashPassword = hashedPassword(password.getText());
        String hashPassword2 = hashedPassword(password2.getText());
        if (hashPassword.equals(hashPassword2)) {
            passwordEqual = true;
        } else {
            error.setText("Passwords do not match");
        }
        return passwordEqual;
    }

    private Table setRoot() {
        label = new Label("Please set a Username and Password", skin);
        error = new Label("", skin);
        usernameLabel = new Label("Please enter a Username", skin);
        passwordLabel = new Label("Please enter a Password", skin);
        password2Label = new Label("Please confirm your Password", skin);

        username = new TextField("", skin);
        password = new TextField("", skin);
        password.setPasswordMode(true);
        password.setPasswordCharacter('*');
        password2 = new TextField("", skin);
        password2.setPasswordMode(true);
        password2.setPasswordCharacter('*');
        Table root = new Table(skin);
        root.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        root.setBackground(skin.getDrawable("custom"));
        root.setFillParent(true);
        //root.debug().defaults().space(6);
        TextButton login = login();
        root.add(label).height(100).row();
        root.add(usernameLabel).left().row();
        root.add(username).left().row();
        root.add(passwordLabel).left().row();
        root.add(password).left().row();
        root.add(password2Label).left().row();
        root.add(password2).left().row();
        root.add(login).right().row();
        root.add(error);
        return root;
    }

    public RegisterScreen(GameStateManager gsm) {
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
