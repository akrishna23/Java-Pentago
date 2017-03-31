package netgame.jeopardy;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class JeopardyClientView extends JFrame {
	
	private JeopardyClientModel model;
	private GameBoardPanel gameBoardPanel;
	private int currentlySelectedCol = -1;
	private int currentlySelectedRow = -1;
	private JTextArea statsTextArea;
	private JTextArea questionTextArea;
	private JTextField answerTextField;
	private JButton answerSubmitButton;
	private JLabel messageLabel;

	public JeopardyClientView() {
		super("Jeopardy!");
		setLocation(200, 100);
		setSize(1000, 600);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel mainPanel = new JPanel();
		this.setContentPane(mainPanel);
		mainPanel.setBackground(Color.BLACK);
		mainPanel.setLayout(new BorderLayout());

		JPanel statsPanel = new JPanel();
		statsPanel.setBackground(Color.BLUE);
		statsPanel.setBorder(new EmptyBorder(10, 15, 10, 15));
		statsTextArea = new JTextArea("Player\tScore\t#Correct\n", 30, 25);
		statsTextArea.setEditable(false);
		statsTextArea.setCursor(null);
		statsTextArea.setOpaque(false);
		statsTextArea.setFocusable(false);
		statsTextArea.setWrapStyleWord(true);
		statsTextArea.setLineWrap(true);
		statsTextArea.setForeground(Color.WHITE);
		statsPanel.add(statsTextArea);
		mainPanel.add(statsPanel, BorderLayout.EAST);
		gameBoardPanel = new GameBoardPanel();
		mainPanel.add(gameBoardPanel, BorderLayout.CENTER);

		JPanel questionAnswerPanel = new JPanel();
		questionAnswerPanel.setBackground(Color.WHITE);
		questionAnswerPanel.setLayout(new BorderLayout());
		JPanel questionPanel = new JPanel();
		questionPanel.setLayout(new GridLayout(2,1));
		questionPanel.setBackground(Color.BLUE);
		questionTextArea = 
				new JTextArea("The questions will display here", 2, 35);
		questionTextArea.setEditable(false);
		questionTextArea.setCursor(null);
		questionTextArea.setOpaque(false);
		questionTextArea.setFocusable(false);
		questionTextArea.setWrapStyleWord(true);
		questionTextArea.setLineWrap(true);
		questionTextArea.setFont(new Font("sansserif", Font.PLAIN, 18));
		questionTextArea.setForeground(Color.WHITE);
		questionTextArea.setBorder(BorderFactory.createEmptyBorder(20, 20, 0, 0));
		questionPanel.add(questionTextArea);
		messageLabel = new JLabel("Click on a number above.");
		messageLabel.setFont(new Font("sansserif", Font.PLAIN, 24));
		messageLabel.setForeground(Color.RED);
		messageLabel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
		questionPanel.add(messageLabel);
		JPanel answerPanel = new JPanel();
		answerPanel.setBackground(Color.WHITE);
		answerTextField = new JTextField("Type your answers here.", 30);
		answerSubmitButton = new JButton("Submit Answer");
		answerPanel.add(answerTextField);
		answerPanel.add(answerSubmitButton);
		questionAnswerPanel.add(questionPanel, BorderLayout.CENTER);
		questionAnswerPanel.add(answerPanel, BorderLayout.SOUTH);
		mainPanel.add(questionAnswerPanel, BorderLayout.SOUTH);
		setVisible(true);
	}
	
	//returns the column of the category corresponding to the point (x,y)
	//or returns -1 if no category corresponds to the point (x,y)
	public int getColumn(int x, int y) {
		int rowHeight = gameBoardPanel.getRowHeight();
		int colWidth = gameBoardPanel.getColWidth();
		int leftMargin = gameBoardPanel.getLeftMargin();
		int heightOfCategoryLabels 
			= gameBoardPanel.getHeight() - rowHeight * model.getNumRows();
		if (y > heightOfCategoryLabels) {
			return (x - leftMargin) / colWidth;
		}
		else return -1;
		
		
	}
	
	public int getRow(int x, int y) {
		int rowHeight = gameBoardPanel.getRowHeight();
		int heightOfCategoryLabels 
			= gameBoardPanel.getHeight() - rowHeight * model.getNumRows();
		if (y > heightOfCategoryLabels) {
			return (y - heightOfCategoryLabels) / rowHeight;
		}
		else return -1;
	}
	
	public void setModel(JeopardyClientModel model) {
		this.model = model;
	}
	
	public void registerActionListener(ActionListener listener) {
		answerSubmitButton.addActionListener(listener);
		answerTextField.addActionListener(listener);
	}
	
	public void registerMouseListener(MouseListener listener) {
		gameBoardPanel.addMouseListener(listener);
	}
	
	public boolean isSubmissionEnabled() {
		return answerSubmitButton.isEnabled();
	}
	
	public void setSubmissionEnabled(boolean enabled) {
		answerSubmitButton.setEnabled(enabled);
	}
	
	public void setMessageText(String messageText) {
		messageLabel.setText(messageText);
	}
	
	public void setQuestionText(String questionText) {
		questionTextArea.setText(questionText);
	}
	
	public String getQuestionText() {
		return questionTextArea.getText();
	}
	
	public void setAnswerText(String answerText) {
		answerTextField.setText(answerText);
	}
	
	public String getAnswerText() {
		return answerTextField.getText();
	}
	
	public void selectAnswerText() {
		answerTextField.selectAll();
		answerTextField.requestFocus();
	}
	
	public int getCurrentlySelectedCol() {
		return currentlySelectedCol;
	}
	
	public void setCurrentlySelectedCol(int col) {
		currentlySelectedCol = col;
	}
	
	public int getCurrentlySelectedRow() {
		return currentlySelectedRow;
	}
	
	public void setCurrentlySelectedRow(int row) {
		currentlySelectedRow = row;
	}
	
	public void updateStats(String stats) {
		statsTextArea.setText(stats);
	}

	private class GameBoardPanel extends JPanel {
		
		int cellPadding = 5;
		
		public GameBoardPanel() {
			this.setBackground(Color.BLACK);
		}
		
		public int getLeftMargin() {
			return cellPadding + (getWidth() % model.getNumCategories()) / 2;
		}
		
		public int getColWidth() {
			int width = gameBoardPanel.getWidth() - 2 * cellPadding;
			return width / (model.getNumCategories());
		}
		
		public int getRowHeight() {
			int height = gameBoardPanel.getHeight() - 2 * cellPadding;
			return height / (model.getNumRows() + 1);
		}
		
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			if (model == null)
				return;
			int height = this.getHeight() - 2 * cellPadding;
			int rowHeight = getRowHeight();
			int categoryRowHeight = rowHeight + height % (model.getNumRows() + 1);
			int colWidth = getColWidth();
			int topMargin = cellPadding;
			int arcRadius = 10;
			int cellWidth = colWidth - 2 * cellPadding;
			int categoryHeight = categoryRowHeight - 2 * cellPadding;
			int cellHeight = rowHeight - 2 * cellPadding;
			for (int col = 0; col < model.getNumCategories(); col++) {
				g.setColor(Color.BLUE);
				int leftEdge = getLeftMargin() + cellPadding + col * colWidth;
				int topEdge = topMargin + cellPadding;
				g.fillRoundRect(leftEdge, topEdge, 
						cellWidth, categoryHeight,
						arcRadius, arcRadius);
				Font font = new Font("sansserif", Font.BOLD, 32);
				g.setFont(font);
				g.setColor(Color.WHITE);
				FontMetrics fm = g.getFontMetrics();
				int manualCorrection = (int)(fm.getAscent() * 0.4);
				int strHeight = fm.getAscent() - manualCorrection;
				int strWidth = fm.stringWidth(model.getCategory(col));
				int strLeftMargin = (cellWidth - strWidth) / 2;
				//adding for top margin since fonts are drawn starting at the lower left corner
				int strTopMargin = (rowHeight + strHeight) / 2; 
				g.drawString(model.getCategory(col), 
						leftEdge + strLeftMargin,
						topEdge + strTopMargin);

				
			}
			for (int col = 0; col < model.getNumCategories(); col++) {
				for (int row = 0; row < model.getNumRows(); row++) {
					int leftEdge = getLeftMargin() + cellPadding + col * colWidth;
					int topEdge = topMargin + categoryRowHeight + cellPadding + row * rowHeight;
					if (col == currentlySelectedCol && row == currentlySelectedRow) {
						g.setColor(Color.YELLOW);
						g.fillRect(getLeftMargin() + col * colWidth,
								topMargin + categoryRowHeight + row * rowHeight,
								getColWidth(), getRowHeight());
					}
					g.setColor(Color.BLUE);
					g.fillRoundRect(leftEdge, topEdge,
						cellWidth, cellHeight,
						arcRadius, arcRadius);
					if (model.isCorrect(col, row))
						continue;
					Font font = new Font("sansserif", Font.BOLD, 40);
					g.setFont(font);
					g.setColor(Color.YELLOW);
					FontMetrics fm = g.getFontMetrics();
					int manualCorrection = (int)(fm.getAscent() * 0.4);
					int strHeight = fm.getAscent() - manualCorrection;
					int strWidth = fm.stringWidth("" + model.getPointValue(col, row));
					int strLeftMargin = (cellWidth - strWidth) / 2;
					//adding for top margin since fonts are drawn starting at the lower left corner
					int strTopMargin = (rowHeight + strHeight) / 2; 
					g.drawString("" + model.getPointValue(col, row), 
							leftEdge + strLeftMargin,
							topEdge + strTopMargin);
				}
			}
		}
	}
}
