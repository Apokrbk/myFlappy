package com.apok.game.myflappy.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.apok.game.myflappy.myFlappy;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = myFlappy.WIDTH;
		config.height = myFlappy.HEIGHT;
		config.title = myFlappy.TITLE;
		new LwjglApplication(new myFlappy(new DesktopDatabase()), config);
	}
}
