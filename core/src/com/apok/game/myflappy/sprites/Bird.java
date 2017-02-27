package com.apok.game.myflappy.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Apok on 25.02.2017.
 */

public class Bird {

    private static final int GRAVITY = -15;

    public static int getMOVEMENT() {
        return MOVEMENT;
    }

    public static void setMOVEMENT(int MOVEMENT) {
        Bird.MOVEMENT = MOVEMENT;
    }

    private static int MOVEMENT = 100;
    private static int addVel = 1;
    private Vector3 position;
    private Vector3 velocity;
    private Animation birdAnimation;
    private Texture bird;


    public Rectangle getBounds() {
        return bounds;
    }

    public void dispose()
    {
        bird.dispose();
    }

    private Rectangle bounds;

    public Vector3 getPosition() {
        return position;
    }

    public TextureRegion getTexture()
    {
        return birdAnimation.getFrame();
    }


    public Bird(int x, int y) {
        position = new Vector3(x,y,0);
        velocity = new Vector3(0,0,0);
        bird = new Texture("birds.png");
        birdAnimation = new Animation(new TextureRegion(bird), 4 , 0.3f);
        bounds = new Rectangle(x+3,y+5, bird.getWidth()/4-5, bird.getHeight()-7);
    }

    public void update(float dt)
    {
        birdAnimation.update(dt);
        if(position.y >0)
            velocity.add(0, GRAVITY, 0);
        velocity.scl(dt);
        position.add(MOVEMENT * dt, velocity.y, 0);
        velocity.scl(1/dt);
        if(position.y <0)
            position.y = 0;
        bounds.setPosition(position.x, position.y);
    }

    public void jump()
    {
        velocity.y = 300;
    }
}
