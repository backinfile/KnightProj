package com.backinfile.seam;

import com.backinfile.client.ResourceManager;
import com.backinfile.client.screen.GameScreen;
import com.backinfile.client.screen.TestScreen;
import com.backinfile.core.GameMessage;
import com.backinfile.core.Log;
import com.backinfile.core.ClientPref;
import com.backinfile.core.event.RoomEvent;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public class ClientStartup extends Game {

	@Override
	public void create() {
		ClientPref.Init();
		GameMessage.collectAllMessage();
		RoomEvent.collectEventListener();
		ResourceManager.init();

		TestScreen testScreen = new TestScreen();
		testScreen.init();

		GameScreen gameScreen = new GameScreen();
		gameScreen.init();

		setScreen(gameScreen);
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
