package com.backinfile.knightProj.core.net;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import com.backinfile.knightProj.core.Const;
import com.backinfile.knightProj.core.GameMessage;
import com.backinfile.knightProj.core.Log;
import com.backinfile.knightProj.gen.pb.Msg.SCGetRoomInfo;
import com.backinfile.knightProj.support.Utils2;

public class GameClient {
	private volatile String remoteIp;
	private volatile int port;

	private Thread connectThread;
	private Socket socket;
	private SocketConnection connection;

	private volatile boolean justConnected = false;
	private volatile boolean isAlive = false; // 设置为false进行开始关闭过程
	private boolean closed = false; // 真正关闭

	public GameClient() {
	}

	public void setAddr(String host, int port) {
		this.remoteIp = host;
		this.port = port;
	}

	public void start() {
		Log.game.info("GameClient start");
		connectThread = new Thread(this::tryConnect);
		connectThread.setName("Thread-ClientConnect");
		connectThread.start();

		isAlive = true;
		closed = false;
	}

	public void tryConnect() {
		int reconnectTimes = 0;
		while (reconnectTimes++ < Const.CLIENT_RECONNECT_TIMES) {
			Log.net.info("connecting server..(the {}th times)", reconnectTimes);
			try {
				if (socket == null) {
					socket = new Socket();
				}
				socket.connect(new InetSocketAddress(remoteIp, port), 1000);
				socket.setKeepAlive(true);
				
			} catch (IOException e) {
				Log.client.error("error in connect server: {}", e.getMessage());
				socket = null;
			}
			if (socket != null && socket.isConnected()) {
				justConnected = true;
				Log.net.info("connect server success");
				return;
			}
		}
		isAlive = false;
	}

	public boolean isAlive() {
		return isAlive;
	}

	public void pulse() {
		// 检查当前socket是否已经关闭
		if (!closed && socket != null && socket.isClosed()) {
			isAlive = false;
		}
		
		if (!isAlive && !closed) {
			if (connectThread != null) {
				try {
					connectThread.join();
				} catch (InterruptedException e) {
					Log.game.error("error on close client", e);
				}
				connectThread = null;
			}
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException e) {
					Log.client.error("error in close socket", e);
				}
				socket = null;
			}
			closed = true;
			Log.client.error("GameClient closed");
			return;
		}

		if (justConnected) {
			justConnected = false;
			connection = new SocketConnection(-1, socket);
			Log.game.info("connect server success addr:{}", socket.getRemoteSocketAddress());
		}

		if (connection != null) {
			if (connection.isAlive()) {
				connection.pulse();
			} else {
				isAlive = false;
			}
		}
	}

	public SocketConnection getConnection() {
		return connection;
	}

	public void close() {
		isAlive = false;
	}

	private static int count = 0;

	public static void main(String[] args) {
		GameMessage.collectAllMessage();
		GameClient gameClient = new GameClient();
		gameClient.setAddr("", Const.GAMESERVER_PORT);
		gameClient.start();
		while (gameClient.isAlive()) {
			gameClient.pulse();
			if (count++ == 5) {
				gameClient.connection.sendGameMessage(new GameMessage(SCGetRoomInfo.newBuilder().build()));
			}
			Utils2.sleep(1000);
		}
		gameClient.close();
	}
}
