package com.backinfile.server.player;

import com.backinfile.core.serilize.ISerializable;
import com.backinfile.core.serilize.InputStream;
import com.backinfile.core.serilize.OutputStream;

public class Player implements ISerializable {
	private long id;
	private String name = "null";

	public Player() {
	}

	public Player(long id, String name) {
		this.id = id;
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	@Override
	public void writeTo(OutputStream out) {
	}

	@Override
	public void readFrom(InputStream in) {
	}

}
