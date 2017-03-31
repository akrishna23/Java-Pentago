import java.awt.Color; 
import java.util.HashMap;


public class PentagoClientModel{

	final static Color WHITE = Color.WHITE;
	final static Color BLACK = new Color(64, 12, 12);
	private boolean gameInProgress;
	private boolean color; //true is W, false is B
	private boolean turnColor;
	private boolean winnerColor;
	private boolean hasOpponent;
	private int turnsPlayed;
	private PentagoSpace[][] arrayQ0;
	private PentagoSpace[][] arrayQ1;
	private PentagoSpace[][] arrayQ2;
	private PentagoSpace[][] arrayQ3;
	private PentagoSpace[][] masterArray;
	private HashMap<Integer, Coordinate> numberMap;
	
	public PentagoClientModel(){	
		arrayQ0 = new PentagoSpace[3][3];
		arrayQ1 = new PentagoSpace[3][3];
		arrayQ2 = new PentagoSpace[3][3];
		arrayQ3 = new PentagoSpace[3][3];
		masterArray = new PentagoSpace[6][6];
		numberMap = new HashMap<Integer, Coordinate>();
		initializeArraysAndNumberMap();		
		updateMergeToMasterArray();
	}

	public void startGame(){
		initializeArraysAndNumberMap();
		updateMergeToMasterArray();
		turnsPlayed = 0;
		setGameInProgress(true);
	}
	
	public void clearBoard(){
		initializeArraysAndNumberMap();
		updateMergeToMasterArray();
		turnsPlayed = 0;
	}
	
	public void endGame(){
		setHasOpponent(false);
		setGameInProgress(false);
	}
	
	public void makeMove(PentagoSpace[][] masterArray){
		setMasterArray(masterArray);
		updateSplitToSmallerArrays();
		switchTurnColor();
		turnsPlayed++;
	}
	
	public void addMarker(int i, int j){	
		Color markerColor;
		if(turnColor)
			markerColor = WHITE;
		else 
			markerColor = BLACK;
		masterArray[i][j].addPentagoMarker(markerColor);
	}
	
	public int getTurnsPlayed(){
		return turnsPlayed;
	}
	
	public void setTurnsPlayed(int turns){
		turnsPlayed = turns;
	}
	
	public void setHasOpponent(boolean hasOpponent){
		this.hasOpponent = hasOpponent;
	}
	
	public boolean getHasOpponent(){
		return hasOpponent;
	}
	
	public boolean getWinnerColor(){
		return winnerColor;
	}
	
	public boolean tieExists(){
		if(turnsPlayed == 36)
			return true;
		return false;
	}
	
	public void setMasterArray(PentagoSpace[][] array){
		this.masterArray = array;
	}
	
//	public int getIdCurrentPlayer() {
//		return idCurrentPlayer;
//	}
//
//	public void setIdCurrentPlayer(int idCurrentPlayer) {
//		this.idCurrentPlayer = idCurrentPlayer;
//	}
	
	public boolean getColor(){
		return color;
	}
	
	public void setColor(boolean color){
		this.color = color;
	}
	
	public void switchTurnColor(){
		if(turnColor)
			turnColor = false;
		else
			turnColor = true;
	}
	
	public void setTurnColor(boolean turnColor){
		this.turnColor = turnColor;
	}
	
	public boolean winnerExists(){
		if(turnsPlayed < 8)
			return false;
		if(testVerticalsWinner(BLACK) || testVerticalsWinner(WHITE) ||
				testHorizontalsWinner(BLACK) || testHorizontalsWinner(WHITE) ||
				testNegativeDiagonalWinner(WHITE) || testNegativeDiagonalWinner(BLACK) ||
				testPositiveDiagonalWinner(WHITE) || testPositiveDiagonalWinner(BLACK))
			return true;			
		return false;
		
	}

