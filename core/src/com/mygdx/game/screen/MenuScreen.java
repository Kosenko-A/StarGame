package com.mygdx.game.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.base.BaseScreen;
import com.mygdx.game.base.Sprite;
import com.mygdx.game.math.Rect;
import com.mygdx.game.math.Rnd;
import com.mygdx.game.sprite.Background;
import com.mygdx.game.sprite.ButtonExit;
import com.mygdx.game.sprite.ButtonPlay;
import com.mygdx.game.sprite.Star;

public class MenuScreen extends BaseScreen {

    private static final int STAR_COUNT = 256;

    private static final float MAX_HEIGHT = 0.05f;
    private static final float MIN_HEIGHT = 0.01f;

    private Texture bg;
    private Background background;

    private TextureAtlas atlas;
    private Star[] stars;

    private ButtonExit buttonExit;
    private ButtonPlay buttonPlay;

    private final Game game;

    public MenuScreen(Game game) {
        this.game = game;
    }

    private Music music;

    @Override
    public void show() {
        bg = new Texture("textures/spaceFon.png");
        background = new Background(bg);
        atlas = new TextureAtlas("textures/test.pack");
        stars = new Star[STAR_COUNT];
        for (int i = 0; i< STAR_COUNT; i++){
            stars[i] = new Star(atlas, MAX_HEIGHT, MIN_HEIGHT );
        }
        buttonExit = new ButtonExit(atlas);
        buttonPlay = new ButtonPlay(atlas, game);
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/menu_sound.mp3"));
        music.setLooping(true);
        music.play();
        super.show();
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
        music.dispose();
        super.dispose();
    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        for (Star stars: stars){
            stars.resize(worldBounds);
        }
        buttonExit.resize(worldBounds);
        buttonPlay.resize(worldBounds);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        buttonExit.touchDown(touch, pointer, button);
        buttonPlay.touchDown(touch, pointer, button);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        buttonExit.touchUp(touch, pointer, button);
        buttonPlay.touchUp(touch, pointer, button);
        return false;
    }

    private void update (float delta){
        for (Star stars: stars) {
            stars.update(delta);
        }
    }

    private void draw (){
        batch.begin();
        background.draw(batch);
        for (Star stars: stars) {
            stars.draw(batch);
        }
        buttonExit.draw(batch);
        buttonPlay.draw(batch);
        batch.end();
    }
}
