package com.kevin.fishy.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.kevin.fishy.FishyGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new FishyGame(), config);
		config.title = "Fishy";
		config.width = 272;
		config.height = 408;
		new LwjglApplication(new FishyGame(), config);
	}
}
