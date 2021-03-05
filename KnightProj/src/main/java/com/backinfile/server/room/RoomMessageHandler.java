package com.backinfile.server.room;

import com.backinfile.core.Log;
import com.backinfile.core.event.MsgHandler;
import com.backinfile.gen.pb.Msg.CSConnect;
import com.backinfile.gen.pb.Msg.SCConnect;

public class RoomMessageHandler {

	@MsgHandler
	public static void onEnterRoom(Room room, CSConnect msg) {
		Log.game.info("enter");
	}

	@MsgHandler
	public static void onEnterRoom(Room room, SCConnect msg) {
		Log.game.info("enter {}", msg.getCode());
	}
}
