package com.backinfile.json;

import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Disposable;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class JsonReader{
	private static final Gson gson = new Gson();
	private FileHandle fileHandle;
	private String fileContent;

	public JsonReader(FileHandle fileHandle) {
		this.fileHandle = fileHandle;
		fileContent = fileHandle.readString(String.valueOf(StandardCharsets.UTF_8));
	}

	public JsonReader(String path) {
		this(Gdx.files.internal(path));
	}

	@SuppressWarnings({ "unchecked" })
	public Map<String, KeywordStrings> parseAsKeywordStrings() {
		Type type = new TypeToken<Map<String, KeywordStrings>>() {
		}.getType();
		return (Map<String, KeywordStrings>) gson.fromJson(fileContent, type);
	}

	@SuppressWarnings({ "unchecked" })
	public Map<String, CardStrings> parseAsCardStrings() {
		Type type = new TypeToken<Map<String, CardStrings>>() {
		}.getType();
		return (Map<String, CardStrings>) gson.fromJson(fileContent, type);
	}
}
