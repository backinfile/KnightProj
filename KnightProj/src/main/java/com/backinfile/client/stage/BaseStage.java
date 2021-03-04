package com.backinfile.client.stage;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;

public abstract class BaseStage extends Stage {
	private boolean active;

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
}
