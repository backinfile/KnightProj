package com.backinfile.seam;

import com.backinfile.core.Const;
import com.backinfile.core.Log;
import com.backinfile.core.SysException;
import com.backinfile.core.net.Connection;
import com.backinfile.core.net.ConnectionMaintainer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.Protocol;
import com.badlogic.gdx.net.ServerSocket;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;

public class GameServer {

	private Thread serverThread;
	private ServerSocket serverSocket;
	// 维护客户端连接
	private ConnectionMaintainer connectionMaintainer;

	private volatile long connectionIdMax = 0;
	private volatile boolean isAlive = false; // 设置为false进行开始关闭过程
	private boolean closed = false; // 真正关闭

	public GameServer() {

	}

	public void start() {
		Log.game.info("GameServer start");
		Log.game.error("GameServer start", new SysException());
		try {
			serverSocket = Gdx.net.newServerSocket(Protocol.TCP, Const.GAMESERVER_PORT, null);
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
		connectionIdMax = 0;
	}

	public void run() {
		SocketHints hints = new SocketHints();
		while (isAlive) {
			pulse();
			Socket socket;
			try {
				socket = serverSocket.accept(hints);
			} catch (Exception e) {
				continue;
			}
			if (socket != null) {
				connectionMaintainer.addConnnect(new Connection(connectionIdMax++, socket));
				Log.game.info("new connection addr:{0}", socket.getRemoteAddress());
			}
		}
	}

	public void pulse() {
		if (!isAlive && !closed) {
			closed = true;
			if (connectionMaintainer != null) {
				connectionMaintainer.abort();
			}
			if (serverSocket != null) {
				serverSocket.dispose();
				try {
					serverThread.join();
				} catch (InterruptedException e) {
					Log.game.error("error on GameServer close", e);
				}
				serverSocket = null;
				serverThread = null;
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
}
