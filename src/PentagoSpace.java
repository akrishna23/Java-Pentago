import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;


public class PentagoSpace implements Serializable{

	private boolean isOccupied;
	private PentagoMarker marker;
	private int x;
	private int y;
	private int number;
	private double radius;
	
	public PentagoSpace(int x, int y){
		this.x = x;
		this.y = y;
		this.isOccupied = false;
		this.radius = 17.5;
		this.marker = null;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public void setNumber(int number){
		this.number = number;
	}
	
	public int getNumber(){
		return number;
	}
	
	public void setIsOccupied(boolean isOccupied){
		this.isOccupied = isOccupied;
	}
	
	public boolean getIsOccupied(){
		return isOccupied;
	}
	
	public PentagoMarker getMarker(){
		if(marker == null)
			return null;
		return marker;
	}
	
	public void draw(Graphics g){
		if(isOccupied)
			marker.draw(g);
		else{
			g.setColor(new Color(228, 147, 86));
			g.fillArc(x, y, 35, 35, 0, 360);
			g.setColor(new Color(215, 147, 82));
			g.fillArc(x+10, y+10, 15, 15, 0, 360);
		}
	}
	
	public void addPentagoMarker(Color color){
		marker = new PentagoMarker(x, y, color);
		isOccupied = true;
	}
	
	public void setMarker(PentagoMarker marker){
		if(marker == null){
			this.marker = null;
			isOccupied = false;
		}
		this.marker = marker;
	}
	
	public boolean containsPoint(int clickX, int clickY){
		return (Math.pow((x + radius - clickX),2) + Math.pow((y + radius - clickY),2) < Math.pow(radius,2));
	}
}
