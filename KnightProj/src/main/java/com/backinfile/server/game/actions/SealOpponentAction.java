package com.backinfile.server.game.actions;

import com.backinfile.server.board.Card;
import com.backinfile.server.board.Player;

public class SealOpponentAction extends AbstractAction {
	public int pos;
	public Player opponent;
	public Card seal;

	public SealOpponentAction(Player player, Card seal, int pos) {
		super(player);
		this.pos = pos;
		this.opponent = player.board.getOpponent(player);
		this.seal = seal;
	}

	@Override
	public void init() {
	}

	@Override
	public void update() {
		player.reviewPile.remove(seal);
		opponent.battleStoreSealPile.set(pos, seal);
		isDone = true;
	}
}
