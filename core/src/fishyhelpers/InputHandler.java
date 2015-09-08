package fishyhelpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import gameobjects.Fish;
import gameworld.GameRenderer;
import gameworld.GameWorld;
import ui.MenuButtons;

/**
 * Created by Kevin on 8/17/2015.
 */


public class InputHandler implements InputProcessor {

    private Fish myFish;
    private OrthographicCamera camera;
    private int gameHeight;
    private int gameWidth;
    private GameRenderer renderer;
    private GameWorld world;
    private boolean hasBeenReleased;
    private MenuButtons menuButtons;

    private float scaleFactorX, scaleFactorY;


    public InputHandler(Fish fish, int gameHeight, int gameWidth, GameRenderer renderer, GameWorld world, float scaleFactorX, float scaleFactorY, MenuButtons menuButtons) {
        this.world = world;
        myFish = fish;
        this.gameHeight = gameHeight;
        this.gameWidth = gameWidth;
        this.renderer = renderer;
        this.scaleFactorX = scaleFactorX;
        this.scaleFactorY = scaleFactorY;
        this.menuButtons = menuButtons;
        hasBeenReleased = true;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        screenX = scaleX(screenX);
        screenY = scaleY(screenY);

        if (world.isMenu()||world.isGameOver()||world.isHighScore()) {
            menuButtons.playButton.isTouchDown(screenX, screenY);
        } else if (world.isReady()) {
            world.start();
        }

        if (world.isMenu()){
            menuButtons.creditsButton.isTouchDown(screenX,screenY);
        }

        if (world.isCredits()){
            world.menu();
        }


        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        screenX = scaleX(screenX);
        screenY = scaleY(screenY);

        if (world.isMenu()||world.isGameOver()||world.isHighScore()) {
            if (menuButtons.playButton.isTouchUp(screenX, screenY)) {
                world.restart();
                return true;
            }
        }

        if (world.isMenu()){
            if (menuButtons.creditsButton.isTouchUp(screenX,screenY)){
                world.credits();
                return true;
            }
        }

        myFish.onRelease();

        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {

        if (world.isReady()) {
            if (menuButtons.playButton.isTouchDown(screenX, screenY)) {
                world.start();
            }
        }

        Vector3 v = new Vector3(screenX, screenY, 0);
        v = renderer.getCamera().unproject(v);


        myFish.onClick((int) v.x, (int) v.y);

        return true;

    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    public int scaleX(int screenX) {
        return (int) (screenX / scaleFactorX);
    }

    public int scaleY(int screenY) {
        return (int) (screenY / scaleFactorY);
    }


}
