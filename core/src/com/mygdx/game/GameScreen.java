package com.mygdx.game;

import static com.mygdx.game.Constantes.PIXELS_IN_METER;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.MouseJointDef;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.TouchableAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.entities.Bicho;
import com.mygdx.game.entities.Misil;
import com.mygdx.game.entities.Suelo;
import com.mygdx.game.extra.RobotTexture;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;


public class GameScreen extends BaseScreen{

    private Image background;
    private Stage stage;
    private World world;

    public boolean vivo;

    private Bicho hormiga;
    private Misil misil;

    private List<Suelo> floorList = new ArrayList<Suelo>();

    private Music tema;


    public GameScreen(final MainGame game){
        super(game);
        stage = new Stage(new FitViewport(640,360));
        world = new World(new Vector2(0,-10),true);

        tema = game.getManager().get("temazo.mp3");

        world.setContactListener(new ContactListener() {
            private boolean choque(Contact contact,Object objeto1,Object objeto2){
                return contact.getFixtureA().getUserData().equals(objeto1) &&
                        contact.getFixtureB().getUserData().equals(objeto2) ||
                        contact.getFixtureA().getUserData().equals(objeto2) &&
                        contact.getFixtureB().getUserData().equals(objeto1);
            }
            @Override
            public void beginContact(Contact contact) {
                if (choque(contact,"hormiga","suelo")){
                    System.out.println("0");
                    hormiga.setVel(1);
                }
                if (choque(contact,"hormiga","paredDerecha")){
                    System.out.println("-1  paredDerecha");
                    hormiga.setVel(-1);

                }
                if (choque(contact,"hormiga","paredIzquierda")){
                    System.out.println("1   paredIzquierda");
                    hormiga.setVel(1);
                }
                if (choque(contact,"hormiga","misil")){
                    misil.remove();
                    hormiga.remove();
                    vivo = false;

                    stage.addAction(
                            Actions.sequence(
                                    Actions.delay(1.5f),
                                    Actions.run(new Runnable() {
                                        @Override
                                        public void run() {
                                            game.setScreen(game.gameWinScreen);
                                        }
                                    })
                            )
                    );
                    vivo = false;
                }
                if (choque(contact,"misil","suelo")){
                    misil.remove();
                    stage.addAction(
                            Actions.sequence(
                                    Actions.delay(1.5f),
                                    Actions.run(new Runnable() {
                                        @Override
                                        public void run() {
                                            game.setScreen(game.gameOverScreen);
                                        }
                                    })
                            )
                    );
                }
            }

            @Override
            public void endContact(Contact contact) {

            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });

    }

    @Override
    public void show() {
        floorList.add(new Suelo(world,0,1000,1.75f));
        floorList.add(new Suelo(world,1.5f,0.2f,2));
        floorList.add(new Suelo(world,12.5f,0.5f,2));


        addBackground();
        addRobot();

        for(Suelo suelo:floorList){
            stage.addActor(suelo);
        }
        mouse();

        tema.setVolume(0.75f);
        tema.play();

    }
    private void addMisil(float x){
        Texture misilTexture = game.getManager().get("misil.png");

        //float posRandomX = MathUtils.random(1.5f, 12f);
        //System.out.println("misil -------------- " + posRandomX);

        this.misil = new Misil(this.world,misilTexture, new Vector2(x/PIXELS_IN_METER,12f ));
        this.stage.addActor(this.misil);
    }

    private void mouse(){
        Pixmap pixmap = new Pixmap(Gdx.files.internal("mira.png"));
        Cursor cursor = Gdx.graphics.newCursor(pixmap, pixmap.getWidth() / 2, pixmap.getHeight() / 2);
        Gdx.graphics.setCursor(cursor);
        pixmap.dispose();
    }

    private void addRobot(){
        Animation<TextureRegion> robotSprite = game.assetManger.getRobotAnimation();
        float posRandomX = MathUtils.random(1.5f, 12f);
        System.out.println(posRandomX);
        this.hormiga = new Bicho(this.world,robotSprite, new Vector2(posRandomX,3f ));
        this.stage.addActor(this.hormiga);
    }


    public void addBackground(){
        Texture sueloTexture = game.getManager().get("fondoEdificios.jpg");
        this.background = new Image(sueloTexture);
        this.background.setPosition(0,0);
        this.background.setSize(stage.getWidth(), stage.getHeight());
        this.stage.addActor(this.background);
    }



    @Override
    public void hide() {
        hormiga.detach();
        hormiga.remove();

        misil.detach();
        misil.remove();

        for(Suelo suelo:floorList){
            suelo.detach();
            suelo.remove();
        }

        background.remove();
        tema.stop();
    }

    @Override
    public void render(float delta) {
        try{

            Gdx.gl.glClearColor(0.4f, 0.5f, 0.8f, 1f);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


            if (Gdx.input.justTouched()){
                addMisil(Gdx.input.getX());
            }

            stage.act();
            world.step(delta,6,2);
            stage.draw();


        }catch (Exception  e){
            System.out.println(e);
        }
    }

    @Override
    public void dispose() {
        stage.dispose();
        world.dispose();
    }
}
