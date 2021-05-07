package com.backinfile.knightProj.seam;

import com.backinfile.knightProj.core.Const;
import com.backinfile.knightProj.core.GameMessage;
import com.backinfile.knightProj.core.Log;
import com.backinfile.knightProj.core.event.Event;
import com.backinfile.knightProj.core.net.netty.Server;
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
		SerializableManager.registerAll(Const.PACKAGE_NAME); // 序列化注册
		SerializableManager.registerAll(Const.CORE_PACKAGE_NAME); // 序列化注册

		Log.game.info("服务启动中...");
		Node node = new Node(Const.GAME_NAME);
		node.localStartUp(new String[] {Const.PACKAGE_NAME}, new String[] {Const.GAME_NAME});

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
