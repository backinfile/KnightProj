package com.backinfile.knightProj.server.room;

import com.backinfile.knightProj.core.Const;
import com.backinfile.mrpc.core.AutoStartUp;
import com.backinfile.mrpc.core.Params;

@AutoStartUp(Const.GAME_NAME)
public class ChatRoomService extends CommonRoomService<CommonRoom> {
	public static final String PORT_ID = ChatRoomService.class.getName();

	public ChatRoomService(String portId) {
		super(portId);
	}

	@Override
	public void startup() {
		room = new CommonRoom(this);
	}

	@Override
	public void pulse() {
	}

	@Override
	public void pulsePerSec() {
	}

	@Override
	public void handleRequest(int requestKey, Params param) {
		super.handleRequest(requestKey, param);
	}

}
