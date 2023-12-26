package com.gdx.game.screens;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gdx.game.Grotto;
import com.gdx.game.scenes.Hud;

public class PlayScreen implements Screen {
    final Grotto game;
    private OrthographicCamera gameCam;
    private Viewport viewport;

    private Hud hud;

    public PlayScreen(final Grotto game) {
        this.game = game;

        gameCam = new OrthographicCamera();
        viewport = new FitViewport(Grotto.V_WIDTH, Grotto.V_HEIGHT, gameCam);
        hud = new Hud(game.batch, game.font);
    }

    @Override
    public void render(float delta) {
        // clear to black
        ScreenUtils.clear(0, 0, 0, 1);

        // tell the SpriteBatch to render in the
        // coordinate system specified by the camera.
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
        // begin a new batch and draw the bucket and
        // all drops
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void show() {
        // start the playback of the background music
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }
}
