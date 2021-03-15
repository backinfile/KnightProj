package com.backinfile.server.room;

import com.backinfile.core.GameMessage;
import com.backinfile.core.net.Connection;
import com.backinfile.gen.pb.Msg.DPlayer;
import com.google.protobuf.Message;

public class Human {
	private String name;
	private Connection connection;

	public Human(String name, Connection connection) {
		this.name = name;
		this.connection = connection;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public long getId() {
		return connection.getId();
	}

	public void sendMsg(Message.Builder builder) {
		sendMsg(builder.build());
	}

	public void sendMsg(Message message) {
		if (connection != null) {
			connection.sendGameMessage(new GameMessage(message));
		}
	}

	public DPlayer.Builder toMsg() {
		DPlayer.Builder builder = DPlayer.newBuilder();
		return builder.setName(name).setId(connection.getId());
	}
}
