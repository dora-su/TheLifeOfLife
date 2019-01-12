

abstract public class ActionTile {
	private int x;
	private int y;
	private String message;
	
	ActionTile(int x, int y, String message){
		this.x = x;
		this.y = y;
		this.message = message; 
		
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

	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}




	
}
