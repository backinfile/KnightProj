package com.backinfile.seam;

import com.backinfile.core.GameMessage;
import com.backinfile.core.event.Event;
import com.backinfile.core.net.GameServer;
import com.backinfile.core.serilize.SerializableManager;
import com.backinfile.support.Utils2;
import com.badlogic.gdx.utils.Json.Serializable;

public class ServerLauncher {
	public static void main(String[] arg) {
		GameMessage.collectAllMessage();
		Event.collectEventListener();
		SerializableManager.registerAll();

		GameServer gameServer = new GameServer();
		gameServer.start();
		while (gameServer.isAlive()) {
			gameServer.pulse();
			Utils2.sleep(1000);
		}
	}
}
