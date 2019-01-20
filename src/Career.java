/**
 * Name: Career.java
 * Version: 1.0
 * Authors: Chris, Dora, Eric, Jason
 * Date: January 3, 2019
 */

import javax.swing.ImageIcon;

public class Career {

    private String careerName;
    private int salary;
    private ImageIcon image;

    /**
     * Constructor
     *
     * @param careerName the career name
     * @param salary     the salary
     * @param image      the image
     */
    Career(String careerName, int salary, ImageIcon image) {
        this.careerName = careerName;
        this.salary = salary;
        this.image = image;
    }

    /**
     * Gets the name of the career
     *
     * @return careerName
     */
    public String getCareerName() {
        return careerName;
    }

    /**
     * Sets the name of the career
     *
     * @param careerName the career name
     */
    public void setCareerName(String careerName) {
        this.careerName = careerName;
    }

    /**
     * Gets the salary
     *
     * @return salary
     */
    public int getSalary() {
        return salary;
    }

    /**
     * Sets the salary
     *
     * @param salary the salary
     */
    public void setSalary(int salary) {
        this.salary = salary;
    }

    /**
     * Gets the image
     *
     * @return image
     */
    public ImageIcon getImage() {
        return image;
    }

    /**
     * Sets the image
     *
     * @param image an image
     */
    public void setImage(ImageIcon image) {
        this.image = image;
    }


}
