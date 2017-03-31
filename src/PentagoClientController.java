import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

import javax.swing.JOptionPane;

import netgame.common.Client;



public class PentagoClientController implements ActionListener, MouseListener, WindowListener {

	private final static int PORT = 50000;
	private PentagoClientConnection connection;
	private PentagoClientModel model;
	private PentagoClientView view;
	private boolean canClickSpaces;
	private boolean canClickTurnButtons;
	
	public PentagoClientController(PentagoClientModel model, PentagoClientView view, String host){
		this.model = model;
		this.view = view;
		view.registerActionListener(this);
		view.registerMouseListener(this);
		view.registerWindowListener(this);
		
		try {
			System.out.println("CONNECTING");
			connection = new PentagoClientConnection(host, PORT);
			System.out.println("CONNECTION FOUND");
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent evt) {
		String command = evt.getActionCommand();
		if(command.equals("Instructions")){
			view.switchToInstructionsCard();
		}	
		else if(command.equals("New 2 Player Game")){			
			view.switchToGameCard();
			if(!model.getHasOpponent()){
				view.setMainMenuButtonVisibility(true);
				view.setResignButtonClickability(false);
				view.setTurnButtonsVisibility(false);
				view.setTurnsPlayed(0);
				canClickTurnButtons = false;
				canClickSpaces = false;
				model.clearBoard();
				view.updateModel(model);
				view.clearMarkersToDraw();
				view.repaint();
				connection.send("NEW_GAME");
			}
		}
		else if(command.equals("Exit")){
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			connection.disconnect();
			System.exit(0);
		}
		else if(command.equals("Back To Main Menu")){
			view.switchToMenuCard();
		}
		else if(command.equals("Main Menu")){
			view.switchToMenuCard();
			if(model.getHasOpponent()){
				try{
				connection.send("DISCONNECT");
				} 
				catch(IllegalStateException e){
					System.out.println("Connection is already closed.");
				}			
			}
		}
		else if(command.equals("Resign")){
			connection.send("RESIGN");
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			model.endGame();
			view.switchToMenuCard();
		}		
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		if(canClickSpaces){
			for(int i = 0; i < model.getMasterArray().length; i++){
				for(int j = 0; j < model.getMasterArray()[i].length; j++){
					PentagoSpace a = model.getMasterArray()[i][j];
					if(!a.getIsOccupied() && a.containsPoint(x, y)){						
						model.addMarker(i, j);					
						view.updateModel(model);
						view.repaint();
						view.setTurnButtonsVisibility(true);
						canClickTurnButtons = true;
						canClickSpaces = false;			
						checkForWin();
						checkForTie();					
					}
				}
			}
		}	
		
		if(canClickTurnButtons){
			if(108 < x && x < 151 && 4 < y && y < 44){
				model.processTurnButton("rightDown");
				afterTurnClicked();
			}
			else if(445 < x && x < 488 && 4 < y && y < 44){
				model.processTurnButton("leftDown");
				afterTurnClicked();
			}
			else if(486 < x && x < 526 && 44 < y && y < 87){
				model.processTurnButton("downLeft");
				afterTurnClicked();
			}
			else if(486 < x && x < 526 && 360 < y && y < 403){
				model.processTurnButton("upLeft");
				afterTurnClicked();
			}
			else if(445 < x && x < 488 && 400 < y && y < 440){
				model.processTurnButton("leftUp");
				afterTurnClicked();
			}
			else if(108 < x && x < 151 && 400 < y && y < 440){
				model.processTurnButton("rightUp");
				afterTurnClicked();
			}
			else if(69 < x && x < 109 && 360 < y && y < 403){
				model.processTurnButton("upRight");
				afterTurnClicked();
			}
			else if(69 < x && x < 109 && 44 < y && y < 87){
				model.processTurnButton("downRight");
				afterTurnClicked();
			}
		}		
	}

	public void afterTurnClicked(){
		model.updateMergeToMasterArray();
		model.makeMove(model.getMasterArray());
		view.updateModel(model);
		view.incrementTurnsPlayed();
		view.repaint();
		view.setTurnButtonsVisibility(false);
		canClickTurnButtons = false;
		if(checkForWin() || checkForTie()){
			return;
		}
		view.updateMessageSwitchTurn("Opponent");
		connection.send(model.getMasterArray());
	}
	
	public void afterGame(String message){
		canClickTurnButtons = false;
		canClickSpaces = false;
		view.setTurnButtonsVisibility(false);
		view.setMainMenuButtonVisibility(true);
		view.setResignButtonClickability(false);
		view.updateMessage(message);
		view.repaint();
	}
	
	public boolean checkForTie(){
		if(model.tieExists()){					
			model.endGame();
			afterGame("It's a TIE!");
			connection.send(model.getMasterArray());
			connection.send("TIE");
			return true;
		}
		return false;
	}
	
	public boolean checkForWin(){
		if(model.winnerExists()){
			model.endGame();
			if(model.getWinnerColor() == model.getColor()){
				afterGame("You WIN!");
				connection.send(model.getMasterArray());
				connection.send("OPPONENT_WIN");
			}
			else{
				afterGame("Opponent WINS!");
				connection.send(model.getMasterArray());
				connection.send("YOU_WIN");
			}
			return true;
		}
		return false;
	}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}
	
