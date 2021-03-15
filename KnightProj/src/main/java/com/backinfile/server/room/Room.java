package com.backinfile.server.room;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import com.backinfile.gen.pb.Msg.DChat;
import com.backinfile.gen.pb.Msg.DRoom;
import com.backinfile.gen.pb.Msg.SCRoomUpdate;

public class Room {
	private final HashMap<Long, Human> humanMap = new HashMap<>();
	private final Queue<DChat> chatQueue = new LinkedList<>();

	private static final int CHAT_SAVE_NUMBER = 10;

	public Room() {

	}

	public void addHuman(Human human) {
		humanMap.put(human.getId(), human);
		sendRoomUpdateMsg();
	}

	public void rename(long id, String newName) {
		Human human = humanMap.get(id);
		if (human == null) {
			return;
		}
		human.setName(newName);
		sendRoomUpdateMsg();
	}

	private void sendRoomUpdateMsg() {
		SCRoomUpdate msg = SCRoomUpdate.newBuilder().setRoom(getDRoom()).build();
		for (Human human : humanMap.values()) {
			human.sendMsg(msg);
		}
	}

	private DRoom.Builder getDRoom() {
		DRoom.Builder builder = DRoom.newBuilder();
		for (Human human : humanMap.values()) {
			builder.addPlayers(human.toMsg());
		}
		for (DChat chat : chatQueue) {
			builder.addLastestChats(chat);
		}
		return builder;
	}

	public Human getHuman(long id) {
		return humanMap.get(id);
	}

	public boolean contains(long id) {
		return humanMap.containsKey(id);
	}
}
