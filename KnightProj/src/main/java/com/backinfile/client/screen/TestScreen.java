package com.backinfile.client.screen;

import com.backinfile.client.stage.TestStage;
import com.backinfile.core.Log;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public class TestScreen extends BaseScreen {

	private int cnt = 0;
	private TestStage testStage;

	@Override
	public void init() {

		testStage = new TestStage(new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
		Gdx.input.setInputProcessor(testStage);
		testStage.init();
		testStage.setScreen(this);
		Log.game.debug("width={}, height={}", Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		super.init();
	}

	@Override
	public void render(float delta) {

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
		testStage.getViewport().update(width, height, true);
		super.resize(width, height);
	}
}
