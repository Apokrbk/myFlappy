package com.apok.game.myflappy.States;

import com.apok.game.myflappy.myFlappy;
import com.apok.game.myflappy.sprites.Bird;
import com.apok.game.myflappy.sprites.Tube;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Apok on 25.02.2017.
 */

public class PlayState extends State {

    private static final int TUBE_SPACING = 120;
    private static final int TUBE_COUNT = 4;
    private static final int GROUND_HEIGHT = 50;

    private Bird bird;
    private Texture background;
    private Texture ground;
    private Vector2 backgroundPositionOne, backgroundPositionTwo;
    private int score=0;
    private BitmapFont font;

    private Array<Tube> tubes;

    protected PlayState(GameStateManager gsm) {
        super(gsm);
        bird = new Bird(50,300);
        cam.setToOrtho(false, myFlappy.WIDTH/2, myFlappy.HEIGHT/2);
        background = new Texture("background2.png");
        ground = new Texture("ground.png");
        font = new BitmapFont();
        font.setColor(Color.GREEN);
        font.getData().setScale(2,2);
        backgroundPositionOne = new Vector2(cam.position.x - cam.viewportWidth/2 , 0);
        backgroundPositionTwo = new Vector2(cam.position.x - cam.viewportWidth/2 + background.getWidth(), 0);
        tubes = new Array<Tube>();
        for(int i=1; i<TUBE_COUNT+1; i++)
        {
            tubes.add(new Tube(i * (TUBE_SPACING + Tube.TUBE_WIDTH)));
        }
    }

    @Override
    public void dispose() {
        background.dispose();
        ground.dispose();
        bird.dispose();
        for(Tube tube : tubes)
            tube.dispose();
        System.out.println("Play state disposed");
    }

    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched())
            bird.jump();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(background, backgroundPositionOne.x, backgroundPositionOne.y);
        sb.draw(background, backgroundPositionTwo.x, backgroundPositionTwo.y);
        sb.draw(bird.getTexture(), bird.getPosition().x, bird.getPosition().y );
        for(Tube tube : tubes)
        {
            sb.draw(tube.getTopTube(), tube.getPosTopTube().x, tube.getPosTopTube().y);
            sb.draw(tube.getBottomTube(), tube.getPosBotTube().x, tube.getPosBotTube().y);
        }
        sb.draw(ground, backgroundPositionOne.x, backgroundPositionOne.y);
        sb.draw(ground, backgroundPositionTwo.x, backgroundPositionTwo.y);
        font.draw(sb, "SCORE: " + score, cam.position.x - (cam.viewportWidth/2)+5, cam.position.y + (cam.viewportHeight/2)-5);
        sb.end();
    }

    @Override
    public void update(float dt) {
        bird.setMOVEMENT(100+score/2);
        handleInput();
        bird.update(dt);
        updateBackground();
        cam.position.x = bird.getPosition().x + 80;
        updateTubes();
        if(bird.getPosition().y <= GROUND_HEIGHT)
        {
            if(score > gsm.database.getFirst())
                gsm.database.addToDatabase(score);
            gsm.set(new MenuState(gsm));
        }

        cam.update();
    }

    private void updateTubes() {
        for(int i=0; i<tubes.size; i++)
        {
            Tube tube = tubes.get(i);
            if(cam.position.x - (cam.viewportWidth/2) > tube.getPosTopTube().x + tube.getTopTube().getWidth())
                tube.reposition(tube.getPosTopTube().x + (Tube.TUBE_WIDTH+ TUBE_SPACING)*TUBE_COUNT);
            if(tube.collides(bird.getBounds()))
            {
                if(score > gsm.database.getFirst())
                    gsm.database.addToDatabase(score);
                gsm.set(new MenuState(gsm));
            }

            if(bird.getPosition().x > tube.getPosBotTube().x && bird.getPosition().x < tube.getPosBotTube().x + tube.TUBE_WIDTH && !tube.isPassed())
            {
                score++;
                tube.setPassed(true);
            }
        }
    }


    public void updateBackground()
    {
        if(cam.position.x - cam.viewportWidth/2 > backgroundPositionOne.x+background.getWidth())
            backgroundPositionOne.add(background.getWidth()*2, 0);
        if(cam.position.x - cam.viewportWidth/2 > backgroundPositionTwo.x+background.getWidth())
            backgroundPositionTwo.add(background.getWidth()*2, 0);
    }


}
