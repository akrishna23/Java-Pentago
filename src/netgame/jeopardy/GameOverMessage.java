package netgame.jeopardy;

import java.io.Serializable;

public class GameOverMessage implements Serializable {
	
	String winner;
	
	public GameOverMessage(String str) {
		winner = str;
	}

}
