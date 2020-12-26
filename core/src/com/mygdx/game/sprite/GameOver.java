package com.mygdx.game.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.mygdx.game.base.Sprite;
import com.mygdx.game.math.Rect;

public class GameOver extends Sprite {

    public GameOver (TextureAtlas wordsAtlas){
        super(wordsAtlas.findRegion("game_over"));
    }

    @Override
    public void resize(Rect worldBounds) {
        setTop(0.06f);
        setHeightProportion(0.5f);
    }
}
