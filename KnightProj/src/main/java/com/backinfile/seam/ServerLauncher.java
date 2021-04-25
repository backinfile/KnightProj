package com.backinfile.seam;

import com.backinfile.core.GameMessage;
import com.backinfile.core.event.Event;
import com.backinfile.core.net.GameServer;
import com.backinfile.support.Utils2;

public class ServerLauncher {
	public static void main(String[] arg) {
		GameMessage.collectAllMessage();
		Event.collectEventListener();
		GameMessage.collectAllMessage();
		Event.collectEventListener();
		GameServer gameServer = new GameServer();
		gameServer.start();
		while (gameServer.isAlive()) {
			gameServer.pulse();
			Utils2.sleep(1000);
		}
	}
}
