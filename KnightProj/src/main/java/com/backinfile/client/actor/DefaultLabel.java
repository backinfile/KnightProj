package com.backinfile.client.actor;

import com.backinfile.client.ResourceManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class DefaultLabel extends Label {
	public DefaultLabel(String text) {
		this(text, 1f, null);
	}

	public DefaultLabel(String text, Color fontColor) {
		this(text, 1f, fontColor);
	}

	public DefaultLabel(String text, float fontScale) {
		this(text, fontScale, null);
	}

	public DefaultLabel(String text, float fontScale, Color fontColor) {
		super(text, new LabelStyle(ResourceManager.DefaultFont, fontColor));
		setFontScale(fontScale);
	}
}
