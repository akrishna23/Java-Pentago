
public class PentagoStats {

	private int turnsPlayed;
	private boolean winnerExists;
	private boolean tieExists;
	private boolean gameInProgress;
	
	public PentagoStats(int turnsPlayed, boolean winnerExists, boolean tieExists, boolean gameInProgress){
		this.turnsPlayed = turnsPlayed;
		this.winnerExists = winnerExists;
		this.tieExists = tieExists;
		this.gameInProgress = gameInProgress;
	}
	
	public int getTurnsPlayed(){
		return turnsPlayed;
	}
	
	public boolean getWinnerExists(){
		return winnerExists;
	}
	
	public boolean getTieExists(){
		return tieExists;
	}
	
	public boolean getGameInProgress(){
		return gameInProgress;
	}
}
