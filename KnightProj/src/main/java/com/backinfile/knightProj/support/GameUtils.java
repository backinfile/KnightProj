package com.backinfile.knightProj.support;

import com.badlogic.gdx.Gdx;

public class GameUtils {

	public static float getDeltaTime() {
		return Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f);
	}
}
