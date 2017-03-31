import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;


public class PentagoServerModel {

	private ConcurrentHashMap<Integer, Integer> currentPartners 
		= new ConcurrentHashMap<Integer, Integer>();
	private LinkedBlockingQueue<Integer> waitingPlayers = new LinkedBlockingQueue<Integer>(50);

	public void addPlayerToQueue(int playerID) throws InterruptedException {
		currentPartners.remove(playerID);
		try {
			waitingPlayers.put(playerID);
		}
		catch (InterruptedException e) {
			
		}
	}
	
//	public PentagoClientModel startGame(int firstPlayer, int secondPlayer) {
//		PentagoClientModel game = new PentagoClientModel(firstPlayer, secondPlayer);
//		currentPartners.put(firstPlayer, secondPlayer);
//		currentPartners.put(secondPlayer, firstPlayer);
//		game.startGame();
//		return game;
//	}
	
//	public boolean makeMove(PentagoSpace[][] masterArray, int playerID){
//		PentagoClientModel game = currentPartners.get(playerID);
//		if(game == null)
//			return false;
//		if (!isPlayersTurn(playerID)) 
//			return false;
//		game.makeMove(masterArray);
//		return true;
//	}
	
	public void makeMatch(int player1, int player2){
		if(!currentPartners.containsKey(player1) && !currentPartners.containsKey(player1)){
			currentPartners.put(player1, player2);
			currentPartners.put(player2, player1);
		}
		removeFromWaitingPlayers(player1);
		removeFromWaitingPlayers(player2);
	}
	
	public void clearCurrentPartners(){
		currentPartners.clear();
	}
	
	public void clearWaitingPlayers(){
		waitingPlayers.clear();
	}
	
//	public boolean isPlayersTurn(int playerID) {
//		PentagoClientModel game = currentPartners.get(playerID);
//		if (game == null)
//			return false;
//		return game.getIdCurrentPlayer() == playerID;
//	}
	
//	public boolean isWinner(int playerID) {
//		PentagoClientModel game = currentPartners.get(playerID);
//		if (game == null)
//			return false;
//		return game.winnerExists() && (playerID == game.getIdCurrentPlayer());
//	}
	
//	public boolean isTie(int playerID) {
//		PentagoClientModel game = currentPartners.get(playerID);
//		if (game == null)
//			return false;
//		return game.tieExists();
//	}
	
	public int getNextPlayer() throws InterruptedException {
		return waitingPlayers.take();
	}
	
	public void removeFromWaitingPlayers(int playerID) {
		if(waitingPlayers.contains(playerID)){
			waitingPlayers.remove(playerID);
		}
	}
	
	public void removeFromCurrentPartners(int playerID){
		currentPartners.remove(playerID);
	}
	
	public ConcurrentHashMap<Integer, Integer> getCurrentPartners(){
		return currentPartners;
	}
	
	public LinkedBlockingQueue<Integer> getWaitingPlayers(){
		return waitingPlayers;
	}

	public int getOpponent(int playerID) {
		if(currentPartners.containsKey(playerID))
			return currentPartners.get(playerID);
		return -1;
	}
	
	}
