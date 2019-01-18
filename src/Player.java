import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.Timer;

public class Player {

	private String name;
	private Career career;
	private ArrayList<Property> property;
	private int child;
	private int tile;
	private int money;
	private int destination;
	private Player player;
	private boolean college;
	private int add;
	private int count;
	private int x,y;
	private double xDiff, yDiff;
	private int step;

	Player(String name, double balance, int x, int y) {
		this.name = name;
		tile = 0;
		property = new ArrayList<Property>();
		this.player = this;
		destination = 0;
		add = 0;
		count = 0;
		this.x = x;
		this.y = y;
		step = 0;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Property> getProperty() {
		return property;
	}

	public void addProperty(Property property) {
		this.property.add(property);
	}

	public int getChild() {
		return child;
	}

	public void setChild(int child) {
		this.child = child;
	}

	public int getTile() {
		return tile;
	}

	public void setTile(int tile) {
		this.tile = tile;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public void addMoney(int money) {
		add += money;
		new Timer(1, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int decrease = 0;
				if (Math.abs(add) < 250) {
					decrease = 1;
				} else if (Math.abs(add) < 500){
					decrease = 5;
				} else if (Math.abs(add) < 1000) {
					decrease = 10;
				} else if (Math.abs(add) < 10000) {
					decrease = 100;
				} else if (Math.abs(add) < 100000) {
					decrease = 100000;
				}
				if (add > 0) {
					add -= decrease;
					player.setMoney(player.getMoney() + decrease);
				} else if (add < 0) {
					add += decrease;
					player.setMoney(player.getMoney() - decrease);
				}
			}
		}).start();
	}

	public void removeMoney(int money) {
		addMoney(-money);
	}

	public Career getCareer() {
		return career;
	}

	public void setCareer(Career career) {
		this.career = career;
	}

	public void setCollege(boolean college) {
		this.college = college;
	}

	public boolean getCollege() {
		return college;
	}

	public void move(int spin, ArrayList<ActionTile> path) {
		count += spin;
		step = 5;
		xDiff = -(double)(x - path.get(player.getTile() + 1).getX()) / 5;
		yDiff = -(double)(y - path.get(player.getTile() + 1).getY()) / 5;
		new Timer(100, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (count > 0) {
					if (step >= 0) {
						step--;
						x = (int)(xDiff + x);
						y = (int)(yDiff + y);
					} else {
						step = 5;
						boolean specialPopup = false;
						count--;
						player.setTile(player.getTile() + 1);
						if (player.getTile() == 1232) {
							Thread t = new Thread(new Runnable() {
								public void run() {
									new CareerPopUp(college, player);
								}
							});
							t.start();
							specialPopup = true;
							count = 0;
						} else if (player.getTile() == 2) {
							Thread t = new Thread(new Runnable() {
								public void run() {
									new HouseSelectionPopUp(player);
								}
							});
							t.start();
							specialPopup = true;
							count = 0;
						} else if (path.get(player.getTile()) instanceof PayDayTile) {
							player.addMoney(career.getSalary());
						} else if (path.get(player.getTile()) instanceof ChoiceTile) {
							count = 0;
						}
	
						if (count == 0 && (path.get(player.getTile()) instanceof PayDayTile)) {
							career.setSalary((int) (career.getSalary() * 1.05));
						}
						if (count == 0 && !specialPopup) {
							new PopUp(path.get(player.getTile()).getMessage(), path.get(player.getTile()), player);
						}
					}
				}
			}
		}).start();
	}

	public int getDestination() {
		return destination;
	}

	public void setDestination(int destination) {
		this.destination = destination;
	}
}
             