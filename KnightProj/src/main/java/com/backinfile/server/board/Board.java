package com.backinfile.server.board;

import java.util.Random;

import com.backinfile.server.game.actions.InTurnAction;
import com.badlogic.gdx.net.Socket;

public class Board {
	private int idCounter = 1;

	private Player[] players = new Player[] { null, null };
	public Random random = new Random();

	private Player curTurnPlayer = null;
	private Player startPlayer = null;

	public final ActionManager actionManager = new ActionManager();

	public Board() {

	}

	public void init() {

		// 发送开始消息

		startPlayer = players[random.nextInt(players.length)];
		curTurnPlayer = startPlayer;
		actionManager.addToBottom(new InTurnAction(startPlayer));
	}

	public void update() {
		actionManager.update();
	}

	public void addPlayer(String name) {
		for (int i = 0; i < players.length; i++) {
			if (players[i] == null) {
				players[i] = new Player(name, this);
				break;
			}
		}
	}

	public Player getOpponent(Player player) {
		if (player == players[0]) {
			return players[1];
		}
		return players[0];
	}

	public int getCardId() {
		return idCounter++;
	}

	public Player getCurActionPlayer() {
		return actionManager.getCurAction().getPlayer();
	}

	public Player getCurTurnPlayer() {
		return curTurnPlayer;
	}

}
