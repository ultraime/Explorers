package com.ultraime.explorers.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.ultraime.explorers.ecran.EcranManager;
import com.ultraime.game.gdxtraime.parametrage.Parametre;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = LwjglApplicationConfiguration.getDesktopDisplayMode().width;
		config.height = LwjglApplicationConfiguration.getDesktopDisplayMode().height;
		config.fullscreen = false;
		config.resizable = false;
		config.width = 1280;// 1280
		config.height = 720;// 720

		Parametre.initEcran(config.width, config.height);
		new LwjglApplication(new EcranManager(), config);
	}
}