	private boolean testVerticalsWinner(Color winnerColor){
		int count = 0;
		for(int i = 0; i < 6; i++){ // test verticals
			count = 0;
			for(int j = 0; j < 6; j++){
				if(masterArray[i][j].getMarker() != null){
					if(masterArray[i][j].getMarker().getColor().equals(winnerColor)){
						count++;
					}
					else if(count <5 && !masterArray[i][j].getMarker().getColor().equals(winnerColor)){
						count = 0;
					}
				}
			}
			if(count(count, winnerColor))
				return true;
		}
		return false;
	}
	
	private boolean testHorizontalsWinner(Color winnerColor){		
		int count = 0;
		for(int i = 0; i < 6; i++){
			count = 0;
			for(int j = 0; j < 6; j++){
				if(masterArray[j][i].getMarker() != null){
					if(masterArray[j][i].getMarker().getColor().equals(winnerColor)){
						count++;
					}
					else if(count <5 && !masterArray[j][i].getMarker().getColor().equals(winnerColor)){
						count = 0;
					}
				}
			}
			if(count(count, winnerColor))
				return true;
		}
		return false;
	}
	
	private boolean testNegativeDiagonalWinner(Color winnerColor){
		int count = 0;	
		int i = 0;
		int j = 0;
		while(i<6 && j<6){
			if(masterArray[i][j].getMarker() != null){
				if(masterArray[i][j].getMarker().getColor().equals(winnerColor)){
					count++;
				}
				else if(count <5 && !masterArray[i][j].getMarker().getColor().equals(winnerColor)){
					count = 0;
				}
			}
			i++;
			j++;		
		}
		if(count(count, winnerColor))
			return true;
			
		
		count = 0;
		System.out.println("WORKS count" + count);
		i = 0;
		j = 0;
		while(i<6 && j<5){
			if(masterArray[i][j+1].getMarker() != null){
				if(masterArray[i][j+1].getMarker().getColor().equals(winnerColor)){
					count++;
				}
				else if(count <5 && !masterArray[i][j+1].getMarker().getColor().equals(winnerColor)){
					count = 0;
				}
			}
			i++;
			j++;		
		}
		if(count(count, winnerColor))
			return true;
		
		count = 0;
		System.out.println("WORKS count" + count);
		i = 0;
		j = 0;
		while(i<5 && j<6){
			if(masterArray[i+1][j].getMarker() != null){
				if(masterArray[i+1][j].getMarker().getColor().equals(winnerColor)){
					count++;
				}
				else if(count <5 && !masterArray[i+1][j].getMarker().getColor().equals(winnerColor)){
					count = 0;
				}
			}
			i++;
			j++;		
		}
		if(count(count, winnerColor))
			return true;
		
		return false;
	}
	
	private boolean testPositiveDiagonalWinner(Color winnerColor){
		int count = 0;	
		int i = 0;
		int j = 5;
		while(i<6 && j>0){
			if(masterArray[i][j].getMarker() != null){
				if(masterArray[i][j].getMarker().getColor().equals(winnerColor)){
					count++;
				}
				else if(count <5 && !masterArray[i][j].getMarker().getColor().equals(winnerColor)){
					count = 0;
				}
			}
			i++;
			j--;		
		}
		if(count(count, winnerColor))
			return true;
				
		count = 0;
		System.out.println("WORKS count" + count);
		i = 0;
		j = 5;
		while(i<6 && j>1){
			if(masterArray[i][j-1].getMarker() != null){
				if(masterArray[i][j-1].getMarker().getColor().equals(winnerColor)){
					count++;
				}
				else if(count <5 && !masterArray[i][j-1].getMarker().getColor().equals(winnerColor)){
					count = 0;
				}
			}
			i++;
			j--;		
		}
		if(count(count, winnerColor))
			return true;
		
		count = 0;
		System.out.println("WORKS count" + count);
		i = 0;
		j = 5;
		while(i<5 && j>0){
			if(masterArray[i+1][j].getMarker() != null){
				if(masterArray[i+1][j].getMarker().getColor().equals(winnerColor)){
					count++;
				}
				else if(count <5 && !masterArray[i+1][j].getMarker().getColor().equals(winnerColor)){
					count = 0;
				}
			}
			i++;
			j--;		
		}
		if(count(count, winnerColor))
			return true;
		
		return false;
	}
	
