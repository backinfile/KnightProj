package com.backinfile.knightProj.client.screen;

import java.util.function.Consumer;

import com.backinfile.knightProj.client.stage.ConfirmStage;
import com.badlogic.gdx.ScreenAdapter;

public abstract class BaseScreen extends ScreenAdapter {

	private static ConfirmStage confirmStage;

	public void init() {

		if (confirmStage == null) {
			confirmStage = new ConfirmStage();
			confirmStage.init();
		}
	}

	@Override
	public void render(float delta) {
		super.render(delta);

		if (confirmStage.isActive()) {
			confirmStage.act();
			confirmStage.draw();
		}
	}

	public void confirm(String text, String buttonText, Consumer<BaseScreen> callback) {
		confirmStage.show(this, text,buttonText, callback);
	}

	public void confirm(String text) {
		confirmStage.show(this, text, null, null);
	}

	@Override
	public void resize(int width, int height) {
		if (confirmStage.isActive()) {
			confirmStage.getViewport().update(width, height);
		}
	}

}
