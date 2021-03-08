package com.backinfile.client.actor;

import java.util.ArrayList;
import java.util.List;

import com.backinfile.client.stage.BaseStage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;

public class RoomListView extends Table {
	private BaseStage stage;
	private List<String> list = new ArrayList<>();

	public RoomListView(BaseStage stage) {
		this.stage = stage;
		setDebug(true);

//		setWidth(stage.getWidth() / 10 * 3);
//		setHeight(stage.getHeight());
//		setPosition((stage.getWidth() - getWidth()) / 2, 0);

		stage.addActor(this);

		align(Align.center);
		addRoom("room1");
		addRoom("room2");
		addRoom("room3");
	}

	public void addRoom(String key) {
		add(new DefaultLabel(key, 1f)).expandX().left();
		add(new LineButton("enter", 1f)).expandX().right();
		row();
	}

	public void refreshList() {
		if (!list.isEmpty()) {
			String key = list.get(0);
		}
		for (int i = 1; i < list.size(); i++) {
			String key = list.get(i);
			add(new DefaultLabel(key, 1f)).expand().left().top().padLeft(stage.getWidth() / 10f);
			add(new LineButton("enter", 1f)).expand().right().top().padRight(stage.getWidth() / 10f);
			row();
		}

	}
}
