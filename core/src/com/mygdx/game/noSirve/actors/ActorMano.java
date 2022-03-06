package com.mygdx.game.noSirve.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ActorMano extends Actor {
    private Texture mano;

    public ActorMano(Texture mano){
        this.mano = mano;
    }
    @Override
    public void act(float delta) {

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(mano,getX(),getY());
    }
}
