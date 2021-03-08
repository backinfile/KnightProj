package com.backinfile.client.stage;

import com.backinfile.client.actor.RoomListView;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class GameStage extends BaseStage {

	RoomListView roomListView;

	public GameStage() {
		super(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
	}

	@Override
	public void init() {
//		addActor(new StageBackground(ResourceManager.IntroBackground, 0.2f));
		roomListView = new RoomListView(this);

//		LineButton lineButton = new LineButton("qwer");
//		lineButton.setPosition(getWidth() / 2, getHeight() / 2);
//		mainTable.add(lineButton);
//		Table lefTable = new Table();

	}

}
