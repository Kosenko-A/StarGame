package com.mygdx.game.base;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.math.Rect;
import com.mygdx.game.utils.Regions;

public class Sprite extends Rect {

    private float angle;
    private float scale = 1;
    protected TextureRegion[] regions;
    private int frame;
    private boolean destroyed;

    public Sprite(){

    }

    public Sprite(TextureRegion region){
        regions = new TextureRegion[1];
        regions[0] = region;
    }

    public Sprite (TextureRegion region, int rows, int cols, int frames){
        regions = Regions.split(region, rows, cols, frames);
    }

    public void setHeightProportion(float height){
        setHeight(height);
        float aspect = regions[frame].getRegionWidth() / regions[frame].getRegionWidth();
        setWidth(height * aspect);
    }

    public void update(float delta){

    }

    public void resize(Rect worldBounds){

    }

    public boolean touchDown(Vector2 touch, int pointer, int button){
        return false;
    }

    public boolean touchUp(Vector2 touch, int pointer, int button){
        return false;
    }

    public boolean touchDragget(Vector2 touch, int pointer, int button){
        return false;
    }

    public void draw(SpriteBatch batch){
        batch.draw(
                regions[frame],
                getLeft(), getBottom(),
                halfWidth, halfHeight,
                getWidth(), getHeight(),
                scale, scale,
                angle
        );
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public boolean isDestroyed(){
        return destroyed;
    }

    public void destroy(){
        destroyed = true;
    }

    public void flushDestroy(){
        destroyed = false;
    }
}
