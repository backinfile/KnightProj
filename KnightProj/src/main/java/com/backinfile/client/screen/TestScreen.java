package com.backinfile.client.screen;

import com.backinfile.client.stage.TestStage;
import com.backinfile.core.Const;
import com.backinfile.core.GameMessage;
import com.backinfile.gen.pb.Msg.SCConnect;
import com.backinfile.seam.GameClient;
import com.backinfile.seam.GameServer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public class TestScreen extends BaseScreen {
	public GameClient gameClient;

	private int cnt = 0;
	private TestStage testStage;

	@Override
	public void init() {
		gameClient = new GameClient();
		gameClient.setAddr("localhost", Const.GAMESERVER_PORT);
		gameClient.start();

		testStage = new TestStage(new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getWidth()));
		Gdx.input.setInputProcessor(testStage);
		testStage.init();
		testStage.setScreen(this);

		super.init();
	}

	@Override
	public void render(float delta) {

		if (gameClient != null && gameClient.isAlive()) {
			gameClient.pulse();
		}

		if (cnt++ == 50) {
//			if (gameClient != null) {
//				gameClient.getConnection().sendGameMessage(new GameMessage(SCConnect.newBuilder().build()));
//			}
//			confirm("hahhah", null);
		}

		testStage.act();
		testStage.draw();

		super.render(delta);
	}

	@Override
	public void dispose() {
		testStage.dispose();
	}

	@Override
	public void resize(int width, int height) {
		testStage.getViewport().update(width, height);
		super.resize(width, height);
	}
}
