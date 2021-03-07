package com.backinfile.client.stage;

import com.backinfile.client.screen.BaseScreen;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.Viewport;

public abstract class BaseStage extends Stage {
	private boolean active = true;
	private BaseScreen screen;

	public BaseStage() {
		super();
	}

	public BaseStage(Viewport viewport, Batch batch) {
		super(viewport, batch);
	}

	public BaseStage(Viewport viewport) {
		super(viewport);
	}

	public abstract void init();

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isActive() {
		return active;
	}

	public void setScreen(BaseScreen screen) {
		this.screen = screen;
	}

	public BaseScreen getScreen() {
		return screen;
	}
}
