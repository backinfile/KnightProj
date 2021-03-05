package com.backinfile.core;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import com.backinfile.support.Utils2;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class ClientPref {
	private static final String PREF_FILE_NAME = Const.GAME_NAME + "(" + Const.GAME_AUTHOR + ")";
	private static final String KEY_PREF = ClientPref.class.getSimpleName() + ":";

	public static ClientPref Instance;

	public String name = "";

	private ClientPref() {
	}

	public void saveSync() {
		Preferences pref = Gdx.app.getPreferences(PREF_FILE_NAME);

		Field[] declaredFields = ClientPref.class.getDeclaredFields();
		for (Field field : declaredFields) {
			if (Modifier.isStatic(field.getModifiers())) {
				continue;
			}
			Class<?> type = field.getType();
			try {
				if (type == int.class) {
					int value = field.getInt(this);
					pref.putInteger(KEY_PREF + field.getName(), value);
					Log.game.debug("save {}->{}", value, field.getName());
				} else if (type == String.class) {
					String value = (String) field.get(this);
					if (value == null)
						value = "";
					pref.putString(KEY_PREF + field.getName(), value);
					Log.game.debug("save {}->{}", value, field.getName());
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}

		pref.flush();
		Log.game.info("save Settings");
	}

	public void loadSync() {
		if (Gdx.app == null) {
			return;
		}
		Preferences pref = Gdx.app.getPreferences(PREF_FILE_NAME);

		Field[] declaredFields = ClientPref.class.getDeclaredFields();
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
						Log.game.debug("write {}->{}", value, field.getName());
					}
				} else if (type == String.class) {
					String value = pref.getString(KEY_PREF + field.getName(), "");
					if (!Utils2.isNullOrEmpty(value)) {
						field.set(this, value);
						Log.game.debug("write {}->{}", value, field.getName());
					}
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}

		Log.game.info("load Settings");
	}

	public static void Init() {
		Instance = new ClientPref();
		Instance.loadSync();
	}
}
