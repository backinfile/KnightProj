package com.backinfile.support;

import java.util.HashMap;

import com.backinfile.core.Log;
import com.backinfile.core.SysException;

public class PairParam implements Param {

	private static final String DEFAULT_KEY = "_DEFAULT_KEY_";
	private final HashMap<String, Object> params = new HashMap<>();

	public PairParam(Object... args) {
		if (args.length == 0) {
		} else if (args.length == 1) {
			params.put(DEFAULT_KEY, args[0]);
		} else if (args.length > 1 && args.length % 2 == 0) {
			for (int i = 0; i < args.length; i += 2) {
				params.put((String) args[i], args[i + 1]);
			}
		} else {
			Log.game.error("Param cannot prase args", new SysException());
		}
	}

	public void add(String name, Object value) {
		params.put(name, value);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T get(String name) {
		return (T) params.get(name);
	}

	@Override
	public <T> T get() {
		return get(DEFAULT_KEY);
	}

	@Override
	public boolean contains(String name) {
		return params.containsKey(name);
	}

	@Override
	public boolean contains() {
		return params.containsKey(DEFAULT_KEY);
	}

}
