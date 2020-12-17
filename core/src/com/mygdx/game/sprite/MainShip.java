package com.mygdx.game.sprite;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.base.Sprite;
import com.mygdx.game.math.Rect;

public class MainShip extends Sprite {

    private static final float HEIGHT = 0.15f;
    private static final float BUTTOM_MARGIN = 0.05f;

    private final Vector2 v;
    private final Vector2 v0;

    private Rect worldBounds;

    private boolean pressedLeft;
    private boolean pressedRight;

    public MainShip(TextureAtlas atlas) {
        super(atlas.findRegion("main_ship"), 1, 2, 2);
        v = new Vector2();
        v0 = new Vector2(0.5f, 0);
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        setHeightProportion(HEIGHT);
        setBottom(worldBounds.getBottom() + BUTTOM_MARGIN);
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(v, delta);
    }

    public boolean keyDown(int keycode) {
        switch (keycode){
            case Input.Keys.RIGHT:
            case Input.Keys.D:
                pressedRight =true;
                moveRight();
                break;
            case Input.Keys.LEFT:
            case Input.Keys.A:
                pressedLeft = true;
                moveLeft();
                break;
        }
        return false;
    }

    public boolean keyUp(int keycode) {
        switch (keycode){
            case Input.Keys.RIGHT:
            case Input.Keys.D:
                pressedRight = false;
                if (pressedLeft){
                    moveLeft();
                } else {
                    stop();
                }
                break;
            case Input.Keys.LEFT:
            case Input.Keys.A:
                pressedLeft = false;
                if (pressedRight){
                    moveRight();
                } else {
                    stop();
                }
                break;
        }

        return false;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        if (touch.x < worldBounds.pos.x){
            moveLeft();
        } else {
            moveRight();
        }
        return super.touchDown(touch, pointer, button);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        stop();
        return super.touchUp(touch, pointer, button);
    }

    private void moveLeft(){
        v.set(v0).rotate(180);
    }

    private void moveRight(){
        v.set(v0);
    }

    private void stop(){
        v.setZero();
    }
}