package com.backinfile.core.net;

import com.backinfile.core.Const;
import com.backinfile.core.Log;
import com.backinfile.server.Server;

public class GameServer {
	public static GameServer Instance;

	private Thread serverThread;
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
		serverThread = new Thread(this::run);
		serverThread.setDaemon(true);
		serverThread.start();

		isAlive = true;
		closed = false;
	}

	public void run() {
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

	public static void main(String[] args) {
		GameServer gameServer = new GameServer();
		gameServer.start();
	}
}