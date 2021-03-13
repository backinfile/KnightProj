package com.backinfile.server.room;

import com.backinfile.core.Log;
import com.backinfile.core.event.EventKey;
import com.backinfile.core.event.EventListener;
import com.backinfile.core.event.MsgHandler;
import com.backinfile.gen.pb.Msg.CSConnect;
import com.backinfile.gen.pb.Msg.SCConnect;
import com.backinfile.support.Param;

public class RoomMessageHandler {

	@MsgHandler
	public static void onEnterRoom(CSConnect msg, Param param) {
		Log.game.info("enter");
	}

	@MsgHandler
	public static void onEnterRoom(SCConnect msg, Param param) {
		Log.game.info("enter {}", msg.getCode());
	}

	@EventListener(EventKey.GAME_START)
	public static void onGameStart(Param param) {
		String value = param.get("qwe");
		Log.game.info("enter event GAME_START, {}", value);
	}
}
