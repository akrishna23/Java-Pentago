package netgame.jeopardy;

public class TestState {
	
	public static void main(String[] args) {
		String[] categories = new String[] {"Cat1", "Cat2", "Cat3"};
		String[]	[] questions = new String[][] { {"q1a", "q1b", "q1c", "q1d"},
				 {"q2a", "q2b", "q2c", "q2d"}, {"q3a", "q3b", "q3c", "q3d"}};
		String[][] answers = new String[][] { {"A1a", "A1b", "A1c", "A1d"},
				 {"A2a", "A2b", "A2c", "A2d"}, {"A3a", "A3b", "A3c", "A3d"}};
		JeopardyServerModel state = new JeopardyServerModel(categories, questions, answers);
		state.addPlayer(2);
		state.addPlayer(3);
		state.addPlayer(5);
		state.setPlayerName(2, "Mike");
		state.setPlayerName(3, "Kristen");
		state.setPlayerName(5, "Paul");

		state.setCorrectAnswer(3, 2, 3);
		state.setCorrectAnswer(2, 1, 3);
		System.out.print(state.getPlayerStats());
		System.out.print(state);
		System.out.print(state.getWinnerString());
	}
}
