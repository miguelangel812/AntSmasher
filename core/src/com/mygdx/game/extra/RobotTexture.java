package com.mygdx.game.extra;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.noSirve.Robot;

public class RobotTexture {

    private AssetManager assetManager;
    private TextureAtlas textureAtlas;

    public RobotTexture(){
        this.assetManager = new AssetManager();
        assetManager.load("robot.txt",TextureAtlas.class);
        assetManager.finishLoading();
        textureAtlas = assetManager.get("robot.txt");
    }
    public Animation<TextureRegion> getRobotAnimation(){
        return new Animation<TextureRegion>(
                0.05f,
                textureAtlas.createSprite("character_robot_walk0"),
                textureAtlas.createSprite("character_robot_walk1"),
                textureAtlas.createSprite("character_robot_walk2"),
                textureAtlas.createSprite("character_robot_walk3"),
                textureAtlas.createSprite("character_robot_walk4"),
                textureAtlas.createSprite("character_robot_walk5"),
                textureAtlas.createSprite("character_robot_walk6"),
                textureAtlas.createSprite("character_robot_walk7")
        );
    }

}
