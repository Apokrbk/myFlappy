package com.apok.game.myflappy;

import com.apok.game.myflappy.States.GameStateManager;
import com.apok.game.myflappy.States.MenuState;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class myFlappy extends ApplicationAdapter {

	public static final int WIDTH = 480;
	public static final int HEIGHT = 800;

	public static final String TITLE = "myFlappy";

	private GameStateManager gsm;
	private SpriteBatch batch;
	private MainDatabase database;

    public myFlappy(MainDatabase db)
    {
        super();
        database = db;
    }

	@Override
	public void create () {
		batch = new SpriteBatch();
		gsm = new GameStateManager(database);
		Gdx.gl.glClearColor(1, 0, 0, 1);
		gsm.push(new MenuState(gsm));
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(batch);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
