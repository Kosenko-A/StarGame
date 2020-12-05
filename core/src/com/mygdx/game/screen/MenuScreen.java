package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.base.BaseScreen;

public class MenuScreen extends BaseScreen {

    private Texture img;
    private Texture spaceship;
    private Vector2 pos;
    private Vector2 subVec;
    private Vector2 wayVec;

    @Override
    public void show() {
        img = new Texture("spaceFon.png");
        spaceship = new Texture("spaceship.png");
        pos = new Vector2();
        subVec = new Vector2(pos);
        wayVec = new Vector2();
        super.show();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(img, 0, 0, 2000, 2000);
        batch.draw(spaceship, pos.x, pos.y, 250,250);
        batch.end();
        if((int)subVec.x != (int)pos.x && (int)subVec.y != (int)pos.y){
            pos.add(wayVec);
        }
        super.render(delta);
    }

    @Override
    public void dispose() {
        img.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        screenY = Gdx.graphics.getHeight() - screenY;
        subVec.set(screenX, screenY);
        wayVec.set(subVec.x-pos.x, subVec.y-pos.y);
        wayVec.nor();
        return super.touchDown(screenX, screenY,pointer, button);
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.UP:
                pos.y += 10;
                break;
            case Input.Keys.DOWN:
                pos.y -= 10;
                break;
            case Input.Keys.RIGHT:
                pos.x += 10;
                break;
            case Input.Keys.LEFT:
                pos.x -= 10;
                break;
        }
        return super.keyDown(keycode);
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        pos.set(screenX, Gdx.graphics.getHeight() - screenY);
        return super.touchDragged(screenX, screenY, pointer);
    }

}
