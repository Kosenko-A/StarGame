package com.mygdx.game.pool;

import com.mygdx.game.base.SpritesPool;
import com.mygdx.game.math.Rect;
import com.mygdx.game.sprite.Enemy;

public class EnemyPool extends SpritesPool<Enemy> {

    private final BulletPool bulletPool;
    private final  ExplosionPool explosionPool;
    private final Rect worldBounds;

    public EnemyPool(BulletPool bulletPool, ExplosionPool explosionPool, Rect worldBounds){
        this.bulletPool = bulletPool;
        this.worldBounds = worldBounds;
        this.explosionPool = explosionPool;

    }

    public Enemy newObject(){
        return new  Enemy(bulletPool, explosionPool, worldBounds);
    }
}
