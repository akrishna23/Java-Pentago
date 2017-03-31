import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import netgame.jeopardy.JeopardyClientModel;


public class PentagoClientView extends JFrame{
	
	
	public static final String INSTRUCTIONS_COMMAND = "Instructions";
	public static final String NEW2PLAYERGAME_COMMAND = "New 2 Player Game";
	public static final String EXIT_COMMAND = "Exit";
	public static final String BACKTOMAINMENU_COMMAND = "Back To Main Menu";
	public static final String RESIGN_COMMAND = "Resign";
	public static final String MAINMENU_COMMAND = "Main Menu";
	
	private PentagoClientModel model;
	private PentagoPanel pentagoPanel;
	private CardLayout cardLayout;
	private JPanel cards;
	private JPanel menuCard;
	private JPanel gameCard;
	private JPanel instructionsCard;
	private JButton instructionsButton;
	private JButton new2PlayerGameButton;
	private JButton exitButton;
	private JButton backToMainMenuButton;
	private JButton resignButton;
	private JButton mainMenuButton;
	private JLabel messageDisplay;
	private JLabel turnsPlayedLabel;	
	private JLabel endGameLabel;
	private JLabel downLeft, downRight, leftDown, leftUp, rightDown, rightUp, upLeft, upRight;
	private JLabel[] turnButtons;
	private String message = "Welcome to Pentago!"; //max number of characters is 18	
	private int turnsPlayed;
	private HashMap<Integer, Coordinate> numberMap;
	private ArrayList<PentagoMarker> markersToDraw;
	
