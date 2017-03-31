import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import netgame.common.Hub;


public class PentagoServerController extends Hub{

	private PentagoServerModel model;
	private boolean running;
	
	public PentagoServerController(int defaultPort) throws IOException{
		super(defaultPort);
		model = new PentagoServerModel();
		model.clearCurrentPartners();
		model.clearWaitingPlayers();
		setAutoreset(true);
		new Thread(){
			public void start(){
				running = true;
				while(running){
					try{	
//						System.out.println("Running... NUM Waiting = " + model.getWaitingPlayers().size());
						if(model.getWaitingPlayers().size() > 1){
							int firstPlayer = model.getNextPlayer();
							int secondPlayer = model.getNextPlayer();
							if(firstPlayer == secondPlayer){
								int nextPlayer = model.getNextPlayer();
								model.addPlayerToQueue(secondPlayer);
								secondPlayer = nextPlayer;
							}
							if(firstPlayer != secondPlayer){
//								System.out.println("Running... NUM Waiting = " + model.getWaitingPlayers().size());

								model.makeMatch(firstPlayer, secondPlayer);
								Thread.sleep(500);
								PentagoServerController.this.sendToOne(firstPlayer, "START_WHITE");
								PentagoServerController.this.sendToOne(secondPlayer, "START_BLACK");
							}							
						}
					}
					catch(InterruptedException e){
						
					}
				}
			}
		}.start();
	}
	
	protected void messageReceived(int playerID, Object message){
		int opponent = model.getOpponent(playerID);
		if(message instanceof PentagoSpace[][]){
			PentagoSpace[][] move = (PentagoSpace[][]) message;
			this.sendToOne(opponent, move);
		}
		else if(message instanceof String){
			String command = (String) message;
			if (command.equals("NEW_GAME")) {
				try {
					this.sendToOne(playerID, "WAITING");
					model.addPlayerToQueue(playerID);
				}
				catch (InterruptedException e) {
				}
			}
			else if(command.equals("RESIGN")){
				model.removeFromCurrentPartners(playerID);
				this.sendToOne(opponent, command);
			}
			else if(command.equals("YOU_WIN") || command.equals("OPPONENT_WIN")){
				this.sendToOne(opponent, command);
			}
			else if(command.equals("TIE")){
				this.sendToOne(opponent, command);
			}
			else if(command.equals("DISCONNECT")){
				model.removeFromCurrentPartners(playerID);
				this.sendToOne(opponent, command);
			}
			
		}
	}
	
	protected void playerConnected(int playerID){}
	
	protected void playerDisconnected(int playerID) {
		int opponentID = model.getOpponent(playerID);
		model.removeFromCurrentPartners(playerID);
		PentagoServerController.this.sendToOne(opponentID, "DISCONNECT");
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}	
	}

	protected void extraHandshake(int playerID, ObjectInputStream in,
			ObjectOutputStream out) throws IOException {
		try {
			model.addPlayerToQueue(playerID);
		} catch (InterruptedException e) {
			return;
		}
	}
}