	public boolean count(int count, Color winColor){
		if(count >= 5){
			if(winColor.equals(WHITE))
				this.winnerColor = true;
			else if(winColor.equals(BLACK))
				this.winnerColor = false;
			return true;
		}
		return false;
	}
	
	public void processTurnButton(String turnButton){
		System.out.println("model is processing " + turnButton);
		if(turnButton.equals("downRight")){
			rotateSmallArrayCounterClockwise(arrayQ0, 0);
		}
		else if(turnButton.equals("rightDown")){
			rotateSmallArrayClockwise(arrayQ0, 0);
		}
		else if(turnButton.equals("leftDown")){
			rotateSmallArrayCounterClockwise(arrayQ2, 2);
		}
		else if(turnButton.equals("downLeft")){
			rotateSmallArrayClockwise(arrayQ2, 2);
		}
		else if(turnButton.equals("upLeft")){
			rotateSmallArrayCounterClockwise(arrayQ3, 3);
		}
		else if(turnButton.equals("leftUp")){
			rotateSmallArrayClockwise(arrayQ3, 3);
		}
		else if(turnButton.equals("rightUp")){
			rotateSmallArrayCounterClockwise(arrayQ1, 1);
		}
		else if(turnButton.equals("upRight")){
			rotateSmallArrayClockwise(arrayQ1, 1);
		}
		
	}
	
	private void rotateSmallArrayCounterClockwise(PentagoSpace[][] array, int quadrant){
		PentagoSpace[][] result = new PentagoSpace[3][3];
		
		for(int i = 0; i < array.length; i++){
			for(int j = 0; j < array[i].length; j++){
				result[i][j] = new PentagoSpace(array[i][j].getX(), array[i][j].getY());
				result[i][j].setMarker(null);
				result[i][j].setIsOccupied(false);
			}
		}
		
		if(array[2][0].getMarker() != null){
			result[0][0].setMarker(new PentagoMarker(result[0][0].getX(), result[0][0].getY(), array[2][0].getMarker().getColor()));
			result[0][0].setIsOccupied(true);
		}
		if(array[2][1].getMarker() != null){
			result[1][0].setMarker(new PentagoMarker(result[1][0].getX(), result[1][0].getY(), array[2][1].getMarker().getColor()));
			result[1][0].setIsOccupied(true);
		}
		if(array[2][2].getMarker() != null){
			result[2][0].setMarker(new PentagoMarker(result[2][0].getX(), result[2][0].getY(), array[2][2].getMarker().getColor()));
			result[2][0].setIsOccupied(true);
		}
		if(array[1][2].getMarker() != null){
			result[2][1].setMarker(new PentagoMarker(result[2][1].getX(), result[2][1].getY(), array[1][2].getMarker().getColor()));
			result[2][1].setIsOccupied(true);
		}
		if(array[0][2].getMarker() != null){
			result[2][2].setMarker(new PentagoMarker(result[2][2].getX(), result[2][2].getY(), array[0][2].getMarker().getColor()));
			result[2][2].setIsOccupied(true);
		}
		if(array[0][1].getMarker() != null){
			result[1][2].setMarker(new PentagoMarker(result[1][2].getX(), result[1][2].getY(), array[0][1].getMarker().getColor()));
			result[1][2].setIsOccupied(true);
		}
		if(array[0][0].getMarker() != null){
			result[0][2].setMarker(new PentagoMarker(result[0][2].getX(), result[0][2].getY(), array[0][0].getMarker().getColor()));
			result[0][2].setIsOccupied(true);
		}
		if(array[1][0].getMarker() != null){
			result[0][1].setMarker(new PentagoMarker(result[0][1].getX(), result[0][1].getY(), array[1][0].getMarker().getColor()));
			result[0][1].setIsOccupied(true);
		}
		if(array[1][1].getMarker() != null){
			result[1][1].setMarker(new PentagoMarker(result[1][1].getX(), result[1][1].getY(), array[1][1].getMarker().getColor()));
			result[1][1].setIsOccupied(true);
		}
		
		if(quadrant == 0)
			arrayQ0 = result;
		else if(quadrant == 1)
			arrayQ1 = result;
		else if(quadrant == 2)
			arrayQ2 = result;
		else{
			arrayQ3 = result;
		}
	}
	
