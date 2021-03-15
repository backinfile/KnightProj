package com.backinfile.server.room.match;

import com.backinfile.server.room.Human;

public class MatchHuman {
	public Human human = null;
	public MatchHumanStatus status = MatchHumanStatus.Unready;

	public enum MatchHumanStatus {
		Ready, Unready
	}
}
