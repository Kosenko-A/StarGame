package com.mygdx.game.base;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.math.Rect;
import com.mygdx.game.pool.BulletPool;
import com.mygdx.game.pool.ExplosionPool;
import com.mygdx.game.sprite.Bullet;
import com.mygdx.game.sprite.Explosion;

public class Ship extends Sprite{

    protected   static final  float DAMAGE_ANIMATE_INTERVAL = 0.1F;

    protected final BulletPool bulletPool;
    private final ExplosionPool explosionPool;

    protected TextureRegion bulletRegion;
    protected Sound bulletSound;
    protected Vector2 bulletV;
    protected Vector2 bulletPos;
    protected float bulletHeight;
    protected int damage;
    protected int hp;

    protected Vector2 v;
    protected Vector2 v0;

    protected Rect worldBounds;

    protected float reloadInterval;
    protected float reloadTimer;
    protected float damageAnimateTimer;

    public Ship(BulletPool bulletPool, ExplosionPool explosionPool){
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        damageAnimateTimer = DAMAGE_ANIMATE_INTERVAL;
    }
    public Ship(TextureRegion region, int rows, int cols, int frames, BulletPool bulletPool, ExplosionPool explosionPool){
        super(region, rows, cols, frames);
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(v, delta);
        reloadTimer += delta;
        if (reloadTimer >= reloadInterval){
            reloadTimer = 0f;
            shoot();
        }
        damageAnimateTimer += delta;
        if (damageAnimateTimer>=DAMAGE_ANIMATE_INTERVAL){
            frame = 0;
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        boom();
    }

    public void damage(int damage){
        this.hp -= damage;
        if(hp<=0){
            hp = 0;
            destroy();
        }
        frame = 1;
        damageAnimateTimer = 0f;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getDamage() {
        return damage;
    }

    private void shoot(){
        bulletSound.play(0.4f);
        Bullet bullet = bulletPool.obtain();
        bullet.set(this, bulletRegion, bulletPos, bulletV, bulletHeight, worldBounds, damage);

    }

    private void boom(){
        Explosion explosion = explosionPool.obtain();
        explosion.set(this.pos, getHeight());
    }
}
