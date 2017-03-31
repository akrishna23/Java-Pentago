package netgame.jeopardy;

public class JeopardyClientModel {
	
	private String[] categories;
	private String[][] questions;
	private int[][] pointValues;
	private boolean[][] isCorrect;
	
	public JeopardyClientModel(String[] categories, String[][] questions, int[][] pointValues) {
		this.categories = categories;
		this.questions = questions;
		this.pointValues = pointValues;
		this.isCorrect = new boolean[questions.length][questions[0].length];
	}

	public String getCategory(int i) {
		return categories[i];
	}

	public String getQuestion(int col, int row) {
		return questions[col][row];
	}
	
	public int getPointValue(int col, int row) {
		return pointValues[col][row];
	}
	
	public int getNumCategories() {
		return categories.length;
	}
	
	public int getNumRows() {
		return questions[0].length;
	}
	
	public boolean isCorrect(int col, int row) {
		return isCorrect[col][row];
	}
	
	public void setCorrect(int col, int row) {
		isCorrect[col][row] = true;
	}
}
