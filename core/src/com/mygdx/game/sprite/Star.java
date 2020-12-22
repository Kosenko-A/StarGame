package com.mygdx.game.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.base.Sprite;
import com.mygdx.game.math.Rect;
import com.mygdx.game.math.Rnd;

public class Star extends Sprite {

    private static float MAX_HEIGHT;
    private static float MIN_HEIGHT;

    private final Vector2 v;
    private Rect worldBounds;

    public Star(TextureAtlas atlas, float a, float b) {
        super(atlas.findRegion("star"));
        float vx = Rnd.nextFloat(-0.005f, 0.005f);
        float vy = Rnd.nextFloat(-0.15f, -0.005f);
        v = new Vector2(vx, vy);
        MAX_HEIGHT = a;
        MIN_HEIGHT = b;
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        float x = Rnd.nextFloat(worldBounds.getLeft(), worldBounds.getRight());
        float y = Rnd.nextFloat(worldBounds.getBottom(), worldBounds.getTop());
        pos.set(x, y);
        setHeightProportion(Rnd.nextFloat(MIN_HEIGHT, MAX_HEIGHT));
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(v, delta);
        if (getRight() < worldBounds.getLeft()){
            setLeft(worldBounds.getRight());
        }
        if (getLeft() > worldBounds.getRight()){
            setRight(worldBounds.getLeft());
        }
        if (getTop() < worldBounds.getBottom()){
            setBottom(worldBounds.getTop());
        }
        changeHeight();
    }

    private void changeHeight(){
        float height = getHeight() + 0.00001f;
        if (height > MAX_HEIGHT) {
            height = MIN_HEIGHT;
        }
        setHeightProportion(height);
    }
}
