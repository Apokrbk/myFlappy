package com.apok.game.myflappy.States;

import com.apok.game.myflappy.myFlappy;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Apok on 25.02.2017.
 */

public class MenuState extends State {

    private Texture background;
    private Texture playButton;
    private BitmapFont font;
    private String highscores;

    public MenuState(GameStateManager gsm) {
        super(gsm);
        cam.setToOrtho(false, myFlappy.WIDTH/2, myFlappy.HEIGHT/2);
        background = new Texture("background.png");
        playButton = new Texture("playButton.png");
        font = new BitmapFont();
        font.setColor(Color.GREEN);
        font.getData().setScale((float)1.2, (float)1.2);
        highscores = "HIGHSCORE: " + gsm.database.databaseToString();

    }

    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched())
        {
            gsm.set(new PlayState(gsm));
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(background, 0,0);
        sb.draw(playButton, cam.position.x- playButton.getWidth() /2, cam.position.y+15);
        font.draw(sb, highscores, cam.position.x- playButton.getWidth() /2, cam.position.y-15);
        sb.end();
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void dispose() {
        background.dispose();
        playButton.dispose();
        System.out.println("Menu state disposed");
    }
}
