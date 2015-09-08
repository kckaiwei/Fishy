package com.kevin.fishy;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

import fishyhelpers.AssetLoader;
import screens.GameScreen;
import screens.SplashScreen;

public class FishyGame extends Game {

    @Override
    public void create() {
        AssetLoader.load();
        setScreen(new SplashScreen(this));

    }

    @Override
    public void dispose() {
        super.dispose();
        AssetLoader.dispose();
    }

}
