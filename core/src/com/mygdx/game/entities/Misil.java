package com.mygdx.game.entities;

import static com.mygdx.game.Constantes.PIXELS_IN_METER;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Misil extends Actor {

    public static final int STATE_NORMAL = 0;

    public int vel;
    public int state;

    private Texture texture;
    private World world;
    private Body body;
    private Fixture fixture;
    private Vector2 position;
    private float stateTime;


    public Misil(World world,Texture texture , Vector2 position){
        this.world = world;
        this.position = position;
        this.texture = texture;

        stateTime = 0f;
        state = STATE_NORMAL;

        createBody();
        createFixture();

        setSize(PIXELS_IN_METER,PIXELS_IN_METER);
    }

    private void createFixture() {
        PolygonShape box = new PolygonShape();
        box.setAsBox(0.3f,0.3f);
        this.fixture = this.body.createFixture(box,1);
        this.fixture.setUserData("misil");
        box.dispose();
    }

    private void createBody() {
        BodyDef def = new BodyDef();
        def.position.set(position);
        def.type = BodyDef.BodyType.DynamicBody;
        this.body = this.world.createBody(def);
    }




    @Override
    public void draw(Batch batch, float parentAlpha) {
        setPosition((body.getPosition().x - 0.5f) * PIXELS_IN_METER, (body.getPosition().y - 0.5f) * PIXELS_IN_METER);
        setSize(PIXELS_IN_METER, PIXELS_IN_METER);
        batch.draw(this.texture, getX(), getY(), getWidth(), getHeight());
        //setPosition((body.getPosition().x - 0.5f) * es.danirod.jddprototype.game.Constants.PIXELS_IN_METER,
        //        (body.getPosition().y - 0.5f) * es.danirod.jddprototype.game.Constants.PIXELS_IN_METER);
    }

    @Override
    public void act(float delta) {
        if (Gdx.input.justTouched()){
            avanzar();
        }

    }



    public void avanzar() {
        body.setLinearVelocity(0,4);
    }

    public void detach(){
        this.body.destroyFixture(this.fixture);
        this.world.destroyBody(this.body);
    }

}
