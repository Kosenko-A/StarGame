package com.mygdx.game.sprite;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.mygdx.game.base.BaseButton;
import com.mygdx.game.math.Rect;
import com.mygdx.game.screen.GameScreen;

public class ButtonPlay extends BaseButton {

    private static final float HEIGHT = 0.28f;
    private final Game game;

    public ButtonPlay(TextureAtlas atlas, Game game) {
        super(atlas.findRegion("button_play-min"));
        this.game = game;
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setHeightProportion(HEIGHT);
        setBottom(worldBounds.getBottom() + 0.007f);
        setLeft(worldBounds.getLeft() + 0.02f);
    }

    @Override
    public void action() {
        game.setScreen(new GameScreen());
    }
}
