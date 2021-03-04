package com.backinfile.KnightProj;

import java.awt.Dimension;
import java.awt.Toolkit;

import com.backinfile.core.Const;
import com.backinfile.core.Log;
import com.backinfile.core.Settings;
import com.backinfile.seam.GameStartup;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {
	public static void main(String[] arg) {
//		Settings.Init();

		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		DisplayMode desktopDisplayMode = LwjglApplicationConfiguration.getDesktopDisplayMode();
		config.title = Const.WINDOW_TITLE;
//		config.width = (int) (desktopDisplayMode.width * 0.7f);
//		config.height = (int) (desktopDisplayMode.height * 0.7f);
		config.width = 1200;
		config.height = 860;
		config.resizable = false;
//		config.addIcon("favicon.ico", FileType.Internal);

		new LwjglApplication(new GameStartup(), config);

//		Gdx.app.postRunnable(() -> {
//			DisplayMode displayMode = Gdx.graphics.getDisplayMode();
//			Gdx.graphics.setWindowedMode((int) (displayMode.width * 0.8f), (int) (displayMode.height * 0.8f));
//		});
	}
}
