package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

import java.awt.Menu;

import fishyhelpers.AssetLoader;
import fishyhelpers.InputHandler;
import gameworld.GameRenderer;
import gameworld.GameWorld;
import ui.MenuButtons;

/**
 * Created by Kevin on 8/17/2015.
 */
public class GameScreen implements Screen {

    private GameWorld world;
    private GameRenderer renderer;
    private float runTime = 0;
    private MenuButtons menuButtons;
    private InputHandler input;

    public GameScreen() {
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        float gameWidth = 136;
        float gameHeight = screenHeight / (screenWidth / gameWidth);

        int midPointY = (int) (gameHeight / 2);
        int midPointX = (int) (gameWidth / 2);
        AssetLoader.ambience.play();
        world = new GameWorld(midPointX, midPointY);

        menuButtons = new MenuButtons(input, world);
        renderer = new GameRenderer(world, (int) gameHeight, (int) gameWidth, midPointX, midPointY, menuButtons, world.getEnemies());
        Gdx.input.setInputProcessor(input = new InputHandler(world.getFish(), (int) gameHeight, (int) gameWidth, renderer, world, screenWidth / gameWidth, screenHeight / gameHeight, menuButtons));


    }

    @Override
    public void render(float delta) {
        runTime += delta;
        world.update(delta);
        renderer.render(delta, runTime);


    }

    @Override
    public void resize(int width, int height) {
        Gdx.app.log("GameScreen", "resizing");

    }

    @Override
    public void show() {
        Gdx.app.log("GameScreen", "show called");

    }

    @Override
    public void hide() {
        Gdx.app.log("GameScreen", "hide called");

    }

    @Override
    public void pause() {
        Gdx.app.log("GameScreen", "pause called");
    }


    @Override
    public void resume() {
        Gdx.app.log("GameScreen", "resume called");
    }

    @Override
    public void dispose() {

    }

}
