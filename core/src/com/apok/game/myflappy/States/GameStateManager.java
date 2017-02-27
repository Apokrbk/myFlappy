package com.apok.game.myflappy.States;

import com.apok.game.myflappy.MainDatabase;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Stack;

/**
 * Created by Apok on 25.02.2017.
 */

public class GameStateManager {

    private Stack<State> states;
    public MainDatabase database;

    public GameStateManager(MainDatabase db)
    {
        database = db;
        states = new Stack<State>();
    }

    public void push(State state)
    {
        states.push(state);
    }

    public void pop()
    {
        states.pop().dispose();
    }

    public void set(State state)
    {
        states.pop().dispose();
        states.push(state);
    }

    public void update(float dt)
    {
        states.peek().update(dt);
    }

    public void render(SpriteBatch sb)
    {
        states.peek().render(sb);
    }
}
