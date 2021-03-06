package com.mygdx.game.noSirve;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class Robot {
    static final float WIDTH = .45f;
    static final float HEIGHT = .6f;

    static final float DRAW_WIDTH = 1.3f;
    static final float DRAW_WEIGHT = 1.7f;

    static final float WALK_FRAME_DURATION = 0.05f;

    static final float WALK_SPEED = 3;
    static final float JUMP_SPEED = 8;

    boolean isJumping,isFalling,isWalking,isDucking;

    float stateTime = 0;
    Vector2 position;
    Vector2 velocity;

    boolean didDuck,didJump;

    public Robot(float x,float y){
        position = new Vector2(x,y);
    }
    public void update(Body body, float delta, float accelX){
        position.x = body.getPosition().x;
        position.y = body.getPosition().y;

        velocity = body.getLinearVelocity();


        if (didDuck){
            isDucking = true;
            didDuck = false;
            stateTime = 0;
        }
        if (didJump){
            isJumping = true;
            didJump = false;
            stateTime = 0;
            velocity.y = JUMP_SPEED;
        }
        if (accelX == -1){
            velocity.x = -WALK_SPEED;
            isWalking = !isJumping && !isFalling;
        }else if(accelX == 1){
            velocity.x = WALK_SPEED;
            isWalking = !isJumping && !isFalling;
        }else{
            velocity.x = 0;
            isWalking = false;
        }

        if (isJumping){
            if (velocity.y <= 0){
                isJumping = false;
                isFalling = true;
                stateTime = 0;
            }
        }else if(isFalling){
            if (velocity.y >=0){
                isFalling = false;
                stateTime = 0;
            }
        }
        body.setLinearVelocity(velocity);
        stateTime+=delta;
    }
    public void jump(){
        if(!isJumping && !isFalling){
            didJump = true;
        }
    }
    public void duck(){
        if (!isJumping && !isFalling && !isWalking){
            didDuck = true;
        }

    }
}
