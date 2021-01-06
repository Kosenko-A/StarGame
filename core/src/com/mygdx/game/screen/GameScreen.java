package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.base.BaseScreen;
import com.mygdx.game.math.Rect;
import com.mygdx.game.pool.BulletPool;
import com.mygdx.game.pool.EnemyPool;
import com.mygdx.game.pool.ExplosionPool;
import com.mygdx.game.sprite.Background;
import com.mygdx.game.sprite.Bullet;
import com.mygdx.game.sprite.ButtonNewGame;
import com.mygdx.game.sprite.Enemy;
import com.mygdx.game.sprite.GameOver;
import com.mygdx.game.sprite.MainShip;
import com.mygdx.game.sprite.Star;
import com.mygdx.game.utils.EnemyEmitter;
import com.mygdx.game.utils.Font;

import java.util.List;
import java.util.logging.FileHandler;

public class GameScreen extends BaseScreen {

    private static final int STAR_COUNT = 128;

    private  enum State {PLAYING, GAME_OVER};

    private static  final float MAX_HEIGHT = 0.009f;
    private static final float MIN_HEIGHT = 0.005f;

    private static final float FONT_SIZE = 0.03f;
    private static final float MARGIN = 0.01f;

    private static final String FRAGS = "Frags: ";
    private static final String HP = "HP: ";
    private static final String LEVEL = "Level: ";
    private static final String TOP = "Top: ";

    private Texture bg;
    private Background background;
    private TextureAtlas atlas;
    private TextureAtlas wordsAtlas;
    private Star[] stars;

    private BulletPool bulletPool;
    private EnemyPool enemyPool;
    private ExplosionPool explosionPool;


    private EnemyEmitter enemyEmitter;

    private MainShip mainShip;

    private Music music;
    private Sound bulletSound;
    private Sound explosionSound;

    private int top;
    private String topStr;


    private State state;
    private GameOver gameOver;
    private ButtonNewGame buttonNewGame;

    private int frags;
    private Font font;
    private StringBuilder sbFrags;
    private StringBuilder sbHP;
    private StringBuilder sbLevel;
    private StringBuilder sbTop;

    @Override
    public void show() {
        super.show();
        bg = new Texture("textures/spaceFon.png");
        background = new Background(bg);
        atlas = new TextureAtlas("textures/mainAtlas.tpack");
        wordsAtlas = new TextureAtlas("textures/wordsAtlas.pack");
        stars = new Star[STAR_COUNT];
        for (int i = 0; i< STAR_COUNT; i++){
            stars[i] = new Star(atlas, MAX_HEIGHT, MIN_HEIGHT);
        }
        bulletPool = new BulletPool();
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("sounds/boom.mp3"));
        explosionPool = new ExplosionPool(atlas, explosionSound);
        enemyPool = new EnemyPool(bulletPool, explosionPool, worldBounds);
        mainShip = new MainShip(atlas, bulletPool, explosionPool);
        bulletSound = Gdx.audio.newSound(Gdx.files.internal("sounds/enemy_sound.mp3"));
        enemyEmitter = new EnemyEmitter(atlas, worldBounds, bulletSound, enemyPool);