	private void rotateSmallArrayClockwise(PentagoSpace[][] array, int quadrant){
		
		PentagoSpace[][] result = new PentagoSpace[3][3];
		
		for(int i = 0; i < array.length; i++){
			for(int j = 0; j < array[i].length; j++){
				result[i][j] = new PentagoSpace(array[i][j].getX(), array[i][j].getY());
				result[i][j].setMarker(null);
				result[i][j].setIsOccupied(false);
			}
		}

		if(array[0][0].getMarker() != null){ //works
			result[2][0].setMarker(new PentagoMarker(result[2][0].getX(), result[2][0].getY(), array[0][0].getMarker().getColor()));
			result[2][0].setIsOccupied(true);
		}
		if(array[1][0].getMarker() != null){ //works
			result[2][1].setMarker(new PentagoMarker(result[2][1].getX(), result[2][1].getY(), array[1][0].getMarker().getColor()));
			result[2][1].setIsOccupied(true);
		}
		if(array[2][0].getMarker() != null){
			result[2][2].setMarker(new PentagoMarker(result[2][2].getX(), result[2][2].getY(), array[2][0].getMarker().getColor()));
			result[2][2].setIsOccupied(true);
		}
		if(array[2][1].getMarker() != null){// work
			result[1][2].setMarker(new PentagoMarker(result[1][2].getX(), result[1][2].getY(), array[2][1].getMarker().getColor()));
			result[1][2].setIsOccupied(true);
		}
		if(array[2][2].getMarker() != null){// work
			result[0][2].setMarker(new PentagoMarker(result[0][2].getX(), result[0][2].getY(), array[2][2].getMarker().getColor()));
			result[0][2].setIsOccupied(true);
		}
		if(array[1][2].getMarker() != null){
			result[0][1].setMarker(new PentagoMarker(result[0][1].getX(), result[0][1].getY(), array[1][2].getMarker().getColor()));
			result[0][1].setIsOccupied(true);
		}
		if(array[0][2].getMarker() != null){ //works
			result[0][0].setMarker(new PentagoMarker(result[0][0].getX(), result[0][0].getY(), array[0][2].getMarker().getColor()));
			result[0][0].setIsOccupied(true);
		}
		if(array[0][1].getMarker() != null){ //works
			result[1][0].setMarker(new PentagoMarker(result[1][0].getX(), result[1][0].getY(), array[0][1].getMarker().getColor()));
			result[1][0].setIsOccupied(true);
		}
		if(array[1][1].getMarker() != null){
			result[1][1].setMarker(new PentagoMarker(result[1][1].getX(), result[1][1].getY(), array[1][1].getMarker().getColor()));
			result[1][1].setIsOccupied(true);
		}
		
		if(quadrant == 0)
			arrayQ0 = result;
		else if(quadrant == 1)
			arrayQ1 = result;
		else if(quadrant == 2)
			arrayQ2 = result;
		else{
			arrayQ3 = result;
		}
	}
	
	
	public void updateMergeToMasterArray(){//copies 4 small arrays into master array
		for(int i = 0; i < arrayQ0.length; i++){
			for(int j = 0; j < arrayQ0[i].length; j++){
				masterArray[i][j] = arrayQ0[i][j];
			}
		}
		for(int i = 0; i < arrayQ1.length; i++){
			for(int j = 3; j < arrayQ1[i].length + 3; j++){
				masterArray[i][j] = arrayQ1[i][j-3];
			}
		}
		for(int i = 3; i < 6; i++){
			for(int j = 0; j < 3; j++){
				masterArray[i][j] = arrayQ2[i-3][j];
			}
		}
		for(int i = 3; i < 6; i++){
			for(int j = 3; j < 6; j++){
				masterArray[i][j] = arrayQ3[i-3][j-3];
			}
		}
	}
	
