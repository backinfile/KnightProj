package com.backinfile.core.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.backinfile.core.GameMessage;
import com.backinfile.core.Log;
import com.backinfile.support.Utils2;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.net.Socket;

public class Connection {
	public static final String TAG = Connection.class.getSimpleName();

	private Socket socket;
	private ConcurrentLinkedQueue<GameMessage> sendList = new ConcurrentLinkedQueue<GameMessage>();
	private ConcurrentLinkedQueue<GameMessage> reciveList = new ConcurrentLinkedQueue<GameMessage>();
	private InputStream inputStream;
	private OutputStream outputStream;

	public String name;
	public long id;

	private final byte[] readBytes = new byte[1024];

	public Connection(long id, Socket socket) {
		this.socket = socket;
		this.inputStream = socket.getInputStream();
		this.outputStream = socket.getOutputStream();
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public GameMessage getGameMessage() {
		return reciveList.poll();
	}

	public ConcurrentLinkedQueue<GameMessage> getReciveList() {
		return reciveList;
	}

	public void sendGameMessage(GameMessage gameMessage) {
		sendList.add(gameMessage);
	}

	public void pulse() {
		if (!socket.isConnected()) {
			return;
		}
		try {
			while (!sendList.isEmpty()) {
				GameMessage sendMsg = sendList.poll();
				outputStream.write(sendMsg.getBytes(), 0, sendMsg.getLength());
				outputStream.flush();
				Log.net.info("Connection {0} send {1}", this.toString(), sendMsg.toString());
			}
			while (inputStream.available() > 0) {
				int len = inputStream.read(readBytes);
				GameMessage gameMessage = GameMessage.buildGameMessage(readBytes, 0, len);
				if (gameMessage != null) {
					reciveList.add(gameMessage);
					Log.net.info("Connection {0} got {1}", this.toString(), gameMessage.toString());
				}
			}
		} catch (IOException e) {
			Gdx.app.log(TAG, e.getMessage(), e);
		}
	}

	public boolean isAlive() {
		return socket.isConnected();
	}

	@Override
	public String toString() {
		if (Utils2.isNullOrEmpty(name)) {
			return String.valueOf(id);
		}
		return name + "(" + id + ")";
	}
}
