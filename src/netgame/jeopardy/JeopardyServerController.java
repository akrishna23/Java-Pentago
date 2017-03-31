package netgame.jeopardy;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashSet;

import netgame.common.*;

public class JeopardyServerController extends Hub {

	private JeopardyServerModel model;
	private HashSet<String> userNames = new HashSet<String>();

	public JeopardyServerController(int port, String[] categories,
			String[][] questions, String[][] answers, int[][] scores)
			throws IOException {
		super(port);
		model = new JeopardyServerModel(categories, questions, answers, scores);
		setAutoreset(true);
	}

	protected void messageReceived(int playerID, Object message) {
		if (message instanceof AnswerMessage) {
			AnswerMessage msg = (AnswerMessage) message;
			if (model.checkAnswer(msg.answer, msg.column, msg.row)) {
				msg.correct = true;
				model.setCorrectAnswer(playerID, msg.column, msg.row);
				sendToOne(playerID, msg);
				sendToAll(new StatsMessage(model.getPlayerStats()));
				if (model.numCorrectForPlayer(playerID) == model.numRows
						* model.numCols)
					sendToAll(new GameOverMessage(model.getWinnerString()));
			} else {
				msg.correct = false;
				model.deductWrongAnswer(playerID, msg.column, msg.row);
				sendToOne(playerID, msg);
				sendToAll(new StatsMessage(model.getPlayerStats()));
			}
		} else if (message instanceof StatsMessage) {
			sendToAll(new StatsMessage(model.getPlayerStats()));
		}
	}

	protected void playerConnected(int playerID) {
		sendToAll(model.getPlayerStats());
	}

	protected void playerDisconnected(int playerID) {
		model.removePlayer(playerID);
		sendToAll(model.getPlayerStats());
	}

	protected void extraHandshake(int playerID, ObjectInputStream in,
			ObjectOutputStream out) throws IOException {
		String name;
		try {
			name = (String) in.readObject();
			while (userNames.contains(name)) {
				out.writeObject(false);
				name = (String) in.readObject();
			}
			userNames.add(name);
			out.writeObject(true); //username was available
			out.writeObject(model.categories);
			out.writeObject(model.questions);
			out.writeObject(model.pointValues);
		} catch (ClassNotFoundException e) {
			return;
		}
		model.addPlayer(playerID);
		model.setPlayerName(playerID, name);
	}

}
