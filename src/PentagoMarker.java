import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;


public class PentagoMarker implements Serializable{

	private Color color;
	private int x; //top left
	private int y; //top left
	
	public PentagoMarker(int x, int y, Color color){
		this.color = color;
		this.x = x;
		this.y = y;
	}
	
	public Color getColor(){
		return color;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public void draw(Graphics g){
		g.setColor(Color.BLACK);
		g.fillArc(x, y, 35, 35, 0, 360);
		g.setColor(color);
		g.fillArc(x+2, y+2, 31, 31, 0, 360);
	}
}
