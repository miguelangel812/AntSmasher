package com.mygdx.game.noSirve;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.BaseScreen;
import com.mygdx.game.MainGame;

public class Box2DScreen extends BaseScreen {
    public Box2DScreen(MainGame game){
        super(game);
    }

    private World world;
    private OrthographicCamera camera;
    private Box2DDebugRenderer renderer;
    private Body mariquitaBody,sueloBody;
    private Fixture mariquitaFixture,sueloFixture;
    private boolean debesaltar,mariquitaSaltando,vida = true;


    @Override
    public void show() {
        world = new World(new Vector2(0,-10),true);
        renderer = new Box2DDebugRenderer();
        camera = new OrthographicCamera(16,9);
        camera.translate(0,1);

        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                Fixture fixtureA = contact.getFixtureA(), fixtureB = contact.getFixtureB();
                if (fixtureA.getUserData().equals("player") && fixtureB.getUserData().equals("floor")||fixtureB.getUserData().equals("floor") && fixtureA.getUserData().equals("player")){
                    if (Gdx.input.isTouched()){
                        debesaltar = true;
                    }
                    mariquitaSaltando = false;
                }

                if (fixtureA.getUserData().equals("player") && fixtureB.getUserData().equals("mano")||fixtureB.getUserData().equals("mano") && fixtureA.getUserData().equals("player")){
                    if (Gdx.input.isTouched()){
                        vida = false;
                    }
                }

            }

            @Override
            public void endContact(Contact contact) {
                Fixture fixtureA = contact.getFixtureA(), fixtureB = contact.getFixtureB();
                if (fixtureA == mariquitaFixture && fixtureB == sueloFixture){
                    mariquitaSaltando = true;
                }
                if (fixtureA == sueloFixture && fixtureB == mariquitaFixture){
                    mariquitaSaltando = true;
                }

            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });

        //BodyDef mariquitaDef = createMariquitaBodyDef();
        //mariquitaBody = world.createBody(mariquitaDef);
        mariquitaBody = world.createBody(createMariquitaBodyDef());
        sueloBody = world.createBody(createSueloBody());




        PolygonShape mariquitaShape = new PolygonShape();
        mariquitaShape.setAsBox(0.5f,0.5f);
        mariquitaFixture = mariquitaBody.createFixture(mariquitaShape,1);
        mariquitaShape.dispose();

        PolygonShape sueloShape = new PolygonShape();
        sueloShape.setAsBox(500,1);
        sueloFixture = sueloBody.createFixture(sueloShape,1);
        sueloShape.dispose();

        mariquitaFixture.setUserData("player");
        sueloFixture.setUserData("floor");
    }

    private BodyDef createSueloBody() {
        BodyDef def = new BodyDef();
        def.position.set(0,-1);
        def.type = BodyDef.BodyType.StaticBody;
        return def;
    }

    private BodyDef createMariquitaBodyDef() {
        BodyDef def = new BodyDef();
        def.position.set(0,5);
        def.type = BodyDef.BodyType.DynamicBody;
        return def;
    }

    @Override
    public void dispose() {
        mariquitaBody.destroyFixture(mariquitaFixture);
        world.destroyBody(mariquitaBody);
        world.dispose();
        renderer.dispose();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (debesaltar){
            debesaltar = false;
            muerte();
        }
        if (Gdx.input.justTouched() && !mariquitaSaltando){
            debesaltar = true;
        }

        if (vida){
            float velocidadY = mariquitaBody.getLinearVelocity().y;
            mariquitaBody.setLinearVelocity(1,velocidadY);
        }



        world.step(delta,6,2);
        camera.update();
        renderer.render(world, camera.combined);
    }

    private void muerte(){
        //mariquitaBody.destroyFixture(mariquitaFixture);
        Vector2 position = mariquitaBody.getPosition();
        if (position.x > Gdx.input.getX() && position.y > Gdx.input.getY()){
            mariquitaBody.destroyFixture(mariquitaFixture);
        }

        mariquitaBody.applyLinearImpulse(0,5,position.x,position.y, true);
    }
    /*
    private void comprobarColision(){
        if (Gdx.input.justTouched()){
            System.out.println("muerto");
            mariquita.setAlive(false);
        }
    }*/

}
