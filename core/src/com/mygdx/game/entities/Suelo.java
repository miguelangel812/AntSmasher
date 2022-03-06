package com.mygdx.game.entities;

import static com.mygdx.game.Constantes.PIXELS_IN_METER;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Suelo extends Actor {

    private Texture fondo;
    private World world;
    private Body body, paredDerechaBody,paredIzquierdaBody;
    private Fixture fixture, paredDerechaFixture,paredIzquierdaFixture;

    public Suelo(World world, float x,float width,float y){
        this.world = world;

        BodyDef def = new BodyDef();
        def.position.set(x+width/2,y-0.5f);
        body = world.createBody(def);

        PolygonShape box = new PolygonShape();
        box.setAsBox(width/2,0.5f);
        fixture = body.createFixture(box,1);
        fixture.setUserData("suelo");
        box.dispose();

        //Pared Derecha
        BodyDef pareDerDef = new BodyDef();
        pareDerDef.position.set(x,y-0.55f);
        paredDerechaBody = world.createBody(pareDerDef);

        PolygonShape paredDerechaBox = new PolygonShape();
        paredDerechaBox.setAsBox(0.1f,0.5f);
        paredDerechaFixture = paredDerechaBody.createFixture(paredDerechaBox,1);
        paredDerechaFixture.setUserData("paredDerecha");
        paredDerechaBox.dispose();


        //Pared Izquierda
        BodyDef pareIzDef = new BodyDef();
        pareIzDef.position.set(x + 0.55f,y);
        paredIzquierdaBody = world.createBody(pareIzDef);

        PolygonShape paredIzquierdaBox = new PolygonShape();
        paredIzquierdaBox.setAsBox(0.1f,0.5f);
        paredIzquierdaFixture = paredIzquierdaBody.createFixture(paredIzquierdaBox,1);
        paredIzquierdaFixture.setUserData("paredIzquierda");
        paredIzquierdaBox.dispose();


        setSize(width * PIXELS_IN_METER,PIXELS_IN_METER);
        setPosition(x * PIXELS_IN_METER, (y - 1) * PIXELS_IN_METER);
    }
/*
    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(suelo, getX(), getY(), getWidth(), getHeight());
        batch.draw(overfloor, getX(), getY() + 0.9f * getHeight(), getWidth(), 0.1f * getHeight());
    }

*/
    public void detach() {
        body.destroyFixture(fixture);
        paredDerechaBody.destroyFixture(paredDerechaFixture);
        paredIzquierdaBody.destroyFixture(paredIzquierdaFixture);
        world.destroyBody(body);
        world.destroyBody(paredDerechaBody);
        world.destroyBody(paredIzquierdaBody);


    }
}
