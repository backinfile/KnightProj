package com.backinfile.knightProj.server.human;

import com.backinfile.knightProj.core.Log;
import com.backinfile.knightProj.core.event.MsgHandler;
import com.backinfile.knightProj.gen.pb.Msg.DChat;
import com.backinfile.mrpc.core.Params;

public class HumanManager {
	@MsgHandler
	public static void onChat(DChat msg, Params params) {
		HumanObject humanObject = params.getValue("humanObject");
		Log.game.info("msg:{} id:{}", msg.getContent(), humanObject.getId());
	}
}
