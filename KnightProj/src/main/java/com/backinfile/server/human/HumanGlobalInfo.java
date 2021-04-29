package com.backinfile.server.human;

public class HumanGlobalInfo {
	public long id;
	public String roomPortId; // 所在房间

	public HumanGlobalInfo() {
	}
	public HumanGlobalInfo(HumanObject humanObject) {
		id = humanObject.getId();
		roomPortId = humanObject.getRoomPortId();
	}
}
