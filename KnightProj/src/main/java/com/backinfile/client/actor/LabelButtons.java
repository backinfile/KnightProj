package com.backinfile.client.actor;

import java.util.HashMap;
import java.util.Map;

import com.backinfile.core.Log;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class LabelButtons extends Table {
	private final Map<String, Bar> bars = new HashMap<>();

	public LabelButtons(float width, float height) {
		setSize(width, height);

	}

	public void addLabelButton(String key, String labelText, String buttonText) {
		Bar bar = new Bar(getWidth(), labelText, buttonText);

		bars.put(key, bar);
		add(bar).row();
	}

	private static class Bar extends Table {
		public Bar(float width, String labelText, String buttonText) {
			setWidth(width);
			add(new DefaultLabel(labelText, 0.8f)).expandX().left();
			add(new LineButton(buttonText, 0.6f)).expandX().right();
			setDebug(true);
		}
	}
}
