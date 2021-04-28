package com.backinfile.server.room;

import com.backinfile.core.Const;
import com.backinfile.core.Log;
import com.backinfile.mrpc.core.AutoStartUp;
import com.backinfile.mrpc.core.Params;
import com.backinfile.mrpc.core.Port;
import com.backinfile.server.RequestKey;
import com.backinfile.server.player.Player;

@AutoStartUp(Const.GAME_NAME)
public class ChatRoom extends Port {
	public ChatRoom(String portId) {
		super(portId);
	}

	@Override
	public void startup() {
	}

	@Override
	public void pulse() {
	}

	@Override
	public void pulsePerSec() {
	}

	@Override
	public void handleRequest(int requestKey, Params param) {
		switch (requestKey) {
		case RequestKey.PLAYER_LOGIN:
			login(param.getValue("player"));
			break;

		default:
			break;
		}
	}

	public void login(Player player) {
		
		// TODO
		Log.chatRoom.info("{} login", player.getName());
	}

}
