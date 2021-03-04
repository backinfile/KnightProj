package com.backinfile.seam;

import com.backinfile.core.Const;
import com.backinfile.core.Log;
import com.backinfile.core.net.Connection;
import com.backinfile.support.DESUtils;
import com.backinfile.support.Utils2;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.Protocol;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;

public class GameClient {
	private volatile String host;
	private volatile int port;

	private Thread connectThread;
	private Socket socket;
	private Connection connection;

	private volatile boolean justConnected = false;
	private volatile boolean isAlive = false; // 设置为false进行开始关闭过程
	private boolean closed = true; // 真正关闭

	public GameClient() {
	}

	public void setAddr(String host, int port) {
		this.host = host;
		this.port = port;
	}

	public boolean setAddr(String addrCode) {
		String decodeStr = DESUtils.decrypt(Const.ADDRCODE_PASSWORD, addrCode);
		if (Utils2.isNullOrEmpty(decodeStr)) {
			return false;
		}
		String[] split = decodeStr.split(":");
		if (split.length != 2) {
			return false;
		}
		try {
			this.port = Integer.valueOf(split[1]);
			this.host = split[0];
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
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
		SocketHints hints = new SocketHints();
		hints.socketTimeout = Const.CLIENT_CONNECT_TIME;
		int reconnectTimes = 0;
		while (reconnectTimes++ < Const.CLIENT_RECONNECT_TIMES) {
			Log.net.info("connecting server..(the {0}th times)", reconnectTimes);
			socket = Gdx.net.newClientSocket(Protocol.TCP, host, port, hints);
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
				socket.dispose();
				socket = null;
			}
			closed = true;
			return;
		}

		if (justConnected) {
			justConnected = false;
			connection = new Connection(-1, socket);
			Log.game.info("connect server success addr:{0}", socket.getRemoteAddress());
		}

		if (connection != null) {
			if (connection.isAlive()) {
				connection.pulse();
			} else {
				isAlive = false;
			}
		}
	}

	public Connection getConnection() {
		return connection;
	}

	public void close() {
		isAlive = false;
	}

}
