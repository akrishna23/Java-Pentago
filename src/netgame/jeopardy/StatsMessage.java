package netgame.jeopardy;

import java.io.Serializable;

public class StatsMessage implements Serializable {
	
	String playerStats;
	
	public StatsMessage(String str) {
		playerStats = str;
	}

}
