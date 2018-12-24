import java.util.ArrayList;

public class Player {
	
	private String name;
	private String career; 
	private ArrayList<Property> property;
	private int child;
	private int x; 
	private int y;
	private BankAccount bankAccount;
	
	Player(String name, double balance){
		this.name = name;
		bankAccount = new BankAccount(balance);
	}
	
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getCareer() {
		return career;
	}
	
	public void setCareer(String career) {
		this.career = career;
	}
	
	public ArrayList<Property> getProperty() {
		return property;
	}
	
	public void setProperty(ArrayList<Property> property) {
		this.property = property;
	}
	
	public int getChild() {
		return child;
	}
	
	public void setChild(int child) {
		this.child = child;
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
	
	
	
}
