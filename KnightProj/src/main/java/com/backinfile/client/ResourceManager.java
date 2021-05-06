package com.backinfile.client;

import com.backinfile.core.Log;
import com.backinfile.json.CardStrings;
import com.backinfile.json.JsonReader;
import com.backinfile.json.KeywordStrings;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeBitmapFontData;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

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

	public static TextureRegion IntroBackground;

	// ======字体
	public static BitmapFont DefaultFontSmallSamll;
	public static BitmapFont DefaultFontSmall;
	public static BitmapFont DefaultFontLarge;
	public static BitmapFont DefaultFont;

	// ======文本
	public static StringContainer<KeywordStrings> KeywordStringContainer;
	public static StringContainer<CardStrings> CardStringContainer;

	public static void init() {
		initImage();

		initFont();

		initText();
	}

	private static void initImage() {
		Chap1Heros = new GridTexture("image/card/chap1/hero.png", 7, 2);
		CardBorder = new TextureRegion(new Texture(Gdx.files.internal("image/card/tools/border.png")));
		CardFrontStore = new TextureRegion(new Texture(Gdx.files.internal("image/card/tools/store.png")));
		CardFrontOccupy = new TextureRegion(new Texture(Gdx.files.internal("image/card/tools/occupy.png")));

		DefaultButtonUp = newColorTexture(Color.DARK_GRAY);
		DefaultButtonDown = newColorTexture(Color.LIGHT_GRAY);
		ZeroAlphaTexture = newColorTexture(Color.CLEAR);
		AlphaMask = newColorTexture(new Color(1, 1, 1, 0.2f));
		White = newColorTexture(Color.WHITE);

		IntroBackground = new TextureRegion(new Texture(Gdx.files.internal("image/background.jpg")));
		IntroBackground.flip(true, false);
	}

	private static void initFont() {
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/msyh.ttc"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 12;
		DefaultFontSmallSamll = generator.generateFont(parameter);
		parameter.size = 18;
		DefaultFontSmall = generator.generateFont(parameter);
		parameter.size = 24;
		DefaultFont = generator.generateFont(parameter);
		parameter.size = 32;
		DefaultFontLarge = generator.generateFont(parameter);
		generator.dispose();
	}

	private static void initText() {
		JsonReader keywordsReader = new JsonReader(Gdx.files.internal("json/keywords.json"));
		KeywordStringContainer = new StringContainer<KeywordStrings>(keywordsReader.parseAsKeywordStrings(),
				KeywordStrings.getDefaultKeywordString());

		JsonReader cardsReader = new JsonReader(Gdx.files.internal("json/cards.json"));
		CardStringContainer = new StringContainer<CardStrings>(cardsReader.parseAsCardStrings(),
				CardStrings.getDefaultCardString());
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
