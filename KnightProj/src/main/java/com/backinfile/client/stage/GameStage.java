package com.backinfile.client.stage;

import com.backinfile.client.ResourceManager;
import com.backinfile.client.actor.LineButton;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class GameStage extends BaseStage {

	private HorizontalGroup mainTable;
	private Table leftTable;
	private Table rightTable;
	private Table middleTable;

	public GameStage() {
		super(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
		setDebugAll(true);
	}

	@Override
	public void init() {
		mainTable = new HorizontalGroup();
		mainTable.setFillParent(true);
//		mainTable.setColor(new Color(1, 1, 1, 0.2f));
//		mainTable.setBackground(new TextureRegionDrawable(ResourceManager.IntroBackground));
		addActor(mainTable);

		leftTable = new Table();
		mainTable.addActor(leftTable);
		leftTable.setWidth(getWidth() / 5);
		leftTable.setHeight(getHeight());
		leftTable.setBackground(new TextureRegionDrawable(ResourceManager.White));

		middleTable = new Table();
		mainTable.addActor(middleTable);

		rightTable = new Table();
		mainTable.addActor(rightTable);

		leftTable.add(new LineButton("左边"));
		middleTable.add(new LineButton("中间"));
		rightTable.add(new LineButton("右边"));

	}

}
