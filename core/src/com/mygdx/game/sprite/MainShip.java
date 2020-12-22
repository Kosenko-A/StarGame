package com.mygdx.game.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.base.Sprite;
import com.mygdx.game.math.Rect;
import com.mygdx.game.pool.BulletPool;

public class MainShip extends Sprite {

    private static final float RELOAD_INTERVAL = 0.2f;

    private static final float HEIGHT = 0.15f;
    private static final float BUTTOM_MARGIN = 0.05f;

    private static final int INVALID_POINTER = -1;

    private final BulletPool bulletPool;
    private TextureRegion bulletRegion;
    private Sound bulletSound;
    private Vector2 bulletV;

    private final Vector2 v;
    private final Vector2 v0;
    private Vector2 bulletPos;

    private Rect worldBounds;

    private boolean pressedLeft;
    private boolean pressedRight;

    private int leftPointer = INVALID_POINTER;
    private int rightPointer = INVALID_POINTER;

    private float reloadTimer;

    private float bulletHeight;
    private int damage;

    public MainShip(TextureAtlas atlas, BulletPool bulletPool) {
        super(atlas.findRegion("main_ship"), 1, 2, 2);
        this.bulletPool = bulletPool;
        bulletRegion = atlas.findRegion("bulletMainShip");
        bulletSound = Gdx.audio.newSound(Gdx.files.internal("sounds/main_shoot.mp3"));
        bulletV = new Vector2(0, 0.5f);
        bulletPos = new Vector2();
        bulletHeight = 0.01f;
        damage = 1;
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
        bulletPos.set(pos.x, pos.y + getHalfHeight());
        reloadTimer += delta;
        if (reloadTimer >= RELOAD_INTERVAL){
            reloadTimer = 0f;
            shoot();
        }
        if (getRight() > worldBounds.getRight()){
            setRight(worldBounds.getRight());
            stop();
        }
        if (getLeft() < worldBounds.getLeft()){
            setLeft(worldBounds.getLeft());
            stop();
        }
    }

    public boolean keyDown(int keycode) {
        switch (keycode){
            case Input.Keys.RIGHT:
            case Input.Keys.D:
                pressedRight = true;
                moveRight();
                break;
            case Input.Keys.LEFT:
            case Input.Keys.A:
                pressedLeft = true;
                moveLeft();
                break;
            case Input.Keys.SPACE:
                shoot();
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
            if (leftPointer != INVALID_POINTER){
                return false;
            }
            leftPointer = pointer;
            moveLeft();
        } else {
            if (rightPointer != INVALID_POINTER){
                return false;
            }
            rightPointer = pointer;
            moveRight();
        }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        if (pointer == leftPointer){
            leftPointer = INVALID_POINTER;
            if (rightPointer != INVALID_POINTER){
                moveRight();
            } else {
                stop();
            }
        } else if (pointer == rightPointer){
            rightPointer = INVALID_POINTER;
            if (leftPointer != INVALID_POINTER){
                moveLeft();
            } else {
                stop();
            }
        }
        return super.touchUp(touch, pointer, button);
    }

    public void dispose(){
        bulletSound.dispose();
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

    private void shoot(){
        bulletSound.play(0.4f);
        Bullet bullet = bulletPool.obtain();
        bullet.set(this, bulletRegion, bulletPos, bulletV, bulletHeight, worldBounds, damage);
    }
}
