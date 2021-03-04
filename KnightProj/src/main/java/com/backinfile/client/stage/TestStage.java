package com.backinfile.client.stage;

import com.backinfile.client.ResourceManager;
import com.backinfile.core.Log;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.Viewport;

public class TestStage extends BaseStage {

	private Actor actor;

	public TestStage(Viewport viewport) {
		super(viewport);
	}

	@Override
	public void init() {
		actor = new Image(ResourceManager.CardFrontStore);
		actor.setX(getWidth() / 2 - actor.getWidth() / 2);
		actor.setY(getHeight() / 2 - actor.getHeight() / 2);
		addActor(actor);
		Log.test.info("show image");
	}

	@Override
	public void act() {
		super.act();
	}
}
