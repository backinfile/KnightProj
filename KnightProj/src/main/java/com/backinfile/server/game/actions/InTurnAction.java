package com.backinfile.server.game.actions;

import com.backinfile.server.board.Player;

public class InTurnAction extends AbstractAction {
	public InTurnAction(Player player) {
		super(player);
	}

	@Override
	public void init() {
		// 发送消息
	}

	@Override
	public void update() {
		// 轮询是否接收到消息
	}

}
