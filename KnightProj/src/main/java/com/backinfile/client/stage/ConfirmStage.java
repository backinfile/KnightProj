package com.backinfile.client.stage;

import java.util.function.Consumer;

import com.backinfile.client.ResourceManager;
import com.backinfile.client.actor.LineButton;
import com.backinfile.client.screen.BaseScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

public class ConfirmStage extends BaseStage {
	private BaseScreen baseScreen;
	private InputProcessor oldInputProcessor;
	private Label label;
	private LineButton confirm;
	private Image alphaMask;
	private Group group;
	private Consumer<BaseScreen> callback = null;

	private class ConfirmStageClickListener extends ClickListener {
		@Override
		public void clicked(InputEvent event, float x, float y) {
			hide();
			Gdx.input.setInputProcessor(oldInputProcessor);
			if (callback != null) {
				callback.accept(baseScreen);
			}
		}
	}

	@Override
	public void init() {
		label = new Label("null", new LabelStyle(ResourceManager.DefaultFont, null));
		label.setAlignment(Align.center);

		confirm = new LineButton("确认");
		confirm.getLabel().setFontScale(0.7f);

		alphaMask = new Image(ResourceManager.AlphaMask);

		group = new Group();
		group.addActor(alphaMask);
		group.addActor(confirm);
		group.addActor(label);
		addActor(group);

		confirm.addListener(new ConfirmStageClickListener());
	}

	public void show(BaseScreen screen, String text, Consumer<BaseScreen> callback) {
		this.baseScreen = screen;

		oldInputProcessor = Gdx.input.getInputProcessor();
		Gdx.input.setInputProcessor(this);

		group.setVisible(true);

		label.setText(text);
		label.setX(getWidth() / 2 - label.getWidth() / 2);
		label.setY(getHeight() / 2 + label.getHeight() / 2);

		confirm.setX(getWidth() / 2 - label.getWidth() / 2);
		confirm.setY(getHeight() / 2 - label.getHeight() * 3 / 2);

		alphaMask.setPosition(0, 0);
		alphaMask.setSize(getWidth(), getHeight());

		this.callback = callback;

		setActive(true);
	}

	public void hide() {
		setActive(false);
	}
	
}
