/**
 * Name: ChoiceTile.java
 * Version: 1.0
 * Authors: Chris, Dora, Eric, Jason
 * Date: January 2, 2019
 */
public class ChoiceTile extends ActionTile {

    private int index;

    /**
     * Constructor
     *
     * @param x       the x coordinate
     * @param y       the y coordinate
     * @param message the tile message
     * @param index   the tile index
     */
    ChoiceTile(int x, int y, String message, int index) {
        super(x, y, message);
        this.index = index;
    }

    /**
     * Gets the tile index
     *
     * @return index
     */
    public int getIndex() {
        return index;
    }

    /**
     * Sets the tile index
     *
     * @param index the tile index
     */
    public void setIndex(int index) {
        this.index = index;
    }

}