        gameOver = new GameOver(wordsAtlas);
        buttonNewGame = new ButtonNewGame(wordsAtlas, this);
        sbFrags = new StringBuilder();
        sbHP = new StringBuilder();
        sbLevel = new StringBuilder();
        sbTop = new StringBuilder();
        font = new Font("fonts/game_font.fnt", "fonts/game_font.png");
        font.setSize(FONT_SIZE);

        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/soundtrack.mp3"));
        music.setLooping(true);
        music.play();
        state = State.PLAYING;

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        checkCollision();
        freeAllDestroyed();
        draw();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        for (Star stars: stars){
            stars.resize(worldBounds);
        }
        mainShip.resize(worldBounds);
        gameOver.resize(worldBounds);
        buttonNewGame.resize(worldBounds);
    }

    @Override
    public void dispose() {
        bg.dispose();
        atlas.dispose();
        wordsAtlas.dispose();
        bulletPool.dispose();
        music.dispose();
        mainShip.dispose();
        enemyPool.dispose();
        explosionPool.dispose();
        explosionSound.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        if (state == State.PLAYING) {
            mainShip.touchDown(touch, pointer, button);
        } else if (state == State.GAME_OVER){
            buttonNewGame.touchDown(touch, pointer, button);
        }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        if (state == State.PLAYING) {
            mainShip.touchUp(touch, pointer, button);
        } else if (state == State.GAME_OVER){
            buttonNewGame.touchUp(touch, pointer, button);
        }
        return super.touchUp(touch, pointer, button);
    }

    @Override
    public boolean keyDown(int keycode) {
        if (state == State.PLAYING) {
            mainShip.keyDown(keycode);
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (state == State.PLAYING) {
            mainShip.keyUp(keycode);
        }
        return false;
    }

    public void startNewGame(){
        mainShip.startNewGame();
        enemyPool.freeAll();
        bulletPool.freeAll();
        explosionPool.freeAll();
        frags = 0;


        state = State.PLAYING;
    }

    private void update (float delta){
        for (Star stars: stars) {
            stars.update(delta);
        }
        explosionPool.updateActiveObjects(delta);
        if(state == State.PLAYING) {
            bulletPool.updateActiveObjects(delta);
            mainShip.update(delta);
            enemyPool.updateActiveObjects(delta);
            enemyEmitter.generate(delta, frags);
        }
    }

    private void checkCollision() {
        if (state == State.GAME_OVER) {
            return;
        }
        List<Enemy> enemyList = enemyPool.getActiveObjects();
        for (Enemy enemy : enemyList) {
            if (enemy.isDestroyed()) {
                continue;
            }
            float minDist = enemy.getHalfWidth() + mainShip.getHalfWidth();
            if (enemy.pos.dst(mainShip.pos) < minDist) {
                enemy.destroy();
                mainShip.damage(enemy.getDamage());
            }
        }
        List<Bullet> bulletList = bulletPool.getActiveObjects();
        for (Bullet bullet : bulletList) {
            if (bullet.isDestroyed()) {
                continue;
            }
            if (bullet.getOwner() != mainShip) {
                if (mainShip.isBulletCollision(bullet)) {
                    mainShip.damage(bullet.getDamage());
                    bullet.destroy();
                    return;
                }
                continue;
            }

            for (Enemy enemy : enemyList) {
                if (enemy.isBulletCollision(bullet)){
                    enemy.damage(bullet.getDamage());
                    if (enemy.isDestroyed()){
                        frags++;
                        setHighScore(getHighScore(),frags);
                    }
                    bullet.destroy();
                    return;
                }
                if (enemy.getBottom() < worldBounds.getBottom()){
                    state = State.GAME_OVER;
                }
            }
            if (mainShip.isDestroyed()) {
                state = State.GAME_OVER;
            }
        }
    }

    private void freeAllDestroyed(){
        bulletPool.freeAllDestroyedActiveObjects();
        enemyPool.freeAllDestroyedActiveObjects();
        explosionPool.freeAllDestroyedActiveObjects();
    }

    private void draw(){
        batch.begin();
        background.draw(batch);
        for (Star stars: stars) {
            stars.draw(batch);
        }
        if (state == State.PLAYING) {
            bulletPool.drawActiveObjects(batch);
            mainShip.draw(batch);
            enemyPool.drawActiveObjects(batch);
        } else if (state == State.GAME_OVER){
            gameOver.draw(batch);
            buttonNewGame.draw(batch);
        }
        explosionPool.drawActiveObjects(batch);
        printInfo();
        batch.end();

    }

    private void printInfo(){
        sbFrags.setLength(0);
        font.draw(batch, sbFrags.append(FRAGS).append(frags), worldBounds.getLeft() + MARGIN, worldBounds.getTop() - MARGIN);

        sbHP.setLength(0);
        font.draw(batch, sbHP.append(HP).append(mainShip.getHp()), worldBounds.pos.x, worldBounds.getTop() - MARGIN, Align.center);

        sbLevel.setLength(0);
        font.draw(batch, sbLevel.append(LEVEL).append(enemyEmitter.getLevel()), worldBounds.getRight() - MARGIN, worldBounds.getTop() - MARGIN, Align.right);

        sbTop.setLength(0);
        font.draw(batch, sbTop.append(TOP).append(getHighScore()), worldBounds.getLeft() + MARGIN, worldBounds.getTop() - 0.05f);
    }

    private int getHighScore() {
        FileHandle file = Gdx.files.internal("files/top.txt");
        topStr = file.readString();
        top = Integer.parseInt(topStr);
        return top;
    }

    private void setHighScore(int top, int frags) {
        if (frags>top) {
            FileHandle file = Gdx.files.local("files/top.txt");
            String score = Integer.toString(frags);
            file.writeString(score, false);
        }
    }


}
