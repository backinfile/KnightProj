package com.backinfile.client.game.chap2;

import com.backinfile.client.game.ActionCard;
import com.backinfile.client.game.Board;
import com.backinfile.client.game.Player;
import com.backinfile.client.game.actions.AttackCardAttackAction;

public class Attack extends ActionCard {

	public static final int SN = 21001; // 章节+类型+序号
	public static final int CHAPTER = 2;

	public Attack(Board board) {
		super(board, SN, CHAPTER);
	}

	@Override
	public boolean canUse() {
		Player player = board.getCurActionPlayer();
		int store = player.getAllStore().size();
		return store > 0;
	}

	@Override
	public void use() {
		board.actionManager.addToBottom(new AttackCardAttackAction(board.getCurActionPlayer()));
	}

}
