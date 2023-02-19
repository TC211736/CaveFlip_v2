package com.mygdx.game.Login;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.SQL.MyJDBC;
import com.mygdx.game.states.GameStateManager;
import com.mygdx.game.states.State;
import com.mygdx.game.states.menuState;

public class LoginScreen extends State {
    Stage stage;
    Label label;
    Label usernameLabel;
    Label passwordLabel;
    TextField username;
    TextField password;
    Skin skin = new Skin(Gdx.files.internal("Skins/neutralizerSkins/neutralizer-ui.json"));

    private TextButton login() {
        TextButton login = new TextButton("Login", skin);
        login.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gsm.set(new menuState(gsm));
            }
        });
        return login;
    }

    private Table setRoot() {
        label = new Label("Please set a Username and Password", skin);
        usernameLabel = new Label("Please enter a Username", skin);
        passwordLabel = new Label("Please enter a Password", skin);

        username = new TextField("", skin);
        password = new TextField("", skin);
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
        root.add(login).right();
        return root;
    }

    public LoginScreen(GameStateManager gsm) {
        super(gsm);
        Table root = setRoot();
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        stage.addActor(root);
        String JDBC = MyJDBC.JDBCConnection();

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