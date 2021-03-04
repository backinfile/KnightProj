package com.backinfile.core;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import com.backinfile.core.log.LoggerLevel;
import com.backinfile.support.Utils2;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class Settings {
	private static final String PREF_FILE_NAME = Const.GAME_NAME + "(" + Const.GAME_AUTHOR + ")";
	private static final String KEY_PREF = Settings.class.getSimpleName() + ":";

	public static Settings Instance;

	public LoggerLevel LOGGER_LEVEL = LoggerLevel.DEBUG;
	public int SCREEN_WIDTH = 1200;
	public int SCREEN_HEIGHT = 800;

	private Settings() {
	}

	public void saveSync() {
		Preferences pref = Gdx.app.getPreferences(PREF_FILE_NAME);

		Field[] declaredFields = Settings.class.getDeclaredFields();
		for (Field field : declaredFields) {
			if (Modifier.isStatic(field.getModifiers())) {
				continue;
			}
			Class<?> type = field.getType();
			try {
				if (type == int.class) {
					int value = field.getInt(this);
					pref.putInteger(KEY_PREF + field.getName(), value);
					Log.game.debug("save {0}->{1}", value, field.getName());
				} else if (type == String.class) {
					String value = (String) field.get(this);
					pref.putString(KEY_PREF + field.getName(), value);
					Log.game.debug("save {0}->{1}", value, field.getName());
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}

		pref.flush();
		Log.game.info("save Settings");
	}

	public void loadSync() {
		Preferences pref = Gdx.app.getPreferences(PREF_FILE_NAME);

		Field[] declaredFields = Settings.class.getDeclaredFields();
		for (Field field : declaredFields) {
			if (Modifier.isStatic(field.getModifiers())) {
				continue;
			}
			Class<?> type = field.getType();
			try {
				if (type == int.class) {
					int value = pref.getInteger(KEY_PREF + field.getName(), Integer.MIN_VALUE);
					if (value != Integer.MIN_VALUE) {
						field.set(this, value);
						Log.game.debug("write {0}->{1}", value, field.getName());
					}
				} else if (type == String.class) {
					String value = pref.getString(KEY_PREF + field.getName(), "");
					if (!Utils2.isNullOrEmpty(value)) {
						field.set(this, value);
						Log.game.debug("write {0}->{1}", value, field.getName());
					}
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}

		Log.game.info("load Settings");
	}

	public static void Init() {
		Instance = new Settings();
		Instance.loadSync();
	}
}
