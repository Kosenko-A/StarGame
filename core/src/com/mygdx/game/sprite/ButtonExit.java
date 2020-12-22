package com.mygdx.game.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.base.BaseButton;
import com.mygdx.game.math.Rect;

public class ButtonExit extends BaseButton {

    private static final float MARGIN = 0.05f;
    private static final float HEIGHT = 0.2f;

    public ButtonExit(TextureAtlas atlas) {
        super(atlas.findRegion("button_exit-min"));
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setHeightProportion(HEIGHT);
        setBottom(worldBounds.getBottom() + MARGIN);
        setRight(worldBounds.getRight() - MARGIN);
    }

    @Override
    public void action() {
        Gdx.app.exit();
    }
}
