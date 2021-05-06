package com.backinfile.core.net;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

import com.backinfile.core.GameMessage;
import com.backinfile.core.Log;
import com.backinfile.mrpc.core.Params;
import com.backinfile.mrpc.core.Proxy;
import com.backinfile.server.RequestKey;
import com.backinfile.server.human.HumanGlobalService;
import com.backinfile.support.Time2;
import com.backinfile.support.Utils2;

import io.netty.channel.Channel;

public class ChannelConnection implements Delayed, Connection {
	public static final String TAG = ChannelConnection.class.getSimpleName();

	private Channel channel;
	private ConcurrentLinkedQueue<GameMessage> sendList = new ConcurrentLinkedQueue<GameMessage>();
	private ConcurrentLinkedQueue<byte[]> inputList = new ConcurrentLinkedQueue<>();
	private long time;
	public static final int HZ = 1;

	public String name;
	private long id;

	public ChannelConnection(long id, Channel channel) {
		this.channel = channel;
		this.id = id;
	}

	@Override
	public long getId() {
		return id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public GameMessage getGameMessage() {
		return null;
	}

	public void sendGameMessage(GameMessage gameMessage) {
		sendList.add(gameMessage);
	}

	public void pulse() {
		time = Time2.getCurrentTimestamp();
//		Log.net.debug("pulse");

		if (!isAlive())
			return;

		pulseSend();
		pulseInput();
	}

	private void pulseSend() {
		while (!sendList.isEmpty()) {
			GameMessage sendMsg = sendList.poll();
			channel.writeAndFlush(sendMsg.getBytes());
			Log.net.info("Connection {} send {}", this.toString(), sendMsg.toString());
		}
	}

	private void pulseInput() {
		while (!inputList.isEmpty()) {
			byte[] data = inputList.poll();
			if (data == null)
				break;
			GameMessage gameMessage = GameMessage.buildGameMessage(data, 0, data.length);
			if (gameMessage != null) {
				Proxy.request(HumanGlobalService.PORT_NAME, RequestKey.HUMAN_GLOBAL_HANDLE_MSG,
						new Params("id", getId(), "msg", gameMessage));
			}
		}
	}

	/**
	 * 添加输入
	 * 
	 * @param data
	 */
	public void addInput(byte[] data) {
		inputList.add(data);
	}

	public boolean isAlive() {
		return channel.isActive();
	}

	@Override
	public String toString() {
		if (Utils2.isNullOrEmpty(name)) {
			return String.valueOf(id);
		}
		return name + "(" + id + ")";
	}

	@Override
	public int compareTo(Delayed o) {
		ChannelConnection connection = (ChannelConnection) o;
		return Long.compare(time, connection.time);
	}

	@Override
	public long getDelay(TimeUnit unit) {
		return time + 1000 / HZ - Time2.getCurrentTimestamp();
	}

	public void close() {
		channel.close();
	}

}
