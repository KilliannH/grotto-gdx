package com.gdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gdx.game.Grotto;
import com.gdx.game.scenes.Hud;
import com.gdx.game.sprites.Hero;

public class PlayScreen implements Screen {
    final Grotto game;
    private OrthographicCamera gameCam;
    private Viewport viewport;
    private Hud hud;

    // Tiled map variables
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;

    // Box2d variables
    private World world;
    private Box2DDebugRenderer b2dr;

    private Hero player;


    public PlayScreen(final Grotto game) {
        this.game = game;

        gameCam = new OrthographicCamera();
        viewport = new FitViewport(Grotto.V_WIDTH / Grotto.PPM, Grotto.V_HEIGHT / Grotto.PPM, gameCam);
        hud = new Hud(game.batch, game.font);

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("lvl1.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map, 1 / Grotto.PPM);
        gameCam.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);

        world = new World(new Vector2(0, -10), true);
        b2dr = new Box2DDebugRenderer();

        player = new Hero(world);

        // will move in another class later
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        // create ground bodies/fixtures
        for(RectangleMapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = object.getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / Grotto.PPM, (rect.getY() + rect.getHeight() / 2) / Grotto.PPM);

            body = world.createBody(bdef);
            shape.setAsBox((rect.getWidth()  / 2) / Grotto.PPM, (rect.getHeight() / 2) / Grotto.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }
    }

    @Override
    public void render(float delta) {
        // separate our update logic from render
        update(delta);

        // clear the game screen with black
        ScreenUtils.clear(0, 0, 0, 1);

        // render our game map
        mapRenderer.render();

        // render our Box2DDebugLines
        b2dr.render(world, gameCam.combined);

        // Set our batch to now draw what the Hud camera sees.
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void show() {
        // start the playback of the background music
    }

    public void handleInput(float dt) {
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            player.b2body.applyLinearImpulse(new Vector2(0, 3f), player.b2body.getWorldCenter(), true);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <= 2) {
            player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x >= -2) {
            player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);
        }
    }

    public void update(float dt) {
        handleInput(dt);

        world.step(1/60f, 6, 2);

        gameCam.position.x = player.b2body.getPosition().x;

        gameCam.update();
        mapRenderer.setView(gameCam);
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
