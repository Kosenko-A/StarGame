package com.mygdx.game.screen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.base.BaseScreen;
import com.mygdx.game.math.Rect;
import com.mygdx.game.math.Rnd;
import com.mygdx.game.sprite.Background;
import com.mygdx.game.sprite.MainShip;
import com.mygdx.game.sprite.Star;

public class GameScreen extends BaseScreen {

    private static final int STAR_COUNT = 128;

    private static  final float MAX_HEIGHT = 0.009f;
    private static final float MIN_HEIGHT = 0.005f;

    private Texture bg;
    private Background background;
    private TextureAtlas atlas;
    private Star[] stars;

    private MainShip mainShip;

    @Override
    public void show() {
        super.show();
        bg = new Texture("textures/spaceFon.png");
        background = new Background(bg);
        atlas = new TextureAtlas("textures/mainAtlas.tpack");
        stars = new Star[STAR_COUNT];
        for (int i = 0; i< STAR_COUNT; i++){
            stars[i] = new Star(atlas, MAX_HEIGHT, MIN_HEIGHT);
        }
        mainShip = new MainShip(atlas);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        draw();
    }

    @Override
    public void dispose() {
        bg.dispose();
        atlas.dispose();
        super.dispose();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        for (Star stars: stars){
            stars.resize(worldBounds);
        }
        mainShip.resize(worldBounds);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        mainShip.touchDown(touch, pointer, button);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        mainShip.touchUp(touch, pointer, button);
        return super.touchUp(touch, pointer, button);
    }

    @Override
    public boolean keyDown(int keycode) {
        mainShip.keyDown(keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        mainShip.keyUp(keycode);
        return false;
    }

    private void update (float delta){
        for (Star stars: stars) {
            stars.update(delta);
        }
        mainShip.update(delta);
    }

    private void draw(){
        batch.begin();
        background.draw(batch);
        for (Star stars: stars) {
            stars.draw(batch);
        }
        mainShip.draw(batch);
        batch.end();
    }


}
