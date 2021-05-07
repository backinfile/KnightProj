package com.backinfile.knightProj.client.stage;

import java.util.function.Consumer;

import com.backinfile.knightProj.client.ResourceManager;
import com.backinfile.knightProj.client.actor.LineButton;
import com.backinfile.knightProj.client.screen.BaseScreen;
import com.backinfile.knightProj.support.Utils2;
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
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

public class ConfirmStage extends BaseStage {
	private InputProcessor oldInputProcessor;
	private Label label;
	private LineButton confirm;
	private Table table;
	private Consumer<BaseScreen> callback = null;

	private class ConfirmStageClickListener extends ClickListener {
		@Override
		public void clicked(InputEvent event, float x, float y) {
			hide();
			Gdx.input.setInputProcessor(oldInputProcessor);
			if (callback != null) {
				callback.accept(getScreen());
			}
		}
	}

	@Override
	public void init() {
		label = new Label("null", new LabelStyle(ResourceManager.DefaultFont, null));
		label.setAlignment(Align.center);

		confirm = new LineButton("确认");
		confirm.getLabel().setFontScale(0.8f);

		table = new Table();
		table.setBackground(new TextureRegionDrawable(ResourceManager.AlphaMask));
		table.setFillParent(true);
		table.add(label);
		table.row();

		table.add(confirm).padTop(20f);
		addActor(table);

		confirm.addListener(new ConfirmStageClickListener());

		setActive(false);
	}

	public void show(BaseScreen screen, String text, String buttonText, Consumer<BaseScreen> callback) {
		this.setScreen(screen);

		oldInputProcessor = Gdx.input.getInputProcessor();
		Gdx.input.setInputProcessor(this);

		table.setVisible(true);

		label.setText(text);
		if (Utils2.isNullOrEmpty(buttonText)) {
			confirm.setText("确认");
		} else {
			confirm.setText(buttonText);
		}

		this.callback = callback;

		setActive(true);
	}

	public void hide() {
		setActive(false);
	}

}
