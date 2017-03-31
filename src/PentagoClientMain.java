import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import netgame.chatroom.ChatRoomClientController;
import netgame.chatroom.ChatRoomClientView;


public class PentagoClientMain {
	
	public static void main(String[] args) {       
		String host = JOptionPane.showInputDialog(
                "Enter the host IP of the server:");
			if (host == null || host.trim().length() == 0)
				return;
		PentagoClientView view = new PentagoClientView();
        PentagoClientModel model = new PentagoClientModel();
        
        try{
        	new PentagoClientController(model, view, host);
        } catch(Exception e){
        	System.out.println("ERROR: Cannot connect to Server.");
        	e.printStackTrace();
        }
        view.setLocation(382,80); //1365 total width
        view.setSize(600, 560);
        view.setVisible(true);
        view.setResizable(false);
        view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }  
	
}
