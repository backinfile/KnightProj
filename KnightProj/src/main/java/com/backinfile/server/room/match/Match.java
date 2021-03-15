package com.backinfile.server.room.match;

import com.backinfile.gen.pb.Msg.DMatch;
import com.backinfile.server.room.Human;
import com.backinfile.server.room.match.MatchHuman.MatchHumanStatus;

public class Match {
	public MatchHuman[] humans = new MatchHuman[2];

	public Match() {
		humans[0] = new MatchHuman();
		humans[1] = new MatchHuman();
	}

	public void addHuman(int index, Human human) {
		humans[index].human = human;
		humans[index].status = MatchHumanStatus.Unready;
	}

	public DMatch.Builder getDMatch() {
		DMatch.Builder builder = DMatch.newBuilder();

		return builder;
	}
}
