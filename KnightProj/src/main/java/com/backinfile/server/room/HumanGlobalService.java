package com.backinfile.server.room;

import com.backinfile.core.GameMessage;
import com.backinfile.core.Service;
import com.backinfile.core.net.Connection;
import com.backinfile.gen.pb.Msg.CSConnect;
import com.backinfile.gen.pb.Msg.CSPing;
import com.backinfile.gen.pb.Msg.SCPing;
import com.backinfile.support.Time2;
import com.google.protobuf.Message;

public class HumanGlobalService extends Service {
	private static final HumanGlobalService Instance = new HumanGlobalService();

	public static HumanGlobalService getInstance() {
		return Instance;
	}

	private Room room = new Room();

	public void onMessage(Connection connection, Message message) {
		if (message instanceof CSPing) {
			onPing(connection);
		} else if (message instanceof CSConnect) {
			onConnect(connection, ((CSConnect) message).getName());
		}
	}

	private void onPing(Connection connection) {
		connection.sendGameMessage(new GameMessage(SCPing.newBuilder().setTime(Time2.getCurrentTimestamp())));
	}

	private void onConnect(Connection connection, String name) {
		if (!room.contains(connection.getId())) {
			Human human = new Human(name, connection);
			room.addHuman(human);
		} else {
			room.rename(connection.getId(), name);
		}
	}

	@Override
	public void startup() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pulse() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pulsePerSec() {
		// TODO Auto-generated method stub

	}

}
