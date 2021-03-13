package com.backinfile.seam;

import com.backinfile.client.ResourceManager;
import com.backinfile.core.Const;
import com.backinfile.core.GameMessage;
import com.backinfile.core.ClientPref;
import com.backinfile.core.event.Event;
import com.backinfile.support.Time2;
import com.backinfile.support.Utils2;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class ServerLauncher {
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

		new LwjglApplication(new ServerStartup(), config);

	}
}
