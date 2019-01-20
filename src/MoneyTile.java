/**
 * Name: MoneyTile.java
 * Version: 1.0
 * Authors: Chris, Dora, Eric, Jason
 * Date: January 2, 2019
 */
public class MoneyTile extends ActionTile {

    private int money;

    /**
     * Constructor
     *
     * @param x       the x coordinate
     * @param y       the y coordinate
     * @param message the message
     * @param money   the amount of money
     */
    MoneyTile(int x, int y, String message, int money) {
        super(x, y, message);
        this.money = money;
    }

    /**
     * @return money
     */
    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

}
