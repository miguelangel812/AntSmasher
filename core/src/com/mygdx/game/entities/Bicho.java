package com.mygdx.game.entities;

import static com.mygdx.game.Constantes.PIXELS_IN_METER;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.mygdx.game.Constantes;
import com.mygdx.game.extra.RobotTexture;

public class Bicho extends Actor {

    public static final int STATE_NORMAL = 0;

    public int vel;
    public int state;

    private Animation<TextureRegion> robotAnimation;
    private World world;
    private Body body;
    private Fixture fixture;
    private Vector2 position;
    private float stateTime;
    private boolean alive = true, walking = false;


    public Bicho(World world, Animation<TextureRegion> animation, Vector2 position){
        this.world = world;
        this.robotAnimation = animation;
        this.position = position;

        stateTime = 0f;
        state = STATE_NORMAL;

        createBody();
        createFixture();

        setSize(PIXELS_IN_METER,PIXELS_IN_METER);
    }

    private void createFixture() {
        PolygonShape box = new PolygonShape();
        box.setAsBox(0.5f,0.5f);
        this.fixture = this.body.createFixture(box,1);
        this.fixture.setUserData("hormiga");
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
        if (vel == 1) {
            setPosition((body.getPosition().x - 0.5f) * PIXELS_IN_METER, (body.getPosition().y - 0.5f) * PIXELS_IN_METER);
            setSize(PIXELS_IN_METER, PIXELS_IN_METER);

        }else if (vel == -1){
            setPosition((body.getPosition().x + 0.5f) * PIXELS_IN_METER, (body.getPosition().y - 0.5f) * PIXELS_IN_METER);
            setSize(-PIXELS_IN_METER, PIXELS_IN_METER);
        }
        batch.draw(this.robotAnimation.getKeyFrame(stateTime,true),getX(),getY(),getWidth(),getHeight());
        stateTime += Gdx.graphics.getDeltaTime();
    }

    @Override
    public void act(float delta) {
        if (alive){
            avanzar();
        }

    }



    public void avanzar() {
        if (alive){
            float speedY = body.getLinearVelocity().y;
            body.setLinearVelocity(vel,speedY);
        }
    }

    public void detach(){
        this.body.destroyFixture(this.fixture);
        this.world.destroyBody(this.body);
    }


    public int vel() {
        return vel;
    }
    public void setVel(int vel){
        this.vel = vel;
    }

}
