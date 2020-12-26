package com.mygdx.game.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.mygdx.game.base.BaseButton;
import com.mygdx.game.math.Rect;
import com.mygdx.game.screen.GameScreen;

public class ButtonNewGame extends BaseButton {

    private GameScreen gameScreen;

    public ButtonNewGame(TextureAtlas wordsAtlas, GameScreen gameScreen) {
        super(wordsAtlas.findRegion("new_game"));
        this.gameScreen = gameScreen;
    }

    @Override
    public void resize(Rect worldBounds) {
        setBottom(-0.13f);
        setHeightProportion(0.5f);
    }

    @Override
    public void action() {
        gameScreen.startNewGame();
    }
}
