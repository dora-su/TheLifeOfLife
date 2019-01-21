/**
 * Name: Property.java
 * Version: 1.0
 * Authors: Chris, Dora, Eric, Jason
 * Date: January 3, 2019
 */

import javax.swing.ImageIcon;

public class Property {
    private String name;
    private int value;
    private ImageIcon image;

    /**
     * Constructor
     *
     * @param name  the name of the property
     * @param value the value of the property
     * @param image the image
     */
    Property(String name, int value, ImageIcon image) {
        this.setName(name);
        this.setValue(value);
        this.setImage(image);
    }

    /**
     * Gets the value of the property
     *
     * @return value
     */
    public int getValue() {
        return value;
    }

    /**
     * Sets the value of the property
     *
     * @param value the value of the property
     */
    public void setValue(int value) {
        this.value = value;
    }

    /**
     * Gets the name of the property
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the property
     *
     * @param name the name of the property
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the image of the property
     *
     * @return image
     */
    public ImageIcon getImage() {
        return image;
    }

    /**
     * Sets the image of the property
     *
     * @param image the image
     */
    public void setImage(ImageIcon image) {
        this.image = image;
    }
}
