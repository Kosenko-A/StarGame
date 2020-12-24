package com.mygdx.game.sprite;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.base.Ship;
import com.mygdx.game.math.Rect;
import com.mygdx.game.pool.BulletPool;
import com.mygdx.game.pool.ExplosionPool;

public class Enemy extends Ship {

    private Vector2 startV;

    public Enemy(BulletPool bulletPool, ExplosionPool explosionPool, Rect worldBounds){
        super(bulletPool, explosionPool);
        this.worldBounds = worldBounds;
        this.v = new Vector2();
        this.v0 = new Vector2();
        this.bulletPos = new Vector2();
        startV = new Vector2(0,-0.3f);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        if (getTop() < worldBounds.getTop()){
            this.v.set(v0);
        } else {
            reloadTimer = reloadInterval - delta * 2;
        }
        bulletPos.set(pos.x, pos.y - getHalfHeight());
        if (getBottom()<worldBounds.getBottom()){
            destroy();
        }
    }

    public void set(
            TextureRegion[] regions,
            TextureRegion bulletRegion,
            Sound bulletSound,
            float bulletHeight,
            Vector2 bulletV,
            int damage,
            float reloadInterval,
            Vector2 v0,
            float height,
            int hp
    ){
            this.regions = regions;
            this.bulletRegion = bulletRegion;
            this.bulletSound = bulletSound;
            this.bulletHeight = bulletHeight;
            this.bulletV = bulletV;
            this.damage = damage;
            this.reloadInterval = reloadInterval;
            this.v0.set(v0);
            this.v.set(startV);
            setHeightProportion(height);
            this.hp = hp;
    }
}
