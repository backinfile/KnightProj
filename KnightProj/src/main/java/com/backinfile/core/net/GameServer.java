package com.backinfile.core.net;

import com.backinfile.core.Const;
import com.backinfile.core.GameMessage;
import com.backinfile.core.Log;
import com.backinfile.core.event.Event;
import com.backinfile.core.net.netty.Server;
import com.backinfile.support.Utils2;

public class GameServer {
	public static GameServer Instance;

	// 维护客户端连接
	private ConnectionMaintainer connectionMaintainer;
	private Server server;

	private volatile boolean isAlive = false; // 设置为false进行开始关闭过程
	private boolean closed = false; // 真正关闭

	public GameServer() {
		Instance = this;
	}

	public void start() {
		Log.game.info("GameServer start");
		server = new Server();
		try {
			server.startServer(Const.GAMESERVER_PORT);
		} catch (Exception e) {
			isAlive = false;
			return;
		}
		connectionMaintainer = new ConnectionMaintainer();
		connectionMaintainer.startUp();

		isAlive = true;
		closed = false;
	}

	public void pulse() {
		if (!isAlive && !closed) {
			closed = true;
			if (connectionMaintainer != null) {
				connectionMaintainer.abort();
			}
			Log.game.info("GameServer closed");
		}
	}

	public void close() {
		Log.game.info("GameServer closing");
		isAlive = false;
	}

	public boolean isAlive() {
		return isAlive;
	}

	public void addConnection(ChannelConnection connection) {
		connectionMaintainer.addConnection(connection);
	}

	public ChannelConnection getConnection(long id) {
		return (ChannelConnection) connectionMaintainer.getConnection(id);
	}

	/**
	 * 发送消息到客户端
	 * 
	 * @param id
	 * @param gameMessage
	 */
	public void sendGameMessage(long id, GameMessage gameMessage) {
		Connection connection = connectionMaintainer.getConnection(id);
		if (connection != null) {
			connection.sendGameMessage(gameMessage);
		} else {
			Log.server.debug("connection disconnect, message miss id:{} msg:{}", id, gameMessage.toString());
		}
	}

	/**
	 * 接受到客户端的消息
	 */
	public void onReceiveGameMessage(long id, GameMessage gameMessage) {
		// TODO
		Log.net.info("onReceiveGameMessage {} got {}", id, gameMessage.toString());
	}

	public static void main(String[] args) {
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
