package com.backinfile.client.stage;

import com.backinfile.client.ResourceManager;
import com.backinfile.client.actor.LineButton;
import com.backinfile.core.Log;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Window.WindowStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.Viewport;

public class TestStage extends BaseStage {

	private Actor actor;

	private Button button;

	public TestStage(Viewport viewport) {
		super(viewport);
	}

	@Override
	public void init() {
		setDebugAll(true);
		Table table = new Table();
		addActor(table);
		table.setFillParent(true);

		table.setBackground(new TextureRegionDrawable(ResourceManager.IntroBackground));

		TextButtonStyle textButtonStyle = new TextButtonStyle();
		textButtonStyle.font = ResourceManager.DefaultFont;
		textButtonStyle.up = new TextureRegionDrawable(ResourceManager.DefaultButtonUp);
		textButtonStyle.down = new TextureRegionDrawable(ResourceManager.DefaultButtonDown);
		button = new TextButton("Button", textButtonStyle);

		table.add(button);

		button.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Log.test.info("click test button");
//				getScreen().confirm(ResourceManager.KeywordStringContainer.get("attack").DESCRIPTION);

				WindowStyle windowStyle = new WindowStyle();
				windowStyle.titleFont = ResourceManager.DefaultFont;
			}
		});

	}

	@Override
	public void act() {
		super.act();
	}
}
