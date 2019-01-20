/**
 * Name: ActionTile.java
 * Version: 1.0
 * Authors: Chris, Dora, Eric, Jason
 * Date: January 2nd, 2019
 */
abstract public class ActionTile {
    private int x;
    private int y;
    private String message;

    ActionTile(int x, int y, String message) {
        this.x = x;
        this.y = y;
        this.message = message;

    }

    /**
     * Gets x value
     *
     * @return x
     */
    public int getX() {
        return x;
    }

    /**
     * Sets x value
     *
     * @param x
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Gets y value
     *
     * @return y
     */
    public int getY() {
        return y;
    }

    /**
     * Sets y value
     *
     * @param y
     */
    public void setY(int y) {
        this.y = y;
    }

    /*
     * Gets message
     * @return mesage
     */
    private String getMessage() {
        return message;
    }

    /**
     * Sets message
     *
     * @param message
     */
    public void setMessage(String message) {
        this.message = message;
    }

}
