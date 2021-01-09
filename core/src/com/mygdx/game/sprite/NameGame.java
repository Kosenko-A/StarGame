package com.mygdx.game.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.mygdx.game.base.Sprite;
import com.mygdx.game.math.Rect;

public class NameGame extends Sprite {

    public NameGame (TextureAtlas name){
        super(name.findRegion("game_name"));
    }

    @Override
    public void resize(Rect worldBounds) {
        setTop(0.4f);
        setHeightProportion(0.55f);
    }
}
