package com.backinfile.server.connect;

import java.util.HashMap;
import java.util.Map;

import com.backinfile.core.Const;
import com.backinfile.core.GameMessage;
import com.backinfile.core.Log;
import com.backinfile.core.net.Connection;
import com.backinfile.mrpc.core.AutoStartUp;
import com.backinfile.mrpc.core.Params;
import com.backinfile.mrpc.core.Port;
import com.backinfile.server.RequestKey;

@AutoStartUp(Const.GAME_NAME)
public class ConnectionGlobalService extends Port {
	public static final String PORT_NAME = ConnectionGlobalService.class.getName();

	private Map<Long, Connection> connections = new HashMap<>();

	public ConnectionGlobalService(String portId) {
		super(portId);
	}

	@Override
	public void startup() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pulse() {
		for (var conn : connections.values()) {
			conn.pulse();
		}
	}

	@Override
	public void pulsePerSec() {
		// TODO Auto-generated method stub

	}

	@Override
	public void handleRequest(int requestKey, Params param) {
		switch (requestKey) {
		case RequestKey.CONNECTION_GLOBAL_ADD_CONNECTION:
			addConnection(param.getValue("connection"));
			break;

		case RequestKey.CONNECTION_GLOBAL_SEND_MSG:
			sendMsg(param.getValue("id"), param.getValue("msg"));
			break;
		default:
			break;
		}
	}

	private void addConnection(Connection connection) {
		connections.put(connection.getId(), connection);
	}

	private void sendMsg(long connectionId, GameMessage gameMessage) {
		Connection conn = connections.get(connectionId);
		if (conn != null) {
			conn.sendGameMessage(gameMessage);
		} else {
			Log.core.warn("connection not found, sendMsg failed id:{}", connectionId);
		}
	}

}
