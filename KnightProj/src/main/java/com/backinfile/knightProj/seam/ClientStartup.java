package com.backinfile.knightProj.seam;

import com.backinfile.knightProj.client.ResourceManager;
import com.backinfile.knightProj.client.screen.GameScreen;
import com.backinfile.knightProj.client.screen.TestScreen;
import com.backinfile.knightProj.core.ClientPref;
import com.backinfile.knightProj.core.GameMessage;
import com.backinfile.knightProj.core.Log;
import com.backinfile.knightProj.core.event.Event;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public class ClientStartup extends Game {

	@Override
	public void create() {
		Log.game.info("初始化客户端配置...");
		ClientPref.Init();
		Log.game.info("注册消息...");
		GameMessage.collectAllMessage();
		Log.game.info("注册事件...");
		Event.collectEventListener();
		Log.game.info("加载资源...");
		ResourceManager.init();

		TestScreen testScreen = new TestScreen();
		testScreen.init();

		GameScreen gameScreen = new GameScreen();
		gameScreen.init();

		Log.game.info("加载场景...");
		setScreen(testScreen);

		Log.game.info("-------------");
		Log.game.info("启动完成");
		Log.game.info("-------------");
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		super.render();
	}

	@Override
	public void dispose() {
		Log.game.info("game exit");
		super.dispose();
	}

}
