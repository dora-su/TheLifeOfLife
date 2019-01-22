
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
import java.util.Random;

class Player implements Comparable<Player> {
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
	int newTile;
	int family, startup;
	private boolean ownStartUp;
	Client c;
	boolean isMoving, moved;

	/**
	 * Constructor
	 *
	 * @param name the username
	 */
	Player(String name) {
		this.name = name;
		tile = 88;
		this.player = this;
		add = 0;
		count = 0;
		money = 1000000;
		family = 0;
		startup = 0;
		isMoving = false;
		moved = false;
	}

	/**
	 * Compares this player to another player
	 *
	 * @param p the other player
	 * @return int, which player is comparatively greater
	 */
	public int compareTo(Player p) {
		return (p.getMoney() + (p.getProperty() != null ? 0 : p.getProperty().getValue()) + p.getChild() * 10000
				+ p.startup) - (money + (property == null ? 0 : property.getValue()) + child * 10000 + startup);
	}

	/**
	 * Sets the client
	 *
	 * @param c the client
	 */
	public void setClient(Client c) {
		this.c = c;
	}

	/**
	 * Gets the name
	 *
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name
	 *
	 * @param name the name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the property
	 *
	 * @return the property
	 */
	public Property getProperty() {
		return property;
	}

	/**
	 * Adds the property
	 *
	 * @param property the property
	 */
	public void addProperty(Property property) {
		this.property = property;
	}

	/**
	 * Gets the amount of children
	 *
	 * @return child
	 */
	public int getChild() {
		return child;
	}

	/**
	 * Sets the amount of children
	 *
	 * @param child the amount of children
	 */
	public void setChild(int child) {
		this.child = child;
	}

	/**
	 * Gets the current tile index
	 *
	 * @return tile
	 */
	public int getTile() {
		return tile;
	}

	/**
	 * Sets the current tile index
	 *
	 * @param tile the tile index
	 */
	public void setTile(int tile) {
		if (c != null) {
			c.output.println(name);
			c.output.println("/tile " + tile);
			c.output.flush();
		}
		this.tile = tile;
	}

	/**
	 * Gets the amount of money
	 *
	 * @return money
	 */
	public int getMoney() {
		return money;
	}

	/**
	 * Sets the amount of money
	 *
	 * @param money the amount of money
	 */
	public void setMoney(int money) {
		this.money = money;
	}

	/**
	 * Adds money to the player
	 *
	 * @param money the amount of money to be added
	 */
	public void addMoney(int money) {
		add += money;
		if (c != null) {
			c.output.println(c.userName);
			c.output.println("/status " + (player.money + add) + " 0");
			c.output.flush();
		}
		add /= 2;
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

	/**
	 * Removes an amount of money
	 *
	 * @param money the amount of money to be removed
	 */
	public void removeMoney(int money) {
		addMoney(-money);
	}

	/**
	 * Gets the career
	 *
	 * @return career
	 */
	public Career getCareer() {
		return career;
	}

	/**
	 * Sets the career
	 *
	 * @param career the career
	 */
	public void setCareer(Career career) {
		this.career = career;
	}

	/**
	 * Sets whether the player is going to college
	 *
	 * @param college if the player is going to college
	 */
	public void setCollege(boolean college) {
		this.college = college;
	}

	/**
	 * Gets whether the player is going to college
	 *
	 * @return college
	 */
	public boolean getCollege() {
		return college;
	}

	/**
	 * Moves the player based on a spin
	 *
	 * @param g     the game
	 * @param spin  the spin number
	 * @param path  the map path
	 * @param popUp if there is a popup
	 */
	public void move(Game g, int spin, ArrayList<ActionTile> path, boolean popUp) {
		count += spin;
		isMoving = true;
		new Timer(500, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (count > 0) {
					boolean specialPopup = false;
					count--;

					//going on the next part of the map
					if (player.tile == 120) {
						g.dispose();
						new FinalScreen(player);
						System.out.println("FINAL SCREEN");
						return;
					} else if (player.tile == 17) {
						player.setTile(35 + 1);
					} else if (player.tile == 66) {
						player.setTile(75 + 1);
					} else if (player.tile == 98) {
						player.tile = 106;
					} else {
						player.setTile(player.getTile() + 1);
					}

					if (player.getTile() == 35) {
						Thread t = new Thread(new Runnable() {
							public void run() {
								if (popUp)
									new CareerSelection(false, player); //if land on a certain tile allow the player to choose career
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
							}
						});
						t2.start();

						specialPopup = true;
						count = 0;
					} else if (player.tile == 42)

					{
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
									new CareerSelection(false, player);
							}
						});
						t.start();
						specialPopup = true;
						count = 0;
					} else if (player.tile == 111) { //last stop sign
						if (player.getCareer().getCareerName().equals("Start Up")) {
							Random rand = new Random();
							int win = rand.nextInt(3);
							if (win == 1) {
								new PopUp("Successful", path.get(player.getTile()), player);
								player.addMoney(1500000);
								player.startup = 1500000;
							} else {
								new PopUp("Your Start Up Failed", path.get(player.getTile()), player);
								player.addMoney(-800000);
								player.startup = -800000;
							}

						} else {
							new PopUp("Nothing to see here, move along", path.get(player.getTile()), player);
						}

						count = 0;
						specialPopup = true;
					} else if (path.get(player.getTile()) instanceof PayDayTile) {
						if (popUp) {
							player.addMoney(career.getSalary()); //if player pass pay day tile add money to their bank balance
						}
					} else if (path.get(player.getTile()) instanceof ChoiceTile) {
						count = 0; //if the player lands on a choice tile end their turn immediately
					}

					//if the tile the player lands on is a pay day tile, increase their salary
					if (count == 0 && (path.get(player.tile) instanceof PayDayTile)) {
						career.setSalary((int) (career.getSalary() * 1.05));
					}

					if (count == 0 && (path.get(player.tile) instanceof MoneyTile)) {
						int money = ((MoneyTile) (path.get(player.tile))).getMoney();
						if (popUp) {
							player.addMoney(money);
						}
					}

					if (count == 0 && player.tile == 2) {
						Thread t = new Thread(new Runnable() {
							public void run() {
								if (popUp) {
									new CareerSelection(true, player);
								}
							}
						});
						t.start();
						specialPopup = true;
					}

					if (player.getTile() == 44 || player.getTile() == 76 || player.getTile() == 96
							|| player.getTile() == 61) {
						if (count == 0) {
							player.setChild(player.getChild() + 1);
							player.family = 1 + player.family;
						}
					}

					//create a pop up with instructions if no special pop ups have been made already
					if (count == 0 && !specialPopup) {
						if (popUp) {
							new PopUp(path.get(player.getTile()).getMessage(), path.get(player.getTile()), player);
						}
					}
					if (count == 0) {
						isMoving = false;
					}
				}
			}
		}).start();
	}

	public boolean isOwnStartUp() {
		return ownStartUp;
	}

	public void setOwnStartUp(boolean ownStartUp) {
		this.ownStartUp = ownStartUp;
	}

}