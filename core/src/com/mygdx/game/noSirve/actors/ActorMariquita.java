package com.mygdx.game.noSirve.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ActorMariquita extends Actor {

    private Texture mariquita;

    private boolean alive;

    public ActorMariquita(Texture mariquita){
        this.mariquita = mariquita;
        this.alive = true;
        setSize(90,90);
    }
    @Override
    public void act(float delta) {
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(mariquita,getX(),getY(),100,100);
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

}
