package com.backinfile.server.room;

import com.backinfile.core.GameMessage;
import com.backinfile.core.event.RoomEvent;

public class Room {

	public Room() {

	}

	public void onReceiveMessage(GameMessage gameMsg) {
		RoomEvent.fire(this, gameMsg.getMessage());
	}
}
