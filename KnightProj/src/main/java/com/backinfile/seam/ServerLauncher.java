package com.backinfile.seam;

import com.backinfile.core.Const;
import com.backinfile.core.GameMessage;
import com.backinfile.core.Log;
import com.backinfile.core.event.Event;
import com.backinfile.core.net.netty.Server;
import com.backinfile.core.serilize.SerializableManager;
import com.backinfile.mrpc.core.Node;
import com.backinfile.mrpc.core.ShutdownThread;

public class ServerLauncher {
	public static void main(String[] arg) {
		Log.game.info("server start prepare...");

		GameMessage.collectAllMessage(); // 消息注册
		Event.collectEventListener(); // 事件注册
		SerializableManager.registerAll(); // 序列化注册

		Node node = new Node("Test-Local");
		node.localStartUp("mrpc.test");

		Server server = new Server();
		server.start(Const.GAMESERVER_PORT);
		ShutdownThread hook = new ShutdownThread();
		Runtime.getRuntime().addShutdownHook(hook);
		hook.start();
	}
}
