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

public class Player {

    private String name;
    private Career career;
    private ArrayList<Property> property;
    private int child;
    private int tile;
    private int money;
    private Player player;
    private boolean college;
    private int add;
    private int count;
    int family;
    Client c;

    /**
     * Constructor
     *
     * @param name the username of the player
     */
    Player(String name) {
        this.name = name;
        tile = 0;
        property = new ArrayList<Property>();
        this.player = this;
        add = 0;
        count = 0;
        money = 1000000;
        family = 0;
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
     * Gets the player's username
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the player
     *
     * @param name the name of the player
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the property of the player
     *
     * @return property
     */
    public ArrayList<Property> getProperty() {
        return property;
    }

    /**
     * Adds the property to the player
     *
     * @param property the property
     */
    public void addProperty(Property property) {
        this.property.add(property);
    }

    /**
     * Gets the number of children
     *
     * @return child
     */
    public int getChild() {
        return child;
    }

    /**
     * Sets the number of children
     *
     * @param child the number of children
     */
    public void setChild(int child) {
        this.child = child;
    }

    /**
     * Gets the current tile the player is on
     *
     * @return tile
     */
    public int getTile() {
        return tile;
    }

    /**
     * Sets the current tile the player is on
     *
     * @param tile the tile
     */
    public void setTile(int tile) {
        this.tile = tile;
    }

    /**
     * Gets the amount of money the player has
     *
     * @return money
     */
    public int getMoney() {
        return money;
    }

    /**
     * Sets the amount of money the player has
     *
     * @param money the amount of money
     */
    public void setMoney(int money) {
        this.money = money;
    }

    /**
     * Adds the amount of money to the player
     *
     * @param money the amount of money to add
     */
    public void addMoney(int money) {
        add += money;
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
                c.output.println(c.userName);
                c.output.println("/status " + (player.money));
                c.output.flush();
            }
        }).start();
    }

    /**
     * Removes an amount of money
     *
     * @param money the amount of money to remove
     */
    public void removeMoney(int money) {
        addMoney(-money);
    }

    /**
     * Gets the player's career
     *
     * @return career
     */
    public Career getCareer() {
        return career;
    }

    /**
     * Sets the player's career
     *
     * @param career the player's career
     */
    public void setCareer(Career career) {
        this.career = career;
    }

    /**
     * Sets whether the player is going to college or not
     *
     * @param college whether the player is going to college or not
     */
    public void setCollege(boolean college) {
        this.college = college;
    }

    /**
     * Gets whether the player is going to college or not
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
     * @param spin  the number gotten from the spin
     * @param path  the map path
     * @param popUp whether there is a popup or not
     */
    public void move(Game g, int spin, ArrayList<ActionTile> path, boolean popUp) {
        count += spin;
        new Timer(500, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (count > 0) {
                    boolean specialPopup = false;
                    count--;

                    //going on the next part of the map
                    if (player.tile == 17) {
                        player.setTile(35 + 1);
                    } else if (player.tile == 66) {
                        player.setTile(75 + 1);
                    } else if (player.tile == 89) {
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
                        Thread t = new Thread(new Runnable() {
                            public void run() {
                                if (popUp)
                                    new HouseSelection(player); //if land on this specific tile create house selections choices for user
                                g.turn++;
                            }
                        });
                        t.start();

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
                                if (popUp) new CareerSelection(true, player);
                                g.turn++;
                            }
                        });
                        t.start();
                        specialPopup = true;
                        count = 0;
                    } else if (path.get(player.getTile()) instanceof PayDayTile) {
                        player.addMoney(career.getSalary()); //if player pass pay day tile add money to their bank balance
                    } else if (path.get(player.getTile()) instanceof ChoiceTile) {
                        count = 0; //if the player lands on a choice tile end their turn immediately
                    }

                    //if the tile the player lands on is a pay day tile, increase their salary
                    if (count == 0 && (path.get(player.getTile()) instanceof PayDayTile)) {
                        career.setSalary((int) (career.getSalary() * 1.05));
                    }

                    if (count == 0 && player.getTile() == 2) {
                        Thread t = new Thread(new Runnable() {
                            public void run() {
                                if (popUp) new CareerSelection(true, player);
                                g.turn++;
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

}
