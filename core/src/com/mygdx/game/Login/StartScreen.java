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
import com.mygdx.game.states.GameStateManager;
import com.mygdx.game.states.State;

public class StartScreen extends State {
    Stage stage;
    private OrthographicCamera camera;
    private Viewport viewport;
    Label label;
    Skin skin = new Skin(Gdx.files.internal("Skins/neutralizerSkins/neutralizer-ui.json"));

    public StartScreen(GameStateManager gsm) {
        super(gsm);
        Table root = new Table(skin);
        root.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        root.setBackground(skin.getDrawable("custom"));
        //root.debug().defaults().space(6);
        TextButton login = login();
        TextButton register = register();
        root.add(login, register, label);
        root.add(label).colspan(2);
        camera = new OrthographicCamera(600, 900);
        viewport = new FillViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);
        stage.addActor(root);


    }

    private TextButton login() {
        TextButton login = new TextButton("Login", skin);
        login.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gsm.set(new LoginScreen(gsm));
            }
        });
        return login;
    }

    private TextButton register() {
        TextButton register = new TextButton("Register", skin);
        register.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gsm.set(new RegisterScreen(gsm));
            }
        });
        return register;
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