package com.backinfile.client.actor;

import com.backinfile.client.stage.BaseStage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class RoomListView extends Table {
	public RoomListView(BaseStage stage) {
		setDebug(true);
		setWidth(stage.getWidth() / 5);
		setHeight(stage.getHeight());
		setPosition(0, 0);
		stage.addActor(this);
	}

	public void addRoom(String key, String text) {
		add(new DefaultLabel(key, 0.8f)).left();
		add(new LineButton("enter", 0.6f)).right();
		row();
	}
}
