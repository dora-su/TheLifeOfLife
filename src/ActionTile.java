import java.awt.Rectangle;

public class ActionTile {
	private int x;
	private int y;
	private int height;
	private int width;
	private Rectangle boundingBox;
	private String message;
	
	ActionTile(int x, int y, int height, int width, String message){
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
		this.message = message; 
		boundingBox = new Rectangle(x,y,height,width);
	}
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public Rectangle getBoundingBox() {
		return boundingBox;
	}
	public void setBoundingBox(Rectangle boundingBox) {
		this.boundingBox = boundingBox;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
