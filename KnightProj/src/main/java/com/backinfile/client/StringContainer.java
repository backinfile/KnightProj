package com.backinfile.client;

import java.util.Map;

public class StringContainer<T> {
	private Map<String, T> strings;
	private T defaultString;

	public StringContainer(Map<String, T> strings, T defaultString) {
		this.strings = strings;
		this.defaultString = defaultString;
	}

	public T get(String key) {
		T str = strings.get(key);
		if (str != null) {
			return str;
		}
		return defaultString;
	}
}
