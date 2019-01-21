
/**
 * Name: Player.java
 * Version: 2.0
 * Authors: Chris, Dora, Eric, Jason
 * Date: January 6, 2019
 */

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

class Player implements Comparable<Player>{
	private String name;
	private Career career;
	private Property property;
	private int child;
	private int tile;
	private int money;
	private Player player;
	private boolean college;
	private int add;
	private int count;
	int family, startup;
	Client c;

	Player(String name) {
		this.name = name;
		tile = 0;
		this.player = this;
		add = 0;
		count = 0;
		money = 1000000;
		family = 0;
		startup = 0;
	}
	
	public int compareTo(Player p) {
		return (p.getMoney() + (p.getProperty() != null ? 0 : p.getProperty().getValue()) + p.getChild() * 10000 + p.startup) - (money + (property == null ? 0 : property.getValue()) + child * 10000 + startup);  
	}
	public void setClient(Client c) {
		this.c = c;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Property getProperty() {
		return property;
	}

	public void addProperty(Property property) {
		this.property = property;
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
		if (c != null) {
			c.output.println(name);
			c.output.println("/status " + money + " 0");
			c.output.flush();
		}
		this.money = money;
	}

	public void addMoney(int money) {
		add += money;
		c.output.println(c.userName);
		c.output.println("/status " + (player.money+ add) + " 0");
		c.output.flush();
		//add money by different increments based on the amount that needs to be added or subtracted
		new Timer(1, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int decrease = 0;
				if (Math.abs(add) < 250) {
					decrease = 1;
				} else if (Math.abs(add) < 500) {
					decrease = 5;
				} else if (Math.abs(add) < 1000) {
					decrease = 10;
				} else if (Math.abs(add) < 10000) {
					decrease = 100;
				} else {
					decrease = 10000;
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

	public void move(Game g, int spin, ArrayList<ActionTile> path, boolean popUp) {
		count += spin;
		new Timer(500, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (count > 0) {
					boolean specialPopup = false;
					count--;

					//going on the next part of the map
					if (player.tile == 124) {
						new FinalScreen(player);
					} else if (player.tile == 17) {
						player.setTile(35 + 1);
					} else if (player.tile == 66) {
						player.setTile(75 + 1);
					} else if (player.tile == 93) {
						player.tile = 106;
					} else {
						player.setTile(player.getTile() + 1);
					}

					if (player.getTile() == 35) {
						Thread t = new Thread(new Runnable() {
							public void run() {
								if (popUp)
									new CareerSelection(false, player); //if land on a certain tile allow the player to choose career
								g.turn++;
							}
						});
						t.start();
						specialPopup = true;
						count = 0;
					} else if (player.getTile() == 50) {
						Thread t2 = new Thread(new Runnable() {
							public void run() {
								if (popUp)
									new HouseSelection(player); //if land on this specific tile create house selections choices for user
								g.turn++;
							}
						});
						t2.start();

						specialPopup = true;
						count = 0;
					} else if (player.tile == 42) {
						if (popUp) {
							new PopUp(player); //if land on this specific tile the user gets married
							family++;
						}
						specialPopup = true;
						count = 0;
					} else if (player.tile == 75) {
						Thread t = new Thread(new Runnable() {
							public void run() {
								if (popUp)
									new CareerSelection(true, player);
							}
						});
						t.start();
						specialPopup = true;
						count = 0;
					} else if (path.get(player.getTile()) instanceof PayDayTile) {
						System.out.println(career.getSalary());
						player.addMoney(career.getSalary()); //if player pass pay day tile add money to their bank balance
					} else if (path.get(player.getTile()) instanceof ChoiceTile) {
						count = 0; //if the player lands on a choice tile end their turn immediately
					}

					//if the tile the player lands on is a pay day tile, increase their salary
					if (count == 0 && (path.get(player.tile) instanceof PayDayTile)) {
						career.setSalary((int) (career.getSalary() * 1.05));
					}

					if (count == 0 && (path.get(player.tile) instanceof MoneyTile)) {
						int money = ((MoneyTile) (path.get(player.tile))).getMoney();
						if (money > 0) {
							player.addMoney(money);
						} else if (money < 0) {
							System.out.println(money);
							player.addMoney(money);
						}
					}

					System.out.println("player Tile " + player.tile);
					if (count == 0 && player.tile == 2) {
						Thread t = new Thread(new Runnable() {
							public void run() {
								if (popUp)
									new CareerSelection(true, player);
								g.turn++;
							}
						});
						t.start();
						specialPopup = true;
					}

					if (player.getTile() == 44 || player.getTile() == 70 || player.getTile() == 76
							|| player.getTile() == 96 || player.getTile() == 104 || player.getTile() == 113) {
						if (count == 0) {
							player.setChild(player.getChild() + 1);
						}
					}

					//System.out.println("Speical Pop pup" + specialPopup);
					//create a pop up with instructions if no special pop ups have been made already
					if (count == 0 && !specialPopup && popUp) {
						new PopUp(path.get(player.getTile()).getMessage(), path.get(player.getTile()), player);
					}

				}
			}
		}).start();
	}

}