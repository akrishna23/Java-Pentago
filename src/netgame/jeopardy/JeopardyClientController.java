package netgame.jeopardy;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JOptionPane;

import netgame.common.Client;

public class JeopardyClientController implements ActionListener, MouseListener {

	private final static int PORT = 37831;
	private volatile String userName;
	private JeopardyClientConnection connection;
	private JeopardyClientView view;
	private JeopardyClientModel model;

	public JeopardyClientController(JeopardyClientView view,
			final String hostName, String userName) {
		this.view = view;
		view.registerActionListener(this);
		view.registerMouseListener(this);
		this.userName = userName;
		new Thread() {
			public void run() {
				try {
					connection = new JeopardyClientConnection(hostName, PORT);
					connection.send(new StatsMessage("get"));
				} catch (IOException e) {
				}
			}
		}.start();
	}

	public void actionPerformed(ActionEvent evt) {
		if (!view.isSubmissionEnabled())
			return;
		String answer = view.getAnswerText();
		if (answer.trim().equals(""))
			return;
		view.setSubmissionEnabled(false);
		connection.send(new AnswerMessage(answer, view
				.getCurrentlySelectedCol(), view.getCurrentlySelectedRow()));

	}

	public void mousePressed(MouseEvent e) {
		view.setMessageText("");
		int col = view.getColumn(e.getX(), e.getY());
		int row = view.getRow(e.getX(), e.getY());
		if (col == -1 || row == -1)
			return;
		if (model.isCorrect(col, row))
			return;
		view.setCurrentlySelectedCol(col);
		view.setCurrentlySelectedRow(row);
		view.setQuestionText(model.getQuestion(col, row));
		view.setSubmissionEnabled(true);
		view.selectAnswerText();
		view.repaint();
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	// ---------Nested class-------------------
	private class JeopardyClientConnection extends Client {

		public JeopardyClientConnection(String host, int port)
				throws IOException {
			super(host, port);
			setAutoreset(true);
		}

		protected void extraHandshake(ObjectInputStream in,
						ObjectOutputStream out) throws IOException {
			try {
				out.writeObject(userName);
				Boolean userNameAvailable = (Boolean) in.readObject();
				while (!userNameAvailable) {
					String name = JOptionPane
							.showInputDialog("Username already in use. Choose another.");
					out.writeObject(name);
					userNameAvailable = (Boolean) in.readObject();
				}
				String[] categories = (String[]) in.readObject();
				String[][] questions = (String[][]) in.readObject();
				int[][] pointValues = (int[][]) in.readObject();
				model = new JeopardyClientModel(categories, questions, pointValues);
				view.setModel(model);
				view.repaint();
			} catch (Exception e) {
				view.setQuestionText("Error while getting game information.");
			}
		}

		protected void messageReceived(Object message) {
			if (message instanceof AnswerMessage) {
				AnswerMessage msg = (AnswerMessage) message;
				if (msg.correct) {
					model.setCorrect(view.getCurrentlySelectedCol(),
									 view.getCurrentlySelectedRow());
					view.setCurrentlySelectedCol(-1);
					view.setCurrentlySelectedRow(-1);
					view.setQuestionText("");
					view.setMessageText("Correct!");
					view.setAnswerText("");
					view.repaint();

				} else {
					view.setMessageText("Incorrect");
					view.setSubmissionEnabled(true);
					view.selectAnswerText();
					view.repaint();
				}
			} else if (message instanceof StatsMessage) {
				String playerStats = ((StatsMessage) message).playerStats;
				view.updateStats(playerStats);
				view.repaint();
			} else if (message instanceof GameOverMessage) {
				GameOverMessage msg = (GameOverMessage) message;
				view.setQuestionText(msg.winner);
				view.setSubmissionEnabled(false);
				view.repaint();
			}
		}

	}

}