	public PentagoClientView(){
		super("PENTAGO");
		pentagoPanel = new PentagoPanel();
		setBackground(Color.BLACK);
		setLayout(new BorderLayout(2,2));
		cardLayout = new CardLayout();
		cards = new JPanel(cardLayout);
		this.setContentPane(cards);
		
		model = new PentagoClientModel();
		setNumberMap(model.getNumberMap());
		markersToDraw = new ArrayList<PentagoMarker>();
		
		//2 panels for card layout
		menuCard = new JPanel(new BorderLayout());
		gameCard = new JPanel(new BorderLayout());
		instructionsCard = new JPanel(new BorderLayout());
		
		//within menuCard
		JPanel menuButtons = new JPanel();
		menuButtons.setLayout(new BoxLayout(menuButtons, BoxLayout.Y_AXIS));
		instructionsButton = new JButton(INSTRUCTIONS_COMMAND);
		instructionsButton.setAlignmentX(CENTER_ALIGNMENT);
		new2PlayerGameButton = new JButton(NEW2PLAYERGAME_COMMAND);
		new2PlayerGameButton.setAlignmentX(CENTER_ALIGNMENT);
		exitButton = new JButton(EXIT_COMMAND);
		exitButton.setAlignmentX(CENTER_ALIGNMENT);
		menuButtons.add(Box.createRigidArea(new Dimension(0,50)));
		menuButtons.add(instructionsButton);
		menuButtons.add(Box.createRigidArea(new Dimension(0,50)));
		menuButtons.add(new2PlayerGameButton);
		menuButtons.add(Box.createRigidArea(new Dimension(0,50)));
		menuButtons.add(exitButton);
		menuButtons.setBackground(new Color(51,25,0));
		//organize menuButtons!!!
		JPanel titlePanel = new JPanel();
		JLabel title = new JLabel();
		title.setFont(new Font("Monotype Corsiva", Font.BOLD, 90));
		title.setText("PENTAGO");
		title.setForeground(new Color(250,164,96));
		title.setHorizontalAlignment(0);
		titlePanel.setBackground(new Color(51,25,0));
		titlePanel.add(title);		
		menuCard.add(menuButtons, BorderLayout.CENTER);
		menuCard.add(titlePanel, BorderLayout.NORTH);
		
		//within instructionsCard
		JPanel backPanel = new JPanel();
		JPanel instructionsPanel = new JPanel(new GridLayout(10,1));	
		backToMainMenuButton = new JButton(BACKTOMAINMENU_COMMAND);
		backPanel.add(backToMainMenuButton);
		backPanel.setBackground(new Color(51,25,0));
		instructionsPanel.setBackground(new Color(139,69,19));
		JLabel firstLine = new JLabel("How To Play:");	
		JLabel secondLine = new JLabel("The object of the game is to get 5 markers in a row.");
		JLabel thirdLine = new JLabel("You and your opponent will alternate turns.");
		JLabel fourthLine = new JLabel("On your turn, you will place a marker");
		JLabel fourthLine2 = new JLabel("    on any empty space.");
		JLabel fifthLine = new JLabel("After placing a marker, you will choose one of the four quadrants");
		JLabel fifthLine2 = new JLabel("    of the board to be rotated 90 degrees clockwise or counterclockwise.");
		JLabel sixthLine = new JLabel("The goal is simple, but the ever-changing board may");
		JLabel sixthLine2 = new JLabel("    make the game challenging.");
		JLabel seventhLine = new JLabel("Try to win in as little turns as possible!");
		firstLine.setFont(new Font("Monotype Corsiva", Font.BOLD, 20));
		secondLine.setFont(new Font("Monotype Corsiva", Font.BOLD, 20));
		thirdLine.setFont(new Font("Monotype Corsiva", Font.BOLD, 20));
		fourthLine.setFont(new Font("Monotype Corsiva", Font.BOLD, 20));
		fourthLine2.setFont(new Font("Monotype Corsiva", Font.BOLD, 20));
		fifthLine.setFont(new Font("Monotype Corsiva", Font.BOLD, 20));
		fifthLine2.setFont(new Font("Monotype Corsiva", Font.BOLD, 20));
		sixthLine.setFont(new Font("Monotype Corsiva", Font.BOLD, 20));
		sixthLine2.setFont(new Font("Monotype Corsiva", Font.BOLD, 20));
		seventhLine.setFont(new Font("Monotype Corsiva", Font.BOLD, 20));
		firstLine.setForeground(Color.WHITE);
		secondLine.setForeground(Color.WHITE);
		thirdLine.setForeground(Color.WHITE);
		fourthLine.setForeground(Color.WHITE);
		fourthLine2.setForeground(Color.WHITE);
		fifthLine.setForeground(Color.WHITE);
		fifthLine2.setForeground(Color.WHITE);
		sixthLine.setForeground(Color.WHITE);
		sixthLine2.setForeground(Color.WHITE);
		seventhLine.setForeground(Color.WHITE);
		instructionsPanel.add(firstLine);
		instructionsPanel.add(secondLine);
		instructionsPanel.add(thirdLine);
		instructionsPanel.add(fourthLine);
		instructionsPanel.add(fourthLine2);
		instructionsPanel.add(fifthLine);
		instructionsPanel.add(fifthLine2);
		instructionsPanel.add(sixthLine);
		instructionsPanel.add(sixthLine2);
		instructionsPanel.add(seventhLine);
		instructionsCard.add(backPanel, BorderLayout.NORTH);
		instructionsCard.add(instructionsPanel, BorderLayout.CENTER);
		
		
		//within gameCard
		JPanel topPanel = new JPanel();
		JPanel boardPanel = new JPanel();
		JPanel bottomPanel = new JPanel(new GridLayout(1,5));
		turnsPlayedLabel = new JLabel("Turns Played: " + Integer.toString(turnsPlayed));
		turnsPlayedLabel.setFont(new Font("Calibri", Font.BOLD, 19));
		turnsPlayedLabel.setForeground(Color.WHITE);
		topPanel.setBackground(new Color(51,25,0));
		boardPanel.setBackground(new Color(139,69,19));
		bottomPanel.setBackground(new Color(51,25,0));
		endGameLabel = new JLabel("dd");
		endGameLabel.setFont(new Font("dd", Font.BOLD, 50));
		boardPanel.add(endGameLabel);
		mainMenuButton = new JButton(MAINMENU_COMMAND);
		resignButton = new JButton(RESIGN_COMMAND);
		messageDisplay = new JLabel(message);
		messageDisplay.setFont(new Font("Calibri", Font.BOLD, 20));
		messageDisplay.setHorizontalAlignment(0);
		messageDisplay.setForeground(Color.white);	
		ImageIcon downLeftI = new ImageIcon(getClass().getResource("/downLeft.png"));
		ImageIcon downRightI = new ImageIcon(getClass().getResource("/downRight.png"));
		ImageIcon leftDownI = new ImageIcon(getClass().getResource("/leftDown.png"));
		ImageIcon leftUpI = new ImageIcon(getClass().getResource("/leftUp.png"));
		ImageIcon rightDownI = new ImageIcon(getClass().getResource("/rightDown.png"));
		ImageIcon rightUpI = new ImageIcon(getClass().getResource("/rightUp.png"));
		ImageIcon upLeftI = new ImageIcon(getClass().getResource("/upLeft.png"));
		ImageIcon upRightI = new ImageIcon(getClass().getResource("/upRight.png"));
		pentagoPanel.setLayout(null);
		downLeft = new JLabel(downLeftI);
		downRight = new JLabel(downRightI);
		leftDown = new JLabel(leftDownI);
		leftUp = new JLabel(leftUpI);
		rightDown = new JLabel(rightDownI);
		rightUp = new JLabel(rightUpI);
		upLeft = new JLabel(upLeftI);
		upRight = new JLabel(upRightI);		
		downLeft.setLocation(486, 44);
		downRight.setLocation(69, 44);
		leftDown.setLocation(445, 4);
		leftUp.setLocation(445, 400);
		rightDown.setLocation(108, 4);
		rightUp.setLocation(108, 400);
		upLeft.setLocation(486, 360);
		upRight.setLocation(69, 360);
		turnButtons = new JLabel[]{downLeft, downRight, leftDown, leftUp, rightDown, rightUp, upLeft, upRight};
		for(JLabel pic : turnButtons){
			pic.setSize(43, 43);
			pentagoPanel.add(pic);
			pic.setVisible(false);
		}
		topPanel.add(resignButton);
		topPanel.add(turnsPlayedLabel);
		topPanel.add(mainMenuButton);
		mainMenuButton.setVisible(false);
		bottomPanel.add(messageDisplay);		
		gameCard.add(topPanel, BorderLayout.NORTH);
		gameCard.add(boardPanel, BorderLayout.CENTER);
		gameCard.add(pentagoPanel);
		gameCard.add(bottomPanel, BorderLayout.SOUTH);
		
		//adding to cards
		cards.add(menuCard, "Menu Card");
		cards.add(gameCard, "Game Card");
		cards.add(instructionsCard, "Instructions Card");
		
		cardLayout.show(cards, "Menu Card");
		
	}
	
