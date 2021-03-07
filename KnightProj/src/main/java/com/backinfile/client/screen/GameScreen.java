package com.backinfile.client.screen;

import com.backinfile.client.stage.GameStage;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public class GameScreen extends BaseScreen {
	public GameStage gameStage;

	@Override
	public void init() {
		super.init();

		gameStage = new GameStage();
		gameStage.setScreen(this);
		gameStage.init();
		Gdx.input.setInputProcessor(gameStage);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		super.render(delta);
		if (gameStage.isActive()) {
			gameStage.act();
			gameStage.draw();
		}

	}

	@Override
	public void resize(int width, int height) {
		gameStage.getViewport().update(width, height, true);
		super.resize(width, height);
	}

	@Override
	public void dispose() {
		gameStage.dispose();
		super.dispose();
	}
}