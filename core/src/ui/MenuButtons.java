package ui;

import com.badlogic.gdx.Input;

import java.util.ArrayList;
import java.util.List;

import fishyhelpers.AssetLoader;
import fishyhelpers.InputHandler;
import gameworld.GameWorld;

/**
 * Created by Kevin on 8/21/2015.
 */
public class MenuButtons {

    public List<SimpleButton> menuButtons;
    public SimpleButton playButton, creditsButton;
    public List<SimpleButton> replayButtons;


    public MenuButtons(InputHandler inputHandler, GameWorld world) {

        menuButtons = new ArrayList<SimpleButton>();
        replayButtons = new ArrayList<SimpleButton>();
        playButton = new SimpleButton(((136 / 2) - 7), (world.getMidPointY()) + 5, 14, 8, AssetLoader.playButtonUp, AssetLoader.playButtonDown);
        creditsButton = new SimpleButton(((136/2)-10), (world.getMidPointY())+15 , 20,8, AssetLoader.creditsButtonUp, AssetLoader.creditsButtonDown);
        menuButtons.add(playButton);
        menuButtons.add(creditsButton);
        replayButtons.add(playButton);
    }

    public List<SimpleButton> getMenuButtons() {
        return menuButtons;
    }
    public List<SimpleButton> getReplayButtons() {return replayButtons;}
}
