package com.backinfile.knightProj.seam;

import java.util.Scanner;

import org.apache.logging.log4j.Level;

import com.backinfile.knightProj.core.Const;
import com.backinfile.knightProj.core.GameMessage;
import com.backinfile.knightProj.core.Log;
import com.backinfile.knightProj.core.event.Event;
import com.backinfile.knightProj.core.net.netty.Client;
import com.backinfile.knightProj.gen.pb.Msg.DChat;
import com.backinfile.mrpc.serilize.SerializableManager;
import com.google.protobuf.Message;

public class CommandlineClientLauncher {
	public static void main(String[] args) {
		Log.setLevel(Level.ERROR);

		Log.game.info("消息注册中...");
		GameMessage.collectAllMessage(); // 消息注册

		Log.game.info("事件注册中...");
		Event.collectEventListener(); // 事件注册

		Log.game.info("序列化注册中...");
		SerializableManager.registerAll(Const.PACKAGE_NAME); // 序列化注册

		Log.game.info("客户端启动中...");
		Client client = new Client();
		client.start();

		Scanner scanner = new Scanner(System.in);
		while (true) {
			String msg = scanner.next();
			if (msg.equals("exit")) {
				break;
			} else {
				sendMsg(DChat.newBuilder().setContent(msg));

			}
		}
		scanner.close();
	}

	private static void sendMsg(Message message) {
		GameMessage gameMessage = new GameMessage(message);
		Client.Channel.writeAndFlush(gameMessage.getBytes());
	}

	private static void sendMsg(Message.Builder builder) {
		sendMsg(builder.build());
	}
}
