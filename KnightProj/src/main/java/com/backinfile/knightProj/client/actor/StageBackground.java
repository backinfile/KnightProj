package com.backinfile.knightProj.client.actor;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class StageBackground extends Table {

	public StageBackground(TextureRegion textureRegion, float alpha) {
		setFillParent(true);
		setBackground(new TextureRegionDrawable(textureRegion));
		setColor(1, 1, 1, alpha);
	}
}