	public void updateMessage(String message){
		this.message = message;
		messageDisplay.setText(message);
	}
	
	public void updateMessageSwitchTurn(String username){
		messageDisplay.setText(username + "'s turn.");
	}
	
	public void incrementTurnsPlayed(){
		turnsPlayed++;
		turnsPlayedLabel.setText("Turns Played: " + Integer.toString(turnsPlayed));
	}
	
	public void setTurnsPlayed(int num){
		turnsPlayed = num;
		turnsPlayedLabel.setText("Turns Played: " + num);
	}
	
	public void updateModel(PentagoClientModel model) {
		this.model = model;
	}
	
	public void registerActionListener(ActionListener listener) {
		instructionsButton.addActionListener(listener);
		new2PlayerGameButton.addActionListener(listener);
		exitButton.addActionListener(listener);
		backToMainMenuButton.addActionListener(listener);
		resignButton.addActionListener(listener);
		mainMenuButton.addActionListener(listener);
	}
	
	public void registerMouseListener(MouseListener listener) {
		pentagoPanel.addMouseListener(listener);
	}
	
	public void registerWindowListener(WindowListener listener) {
		   this.addWindowListener(listener);
	}
	
	public void switchToGameCard(){
		cardLayout.show(cards, "Game Card");
	}
	
	public void switchToInstructionsCard(){
		cardLayout.show(cards, "Instructions Card");
	}
	
	public void setTurnButtonsVisibility(boolean visibility){
		for(JLabel pic : turnButtons){
			pic.setVisible(visibility);
		}
	}
	public void switchToMenuCard(){
		cardLayout.show(cards, "Menu Card");
	}
	
	public void setNumberMap(HashMap<Integer, Coordinate> numberMap){
		this.numberMap = numberMap;
	}
	
	public HashMap<Integer, Coordinate> getNumberMap(){
		return numberMap;
	}
	
	public void setMainMenuButtonVisibility(boolean visibility){
		mainMenuButton.setVisible(visibility);
	}
	
	public void setResignButtonClickability(boolean clickability){
		resignButton.setEnabled(clickability);
	}
	
	public void clearMarkersToDraw(){
		markersToDraw.clear();
	}
	
	public void drawAllSpaces(Graphics g){
		for(int i = 0; i < model.getMasterArray().length; i++){
			for(int j = 0; j < model.getMasterArray()[i].length; j++){
				model.getMasterArray()[i][j].draw(g);

			}
		}
	}	
	
	private class PentagoPanel extends JPanel{
		
		public PentagoPanel(){

		}
		
		@Override
		public void paintComponent(Graphics g){			
				g.setColor(Color.BLACK);
				g.fillRect(0, 0, 595, 470);
				g.setColor(new Color(139,69,19));
				g.fillRect(5, 5, 585, 460);			
				//black background of the board
				g.setColor(Color.BLACK);
				g.fillRoundRect(142, 66, 318, 318, 20, 20);
				//4 quadrants
				g.setColor(new Color(250,164,96));
				g.fillRoundRect(135, 60, 162, 162, 30, 30);
				g.fillRoundRect(135, 228, 162, 162, 30, 30);
				g.fillRoundRect(303, 60, 162, 162, 30, 30);
				g.fillRoundRect(303, 228, 162, 162, 30, 30);
	
				drawAllSpaces(g);

				for(int i = 0; i < model.getMasterArray().length; i++){
					for(int j = 0; j < model.getMasterArray()[i].length; j++){
						if(model.getMasterArray()[i][j].getIsOccupied())
							model.getMasterArray()[i][j].getMarker().draw(g);
						else{
							model.getMasterArray()[i][j].draw(g);
						}
					}
				}
			
		}
		
	}
}
