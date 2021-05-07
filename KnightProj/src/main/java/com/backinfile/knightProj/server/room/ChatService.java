package com.backinfile.knightProj.server.room;

import com.backinfile.knightProj.core.Const;
import com.backinfile.mrpc.core.AutoStartUp;
import com.backinfile.mrpc.core.Params;
import com.backinfile.mrpc.core.Port;

@AutoStartUp(Const.GAME_NAME)
public class ChatService extends Port {

	public ChatService(String portId) {
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
	}

}
