package com.backinfile.knightProj.server.room;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.backinfile.knightProj.server.human.HumanObject;
import com.backinfile.mrpc.core.Port;
import com.backinfile.mrpc.timer.TimerQueue;
import com.backinfile.mrpc.utils.Time2;

// 房间（玩家集合）
public class CommonRoom {
	private Map<Long, HumanObject> humanMap = new HashMap<>();
	private Port port;

	// 延迟到一帧的最后执行
	private List<HumanObject> enterHumans = new ArrayList<>();
	private List<HumanObject> leaveHumans = new ArrayList<>();

	private long lastPulseSecTime;

	public CommonRoom(Port port) {
		this.port = port;
	}

	/**
	 * 注册进入房间
	 * 
	 * @param humanObject
	 */
	public final void register(HumanObject humanObject) {
		enterHumans.add(humanObject);
	}

	/**
	 * 离开房间
	 * 
	 * @param humanId
	 * @return
	 */
	public final void unRegister(long humanId) {
		HumanObject humanObject = humanMap.getOrDefault(humanId, null);
		if (humanObject != null) {
			leaveHumans.add(humanObject);
		}
	}

	public final HumanObject getHumanObject(long id) {
		return humanMap.getOrDefault(id, null);
	}

	public final void roomPulse() {
		// 玩家心跳
		for (var humanObject : humanMap.values()) {
			humanObject.pulse();
		}

		// 地图心跳
		pulse();
		long currentTimestamp = Time2.getCurrentTimestamp();
		if (lastPulseSecTime == 0 || currentTimestamp > lastPulseSecTime) {
			lastPulseSecTime = currentTimestamp + Time2.SECOND;
			pulsePerSec();
		}

		// 统一添加/删除
		for (var humanObject : enterHumans) {
			humanMap.put(humanObject.getId(), humanObject);
			onHumanEnterRoom(humanObject);
		}
		enterHumans.clear();
		for (var humanObject : leaveHumans) {
			if (humanObject != null) {
				onHumanLeaveRoom(humanObject);
			}
			humanObject.setRoom(null);
			humanObject.setRoomPortId(port.getPortId());
			humanMap.remove(humanObject.getId());
		}
		leaveHumans.clear();
	}

	public TimerQueue geTimerQueue() {
		return port.getTimerQueue();
	}

	// for override
	public void pulse() {

	}

	// for override
	public void pulsePerSec() {

	}

	// for override
	public void onHumanEnterRoom(HumanObject humanObject) {
	}

	// for override
	public void onHumanLeaveRoom(HumanObject humanObject) {
	}
}
