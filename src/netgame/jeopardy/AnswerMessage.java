package netgame.jeopardy;

import java.io.Serializable;

public class AnswerMessage implements Serializable {
	
	int column;
	int row;
	String answer;
	boolean correct;
	
	public AnswerMessage(String str, int c, int r) {
		column = c;
		row = r;
		answer = str;
	}
	
	public AnswerMessage(String str, int c, int r, boolean cor) {
		this(str, c, r);
		correct = cor;
	}

}
