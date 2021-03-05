package com.backinfile.seam;

import java.awt.Dimension;
import java.awt.Toolkit;

import com.backinfile.core.Const;
import com.backinfile.core.Log;
import com.backinfile.core.ClientPref;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopClientLauncher {
	public static void main(String[] arg) {
//		Settings.Init();

		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = Const.WINDOW_TITLE;
		config.width = Const.WINDOW_WIDTH;
		config.height = Const.WINDOW_HEIGHT;
		config.resizable = false;
		new LwjglApplication(new ClientStartup(), config);

//		Gdx.app.postRunnable(() -> {
//			DisplayMode displayMode = Gdx.graphics.getDisplayMode();
//			Gdx.graphics.setWindowedMode((int) (displayMode.width * 0.8f), (int) (displayMode.height * 0.8f));
//		});
	}
}
