package com.backinfile.knightProj.server.human;

import com.backinfile.knightProj.core.GameMessage;
import com.backinfile.knightProj.server.RequestKey;
import com.backinfile.knightProj.server.connect.ConnectionGlobalService;
import com.backinfile.knightProj.server.room.CommonRoom;
import com.backinfile.mrpc.core.Params;
import com.backinfile.mrpc.core.Proxy;
import com.backinfile.mrpc.serilize.ISerializable;
import com.backinfile.mrpc.serilize.InputStream;
import com.backinfile.mrpc.serilize.OutputStream;
import com.google.protobuf.Message;

public class HumanObject implements ISerializable {
	private long id;
	private String name = "null";
	private String roomPortId;
	private CommonRoom room; // 所在房间

	public HumanObject() {
	}

	public HumanObject(long id, String name) {
		this.id = id;
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setRoomPortId(String roomPortId) {
		this.roomPortId = roomPortId;
	}

	public String getRoomPortId() {
		return roomPortId;
	}

	public void setRoom(CommonRoom room) {
		this.room = room;
	}

	public CommonRoom getRoom() {
		return room;
	}

	public void sendMsg(GameMessage gameMessage) {
		Params params = new Params("id", id, "msg", gameMessage);
		Proxy.request(ConnectionGlobalService.PORT_NAME, RequestKey.CONNECTION_GLOBAL_SEND_MSG, params);
	}

	public void sendMsg(Message message) {
		sendMsg(new GameMessage(message));
	}

	public void sendMsg(Message.Builder builder) {
		sendMsg(new GameMessage(builder.build()));
	}

	@Override
	public void writeTo(OutputStream out) {
		out.write(id);
		out.write(name);
	}

	@Override
	public void readFrom(InputStream in) {
		id = in.read();
		name = in.read();
	}

	public void pulse() {
	}

}
