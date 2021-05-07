package com.backinfile.knightProj.server;

public class RequestKey {
	public static final int PLAYER_LOGIN = 1000;
	
	public static final int CONNECTION_GLOBAL_SEND_MSG = 1101;
	

	public static final int HUMAN_GLOBAL_HANDLE_MSG = 1201; // 分发处理来自玩家的消息
	
	public static final int COMMON_ROOM_ENTER_ROOM = 1301;
	public static final int COMMON_ROOM_LEAVE_ROOM = 1302;
	public static final int COMMON_ROOM_HANDLE_MSG = 1303;
}
