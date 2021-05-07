package com.backinfile.knightProj.server.human;

import java.util.HashMap;
import java.util.Map;

import com.backinfile.knightProj.core.Const;
import com.backinfile.knightProj.core.GameMessage;
import com.backinfile.knightProj.core.Log;
import com.backinfile.knightProj.core.event.Event;
import com.backinfile.knightProj.core.event.EventKey;
import com.backinfile.knightProj.gen.pb.Msg.CSConnect;
import com.backinfile.knightProj.gen.pb.Msg.SCConnect;
import com.backinfile.knightProj.server.RequestKey;
import com.backinfile.knightProj.server.room.ChatRoomService;
import com.backinfile.mrpc.core.AutoStartUp;
import com.backinfile.mrpc.core.Params;
import com.backinfile.mrpc.core.Port;
import com.backinfile.mrpc.core.Proxy;

@AutoStartUp(Const.GAME_NAME)
public class HumanGlobalService extends Port {
	public static final String PORT_NAME = HumanGlobalService.class.getName();

	private Map<Long, HumanGlobalInfo> infos = new HashMap<>();

	public HumanGlobalService(String portId) {
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
		case RequestKey.HUMAN_GLOBAL_HANDLE_MSG:
			handleMsg(param.getValue("id"), param.getValue("msg"));
			break;

		default:
			break;
		}
	}

	private void handleMsg(long id, GameMessage msg) {
		if (infos.containsKey(id)) { // 已经登陆过了
			Proxy.request(infos.get(id).roomPortId, RequestKey.COMMON_ROOM_HANDLE_MSG,
					new Params("id", id, "msg", msg));
		} else if (msg.getMessage() instanceof CSConnect) {
			var connectMsg = (CSConnect) msg.getMessage();
			// 创建玩家Global信息
			HumanGlobalInfo info = new HumanGlobalInfo();
			info.id = id;
			info.roomPortId = ChatRoomService.PORT_ID;
			infos.put(id, info);

			// 创建HumanObject
			HumanObject humanObject = new HumanObject(id, connectMsg.getName());

			// 进入地图
			Params params = new Params("humanObject", humanObject);
			Proxy.request(info.roomPortId, RequestKey.COMMON_ROOM_ENTER_ROOM, params);

			// 返回消息
			humanObject.sendMsg(SCConnect.newBuilder().setCode(0));
			Log.game.info("human login id:{} name:{}", id, connectMsg.getName());
		} else {
			Log.game.warn("human no login id:{}", id);
		}
	}

}
