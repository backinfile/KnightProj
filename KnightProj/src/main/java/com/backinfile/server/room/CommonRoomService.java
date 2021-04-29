package com.backinfile.server.room;

import com.backinfile.core.GameMessage;
import com.backinfile.core.Log;
import com.backinfile.core.event.Event;
import com.backinfile.mrpc.core.Params;
import com.backinfile.mrpc.core.Port;
import com.backinfile.server.RequestKey;
import com.backinfile.server.human.HumanObject;

public abstract class CommonRoomService<T extends CommonRoom> extends Port {
	protected T room;

	public CommonRoomService(String portId) {
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
		case RequestKey.COMMON_ROOM_ENTER_ROOM:
			enterRoom(param.getValue("humanObject"));
			break;
		case RequestKey.COMMON_ROOM_LEAVE_ROOM:
			leaveRoom(param.getValue("id"));
			break;
		case RequestKey.COMMON_ROOM_HANDLE_MSG:
			handleMsg(param.getValue("id"), param.getValue("msg"));
			break;
		default:
			break;
		}
	}

	private void enterRoom(HumanObject humanObject) {
		room.register(humanObject);
	}

	private void leaveRoom(long id) {
		returns("humanObject", room.getHumanObject(id));
		room.unRegister(id);
	}

	private void handleMsg(long id, GameMessage msg) {
		HumanObject humanObject = room.getHumanObject(id);
		if (humanObject == null) {
			Log.game.error("no human handle msg id:{}", id);
			return;
		}
		Event.fireMessage(msg.getMessage(), null);
	}
}
