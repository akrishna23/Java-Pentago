package netgame.jeopardy;

import javax.swing.JOptionPane;

public class JeopardyClientMain {
	
	public static void main(String[] args) {
		String host = JOptionPane
				.showInputDialog("Enter the host name of the\ncomputer that hosts Jeopardy:");
		if (host == null || host.trim().length() == 0)
			return;
		String name = JOptionPane
				.showInputDialog("Enter that name that you want\nto use in the game.");
		if (name == null || name.trim().length() == 0)
			return;
		JeopardyClientView view = new JeopardyClientView();
		new JeopardyClientController(view, host, name);
	}

}
