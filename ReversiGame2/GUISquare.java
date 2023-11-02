package reversi;

import javax.swing.JButton;
import javax.swing.BorderFactory;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;

public class GUISquare extends JButton{

	private IModel GUIMod; 
	private int integer1; 
	private int integer2;
	
	public GUISquare (IModel GUIMod,int integer1,int integer2)
	{
		setGUIModel(GUIMod);
		setInteger1(integer1);
		setInteger2(integer2);
		setPreferredSize(new Dimension(50,50));
		setMinimumSize(new Dimension(50,50));
	}
	
	public Color circleColor() { 
		int playerColor = getGUIModel().getBoardContents(getInteger1(), getInteger2());
		if(playerColor == 2) {
			return Color.black;
		}
		else if(playerColor == 1) {
			return Color.white;
		}
		else {
			return null; 
		}
	}
	
	public Color borderColor() {
		int playerColor = getGUIModel().getBoardContents(getInteger1(), getInteger2());
		if(playerColor == 2) {
			return Color.white;
		}
		else if(playerColor == 1) {
			return Color.black;
		}
		else {
			return null;
		}
	}
	
	protected void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    setBackground(Color.green);
	    setOpaque(true);
	    setBorder(BorderFactory.createLineBorder(Color.black));

	    Color cColor = circleColor();
	    Color bColor = borderColor();
	    if (cColor != null || bColor != null) {
	    g.setColor(cColor);
	    g.fillOval(2, 2, 46, 46);
	    g.setColor(bColor);
	    g.drawOval(2, 2, 46, 46);
	    }
	}
	
	
	public void setInteger1 (int integer1) {
		this.integer1 = integer1;
	}
	
	public void setInteger2 (int integer2) {
		this.integer2 = integer2;
	}
	
	public void setGUIModel(IModel GUIMod) {
		this.GUIMod = GUIMod;
	}
	
	public int getInteger1()
	{
		return integer1;
	}
	
	public int getInteger2()
	{
		return integer2;
	}
	
	public IModel getGUIModel() {
		return GUIMod;
	}
	
	
}
