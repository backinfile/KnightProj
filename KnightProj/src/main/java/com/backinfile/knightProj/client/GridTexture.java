package com.backinfile.knightProj.client;

import java.util.HashMap;
import java.util.Map;

import com.backinfile.knightProj.core.Log;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class GridTexture {
	private final TextureRegion gridTexture;
	private final int maxX;
	private final int maxY;
	private final Map<Integer, Cell> cells = new HashMap<>();
	private static final int OFFSET = 1000;

	private static class Cell {
		public String name;
		public int x;
		public int y;
		public TextureRegion texture;

		public Cell(int x, int y, TextureRegion texture) {
			this.x = x;
			this.y = y;
			this.texture = texture;
		}
	}

	public GridTexture(TextureRegion gridTexture, int maxX, int maxY) {
		this.gridTexture = gridTexture;
		this.maxX = maxX;
		this.maxY = maxY;

		init();
	}

	public GridTexture(String file, int maxX, int maxY) {
		this(new TextureRegion(new Texture(Gdx.files.internal(file))), maxX, maxY);
	}

	private void init() {
		int width = gridTexture.getRegionWidth();
		int height = gridTexture.getRegionHeight();

		int cellWidth = width / maxX;
		int cellHeight = height / maxY;

		for (int x = 0; x < maxX; x++) {
			for (int y = 0; y < maxY; y++) {
				TextureRegion texture = new TextureRegion(gridTexture, cellWidth * x, cellHeight * y, cellWidth,
						cellHeight);
				cells.put(getKey(x, y), new Cell(x, y, texture));
			}
		}
	}

	public TextureRegion getTexture(int x, int y) {
		return cells.get(getKey(x, y)).texture;
	}

	public void setName(int x, int y, String name) {
		cells.get(getKey(x, y)).name = name;
	}

	public TextureRegion getTexture(String name) {
		for (Cell cell : cells.values()) {
			if (name.equals(cell.name)) {
				return cell.texture;
			}
		}
		return null;
	}

	private final int getKey(int x, int y) {
		return x * OFFSET + y;
	}
}