	//--------------------------------------------------------------
	private class PentagoClientConnection extends Client{
		
		public PentagoClientConnection(String host, int port) throws IOException{
			super(host, port);
			setAutoreset(true);			
		}

		@Override
		protected void messageReceived(Object message) {
			// TODO Auto-generated method stub
			if(message instanceof PentagoSpace[][]){
				model.setMasterArray((PentagoSpace[][]) message);
				model.updateSplitToSmallerArrays();
				model.setTurnsPlayed(model.getTurnsPlayed() + 1);
				view.setTurnsPlayed(model.getTurnsPlayed());
				view.updateModel(model);
				view.updateMessage("Your Turn");
				view.repaint();
				canClickSpaces = true;
				model.switchTurnColor();
			}
			else if(message instanceof String){
				String messageString = (String) message;
				if(messageString.equals("WAITING")){
					view.updateMessage("Waiting for an Opponent...");
				}
				else if(messageString.equals("START_WHITE")){
					System.out.println("CLIENT RECEIVED 'START_WHITE'");
					view.setMainMenuButtonVisibility(false);
					view.setResignButtonClickability(true);
					view.setTurnButtonsVisibility(false);
					view.updateMessage("Your Turn");
					model.startGame();
					view.updateModel(model);
					model.setTurnsPlayed(0);
					view.setTurnsPlayed(0);
					model.setColor(true);
					model.setTurnColor(true);
					model.setHasOpponent(true);
					canClickTurnButtons = false;
					canClickSpaces = true;
					view.repaint();
				}
				else if(messageString.equals("START_BLACK")){
					System.out.println("CLIENT RECEIVED 'START_BLACK'");
					view.setMainMenuButtonVisibility(false);
					view.setResignButtonClickability(true);
					view.setTurnButtonsVisibility(false);
					view.updateMessage("Opponent's Turn");
					model.startGame();
					view.updateModel(model);
					view.setTurnsPlayed(0);
					model.setColor(false);
					model.setTurnColor(true);
					model.setHasOpponent(true);
					canClickTurnButtons = false;
					canClickSpaces = false;
					view.repaint();
				}
				else if(messageString.equals("TIE")){
					model.endGame();
					afterGame("It's a TIE!");
				}
				else if(messageString.equals("YOU_WIN")){
					model.endGame();
					afterGame("You WIN!");
				}
				else if(messageString.equals("OPPONENT_WIN")){
					model.endGame();
					afterGame("Opponent WINS!");
				}
				else if(messageString.equals("RESIGN")){
					model.endGame();
					afterGame("Opponent Resigned");
				}
				else if(messageString.equals("DISCONNECT")){
					if(model.getHasOpponent())
						afterGame("Opponent Disconnected");
					else
						afterGame("Game Over");
					model.endGame();
					
					try {
						Thread.sleep(500);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		
	}
	//--------------------------------------------------------------

	@Override
	public void windowOpened(WindowEvent e) {}

	@Override
	public void windowClosing(WindowEvent ev) {
		view.dispose();
		connection.disconnect(); // Send a disconnect message to the hub.
		try {
			Thread.sleep(500); // Wait one-half second to allow the message to
								// be sent.
		} catch (InterruptedException e) {
		}
		System.exit(0);
	}

	@Override
	public void windowClosed(WindowEvent e) {}

	@Override
	public void windowIconified(WindowEvent e) {}

	@Override
	public void windowDeiconified(WindowEvent e) {}

	@Override
	public void windowActivated(WindowEvent e) {}

	@Override
	public void windowDeactivated(WindowEvent e) {}
}
