package com.backinfile.seam;

import com.backinfile.core.Const;
import com.backinfile.core.GameMessage;
import com.backinfile.core.Log;
import com.backinfile.core.event.Event;
import com.backinfile.core.net.netty.Server;
import com.backinfile.mrpc.core.Node;
import com.backinfile.mrpc.core.ShutdownThread;
import com.backinfile.mrpc.serilize.SerializableManager;

public class ServerLauncher {
	public static void main(String[] arg) {
		Log.game.info("server start prepare...");

		Log.game.info("消息注册中...");
		GameMessage.collectAllMessage(); // 消息注册

		Log.game.info("事件注册中...");
		Event.collectEventListener(); // 事件注册

		Log.game.info("序列化注册中...");
		SerializableManager.registerAll(); // 序列化注册

		Log.game.info("服务启动中...");
		Node node = new Node(Const.GAME_NAME);
		node.localStartUp(Const.GAME_NAME);

		Log.game.info("监听启动中...");
		Server server = new Server();
		server.start(Const.GAMESERVER_PORT);

		Log.game.info("------------------");
		Log.game.info("启动完成");
		Log.game.info("------------------");

		ShutdownThread hook = new ShutdownThread();
		Runtime.getRuntime().addShutdownHook(hook);
		hook.start();
	}
}
