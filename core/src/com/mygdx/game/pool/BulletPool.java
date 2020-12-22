package com.mygdx.game.pool;

import com.mygdx.game.base.Sprite;
import com.mygdx.game.base.SpritesPool;
import com.mygdx.game.sprite.Bullet;

public class BulletPool extends SpritesPool <Bullet> {

    @Override
    public Bullet newObject() {
        return new Bullet();
    }
}
