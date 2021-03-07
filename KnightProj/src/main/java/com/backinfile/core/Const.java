package com.backinfile.core;

import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Const {
	public static final String GAME_NAME = "KnightProj";
	public static final String GAME_AUTHOR = "backinfile";

	public static final String WINDOW_TITLE = "KnightProj";

	public static final float WINDOW_RATE = 1080f / 1920f;
	public static final int WINDOW_WIDTH = (int) (LwjglApplicationConfiguration.getDesktopDisplayMode().width * 0.8f);
	public static final int WINDOW_HEIGHT = (int) (WINDOW_WIDTH * WINDOW_RATE);

	public static final String ADDRCODE_PASSWORD = "ASNK2789B";
	public static final int GAMESERVER_PORT = 9660;
	public static final int CLIENT_RECONNECT_TIMES = 5; // 连接重试次数
	public static final int CLIENT_CONNECT_TIME = 300; // 不活跃时间
	public static final String LOG_FILENAME = "./game.log";
}
