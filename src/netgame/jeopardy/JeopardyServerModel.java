package netgame.jeopardy;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public class JeopardyServerModel implements Serializable {
	
	int numCols;
	int numRows;
	String[] categories;
	String[][] questions;
	String[][] answers;
	int[][] pointValues;
	TreeMap<Integer, Player> playerMap;
	
	boolean gameStarted;
	boolean gameOver;
	
	public JeopardyServerModel(String[] categories, String[][] questions, String[][] answers, int[][] pointValues) {
		if (questions.length == 0)
			throw new IllegalArgumentException("Questions length is zero.");
		if (categories.length != questions.length)
			throw new IllegalArgumentException("Dimension mismatch: categories and questions.");
		this.categories = categories;
		this.questions = questions;
		this.answers = answers;
		numCols = questions.length;
		numRows = questions[0].length;
		playerMap = new TreeMap<Integer, Player>();
		if (pointValues != null) {
			this.pointValues = pointValues;
		}
		else {
			this.pointValues = new int[numCols][numRows];
			for (int i = 0; i < numCols; i++) {
				for (int j = 0; j < numRows; j++) {
					this.pointValues[i][j] = (j + 1) * 100;
				}
			}

		}
	}
	
	public JeopardyServerModel(String[] categories, String[][] questions, String[][] answers) {
		this(categories, questions, answers, null);
	}
	
	private static class Player implements Serializable, Comparable<Player> {
		String name;
		int score;
		int numCorrect;
		boolean[][] correctlyAnswered;
		
		public Player(String name, int columns, int rows) {
			this.name = name;
			score = 0;
			correctlyAnswered = new boolean[columns][rows];
		}
		
		public void setName(String str) {
			name = str;
			
		}
		
		/** @return current number of correct answers **/
		public int setCorrectAnswer(int column, int row, int points) {
			if (correctlyAnswered[column][row])
				return numCorrect;
			correctlyAnswered[column][row] = true;
			score += points;
			numCorrect++;
			return numCorrect;
		}
		
		public String toString() {
			return "<" + name + ", " + score + ", " + numCorrect + ">";
		}

		public int compareTo(Player comparedPlayer) {
			if (score > comparedPlayer.score) {
				return -1;
			} else if (score < comparedPlayer.score) {
				return 1;
			}
			return 0;
		}
	}
	
	public String[][] getQuestions() {
		return questions;		
	}
	
	public boolean checkAnswer(String answer, int column, int row) {
		return (answer.trim().equalsIgnoreCase(answers[column][row]));
	}
	
	/** updates correctlyAnswered variable and player score **/
	public void setCorrectAnswer(int id, int column, int row) {
		if( ! playerMap.get(id).correctlyAnswered[column][row])
		playerMap.get(id).setCorrectAnswer(column, row, pointValues[column][row]);
		
	}
	
	public void deductWrongAnswer(int id, int column, int row) {
		playerMap.get(id).score -= pointValues[column][row];
	}
	
	public void addPlayer(int id) {
		playerMap.put(id, new Player(null, numCols, numRows));
	}
	
	public void removePlayer(int id) {
		playerMap.remove(id);
	}
	
	public void setPlayerName(int id, String name) {
		playerMap.get(id).setName(name);
	}
	
	public Player getPlayer(int id) {
		return playerMap.get(id);
	}
	
	public int numCorrectForPlayer(int id) {
		return playerMap.get(id).numCorrect;
	}
	
	
	public String getPlayerStats() {
		String str = "Player\tScore\t#Correct\n";
		ArrayList<Player> players = getSortedPlayers();
		for (Player player : players) {
			str +=  player.name + "\t" + player.score + "\t" + player.numCorrect + "\n";
		}
		return str;
	}
	
	public ArrayList<Player> getSortedPlayers() {
		ArrayList<Player> players = new ArrayList<Player>(playerMap.values());
		Collections.sort(players);
		return players;
	}
	
	public String getWinnerString() {
		String winnerString = "";
		ArrayList<Player> players = getSortedPlayers();
		int maxScore = players.get(0).score;
		int count = 0;
		while (count < players.size() - 1 && players.get(count + 1).score == maxScore)
			count++;
		for (int i = 0; i < count; i++) {
			winnerString += players.get(i).name + " and ";
		}
		winnerString += players.get(count).name + " won the game!";
		return winnerString;
	}

	public String toString() {
		String str = "";
		str += "Categories:\n";
		str += Arrays.toString(categories) + "\n";
		str += "Questions:\n";
		str += Arrays.deepToString(questions) + "\n";
		str += "Answers:\n";
		str += Arrays.deepToString(answers) + "\n";
		str += "Scores:\n";
		str += Arrays.deepToString(pointValues) + "\n";
		str += "Players:\n";
		str += playerMap.toString();
		return str;
	}
}
