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

	Player(String name, double balance) {
		this.name = name;
		tile = 0;
		property = new ArrayList<Property>();
		this.player = this;
		destination = 0;
		add = 0;
		count = 0;
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
		//add money by different increments based on the amount that needs to be added or subtracted
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
					player.setMoney(player.money + decrease);
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
		new Timer(500, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (count > 0) {
					boolean specialPopup = false;
					count--;
					
					//going on the next part of the map
					if(player.tile == 18) {
						player.setTile(36 + 1);
					}else {
						player.setTile(player.getTile() + 1);
					}
					
				
					if (player.getTile() == 36) {
						Thread t = new Thread(new Runnable() {
							public void run() {
								//if land on a certain tile allow the player to choose career
								new CareerSelection(true, player);
							}
						});
						t.start();
						specialPopup = true;
						count = 0;
					} else if (player.getTile() == 122) {
						
						Thread t = new Thread(new Runnable() {
							public void run() {
								//if land on this specific tile create house selections choices for user
								new HouseSelection(player);
							}
						});
						t.start();
						specialPopup = true;
						count = 0;
					}else if(player.tile == 48) {
						//if land on this specific tile the user gets married
						new PopUp(player);
						Game.family.add(1);
						specialPopup = true;
						count = 0;
					} else if (path.get(player.getTile()) instanceof PayDayTile) {
						
					    //if player pass pay day tile add money to their bank balance
						player.addMoney(career.getSalary());
					} else if (path.get(player.getTile()) instanceof ChoiceTile) {					
						//if the player lands on a choice tile end their turn immediately
						count = 0;
					} 
					
					//if the tile the player lands on is a pay day tile, increase their salary
					if (count == 0 && (path.get(player.getTile()) instanceof PayDayTile)) {
						
						career.setSalary((int) (career.getSalary() * 1.05));
					}
					
					if(count == 0 && player.getTile() == 2) {
						Thread t = new Thread(new Runnable() {
							public void run() {
								new CareerSelection(true, player);
							}
						});
						t.start();
						specialPopup = true;
					}
					
					//create a pop up with instructions if no special pop ups have been made already
					if (count == 0 && !specialPopup) {
						new PopUp(path.get(player.getTile()).getMessage(), path.get(player.getTile()), player);
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
