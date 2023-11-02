package reversi;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridLayout;
import java.awt.BorderLayout;

public class GUIView implements IView{

	IModel viewModel;
	IController viewController; 
	JLabel bMessage = new JLabel(); 
	JLabel wMessage = new JLabel(); 
	JPanel bBoard;
	JPanel wBoard;
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		}

	@Override
	public void initialise(IModel viewModel, IController viewController){
		this.viewController = viewController;
		this.viewModel = viewModel;
		int heightBoard = viewModel.getBoardHeight();
		int widthBoard = viewModel.getBoardWidth();
		/*Setting the frames*/
		JFrame bFrame = new JFrame("Reversi - black player");
		bBoard = new JPanel();
		bBoard.setLayout(new GridLayout(widthBoard,heightBoard));
		bFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		bFrame.add(bMessage, BorderLayout.NORTH);
	
		JFrame wFrame = new JFrame("Reversi - white player");
		wBoard = new JPanel();
		wBoard.setLayout(new GridLayout(widthBoard,heightBoard));
		wFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		wFrame.add(wMessage, BorderLayout.NORTH);	
		
		/*adding the squares - white has to be first instead of black on the board since white has to go first*/
		for (int j = 0; j < heightBoard; j++) {
			for (int i = 0; i < widthBoard; i++) {
		       final int x = i;
		       final int y = j;
		       GUISquare wSquare = new GUISquare(viewModel, i, j);
		       wSquare.addActionListener(e -> viewController.squareSelected(1, x, y));
		       wBoard.add(wSquare);
		       
               int bWidth = widthBoard-1; int bHeight = heightBoard-1;
               GUISquare bSquare = new GUISquare(viewModel, bWidth-i, bHeight-j);
               bSquare.addActionListener(e -> viewController.squareSelected(2, bWidth-x, bHeight-y));
               bBoard.add(bSquare); 
            }
		}
		/*AI and Layout Setup*/
		JPanel wButtons = new JPanel(new GridLayout(2, 1));
		JButton wAI = new JButton("Greedy AI (play white)");
		wAI.addActionListener(e -> viewController.doAutomatedMove(1));
		JButton wRestart = new JButton("Restart");
		wRestart.addActionListener(e -> viewController.startup());
		wButtons.add(wAI);
		wButtons.add(wRestart);
		wFrame.add(wButtons, BorderLayout.SOUTH);
		wFrame.add(wBoard, BorderLayout.CENTER);
		wFrame.pack();
		wFrame.setVisible(true);
		
		JPanel bButtons = new JPanel(new GridLayout(2, 1));
		JButton bAI = new JButton("Greedy AI (play black)");
		bAI.addActionListener(e -> viewController.doAutomatedMove(2));
		JButton bRestart = new JButton("Restart");
		bRestart.addActionListener(e -> viewController.startup());
		bButtons.add(bAI);
		bButtons.add(bRestart);
		bFrame.add(bButtons, BorderLayout.SOUTH);
		bFrame.add(bBoard, BorderLayout.CENTER);
		bFrame.pack();
		bFrame.setVisible(true);		
}
	
	@Override 
	public void refreshView() {		
	    bBoard.repaint();
	    wBoard.repaint();
	}
	
	@Override 
	public void feedbackToUser(int player, String message) {		
		if(player!=1) {
			bMessage.setText(message);
		}
		else {
			wMessage.setText(message);
		}
	}
}

