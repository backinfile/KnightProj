package com.backinfile.knightProj.core.net;

import java.util.concurrent.Delayed;

import com.backinfile.knightProj.core.GameMessage;

public interface Connection extends Delayed {
	public long getId();

	public void pulse();

	public boolean isAlive();

	public GameMessage getGameMessage();

	public void sendGameMessage(GameMessage gameMessage);

	public void close();

}
