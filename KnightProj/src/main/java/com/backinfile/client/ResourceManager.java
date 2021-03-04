package com.backinfile.client;

import com.backinfile.core.Log;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class ResourceManager {

	// ========图片
	public static GridTexture Chap1Heros;
	public static TextureRegion CardBorder;
	public static TextureRegion CardFrontOccupy;
	public static TextureRegion CardFrontStore;

	public static TextureRegion DefaultButtonUp;
	public static TextureRegion DefaultButtonDown;
	public static TextureRegion ZeroAlphaTexture;

	public static TextureRegion AlphaMask;
	public static TextureRegion White;

	// ======字体
	public static BitmapFont DefaultFont;

	public static void init() {
		Log.game.info("start loading resource...");

		// ========图片
		Chap1Heros = new GridTexture("image/card/chap1/hero.png", 7, 2);
		CardBorder = new TextureRegion(new Texture(Gdx.files.internal("image/card/tools/border.png")));
		CardFrontStore = new TextureRegion(new Texture(Gdx.files.internal("image/card/tools/store.png")));
		CardFrontOccupy = new TextureRegion(new Texture(Gdx.files.internal("image/card/tools/occupy.png")));

		DefaultButtonUp = newColorTexture(Color.DARK_GRAY);
		DefaultButtonDown = newColorTexture(Color.LIGHT_GRAY);
		ZeroAlphaTexture = newColorTexture(Color.CLEAR);
		AlphaMask = newColorTexture(new Color(0, 0, 0, 0.95f));
		White = newColorTexture(Color.WHITE);
		// ======字体
		DefaultFont = new BitmapFont(Gdx.files.internal("font/sarasa/sarasa.fnt"), false);
		Log.game.info("resource loading complete");
	}

	public static void dispose() {

	}

	public static TextureRegion newColorTexture(Color color) {
		return newColorTexture(8, 8, color);

	}

	public static TextureRegion newColorTexture(int width, int height, Color color) {
		Pixmap pixmap = new Pixmap(width, height, Format.RGBA8888);
		pixmap.setColor(color);
		pixmap.fill();
		TextureRegion texture = new TextureRegion(new Texture(pixmap));
		pixmap.dispose();
		return texture;
	}
}
