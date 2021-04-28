package com.backinfile.support;

import java.util.function.Supplier;

import com.backinfile.core.serilize.ISerializable;

public interface Param extends ISerializable {

	<T> T get(String name);

	boolean contains(String name);

	// 适用于传递单个值
	boolean contains();

	// 适用于传递单个值
	<T> T get();

	default <T> T getOrDefault(String name, T defaultValue) {
		T value = get(name);
		if (value != null) {
			return value;
		}
		return defaultValue;
	}

	default <T> T getOrDefault(String name, Supplier<T> defaultValue) {
		T value = get(name);
		if (value != null) {
			return value;
		}
		return defaultValue.get();
	}

	default int getInt(String name) {
		return get(name);
	}

	default String getString(String name) {
		return getOrDefault(name, "");
	}
}
