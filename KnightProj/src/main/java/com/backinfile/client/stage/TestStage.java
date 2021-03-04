package com.backinfile.client.stage;

import com.backinfile.client.ResourceManager;
import com.backinfile.core.Log;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.Viewport;

public class TestStage extends BaseStage {

	private Actor actor;

	private Button button;

	public TestStage(Viewport viewport) {
		super(viewport);
	}

	@Override
	public void init() {
		actor = new Image(ResourceManager.CardFrontStore);
		actor.setX(getWidth() / 2 - actor.getWidth() / 2);
		actor.setY(getHeight() / 2 - actor.getHeight() / 2);
		Log.test.info("show image");

		TextButtonStyle textButtonStyle = new TextButtonStyle();
		textButtonStyle.font = ResourceManager.DefaultFont;
		textButtonStyle.up = new TextureRegionDrawable(ResourceManager.DefaultButtonUp);
		textButtonStyle.down = new TextureRegionDrawable(ResourceManager.DefaultButtonDown);
		button = new TextButton("Button", textButtonStyle);

		addActor(button);

		button.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Log.test.info("click test button");
				getScreen().confirm("啊好久不是打开是看看");
			}
		});

	}

	@Override
	public void act() {
		super.act();
	}
}