	public void updateSplitToSmallerArrays(){//copies master array into 4 smaller arrays
		for(int i = 0; i < arrayQ0.length; i++){
			for(int j = 0; j < arrayQ0[i].length; j++){
				arrayQ0[i][j] = masterArray[i][j];
			}
		}
		for(int i = 0; i < arrayQ1.length; i++){
			for(int j = 0; j < arrayQ1[i].length; j++){
				arrayQ1[i][j] = masterArray[i][j+3];
			}
		}
		for(int i = 0; i < arrayQ2.length; i++){
			for(int j = 0; j < arrayQ2[i].length; j++){
				arrayQ2[i][j] = masterArray[i+3][j];
			}
		}
		for(int i = 0; i < arrayQ3.length; i++){
			for(int j = 0; j < arrayQ3[i].length; j++){
				arrayQ3[i][j] = masterArray[i+3][j+3];
			}
		}
	}
	
	public HashMap<Integer, Coordinate> getNumberMap(){
		return numberMap;
	}
	
	public PentagoSpace[][] getMasterArray(){
		return masterArray;
	}
		
	private void initializeArraysAndNumberMap(){		
		int quadrant = 0;
		int startX = 135;
		int startY = 60;
		int count = 1+quadrant*9;
		for(int i = 0; i < arrayQ0.length; i++){
			for(int j = 0; j < arrayQ0[i].length; j++){
				arrayQ0[i][j] = new PentagoSpace(startX+8+55*i, startY+8+55*j);
				arrayQ0[i][j].setNumber(count);
				numberMap.put(arrayQ0[i][j].getNumber(), new Coordinate(arrayQ0[i][j].getX(), arrayQ0[i][j].getY()));
				count++;
			}		
		}		
		quadrant = 1;
		startX = 135;
		startY = 228;
		count = 1+quadrant*9;
		for(int i = 0; i < arrayQ1.length; i++){
			for(int j = 0; j < arrayQ1[i].length; j++){
				arrayQ1[i][j] = new PentagoSpace(startX+8+55*i, startY+8+55*j);
				arrayQ1[i][j].setNumber(count);
				numberMap.put(arrayQ1[i][j].getNumber(), new Coordinate(arrayQ1[i][j].getX(), arrayQ1[i][j].getY()));
				count++;
			}		
		}		
		quadrant = 2;
		startX = 303;
		startY = 60;
		count = 1+quadrant*9;
		for(int i = 0; i < arrayQ2.length; i++){
			for(int j = 0; j < arrayQ2[i].length; j++){
				arrayQ2[i][j] = new PentagoSpace(startX+8+55*i, startY+8+55*j);
				arrayQ2[i][j].setNumber(count);
				numberMap.put(arrayQ2[i][j].getNumber(), new Coordinate(arrayQ2[i][j].getX(), arrayQ2[i][j].getY()));
				count++;
			}		
		}		
		quadrant = 3;
		startX = 303;
		startY = 228;
		count = 1+quadrant*9;
		for(int i = 0; i < arrayQ3.length; i++){
			for(int j = 0; j < arrayQ3[i].length; j++){
				arrayQ3[i][j] = new PentagoSpace(startX+8+55*i, startY+8+55*j);
				arrayQ3[i][j].setNumber(count);
				numberMap.put(arrayQ3[i][j].getNumber(), new Coordinate(arrayQ3[i][j].getX(), arrayQ3[i][j].getY()));
				count++;
			}		
		}
	}

	public boolean isGameInProgress() {
		return gameInProgress;
	}

	public void setGameInProgress(boolean gameInProgress) {
		this.gameInProgress = gameInProgress;
	}

}
