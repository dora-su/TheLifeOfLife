import java.util.ArrayList;

public class Player {

    private String name;
    private Career career;
    private ArrayList<Property> property;
    private int child;
    private int tile;
    private int money;
    private int destination;

    private boolean college;

    Player(String name, double balance, int x, int y) {
        this.name = name;
        tile = 0;
        destination = 0;
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

    public void setProperty(ArrayList<Property> property) {
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
        this.money = money;
    }


    public Career getCareer() {
        return career;
    }


    public void setCareer(Career career) {
        this.career = career;
    }

    public void move(int spin, ArrayList<ActionTile> path) {


        //choose career
        if (getTile() == getDestination() - 1) {
            if (this.getTile() == 36) {
                new CareerPopUp(college, this);

            }
            if (path.get(this.getTile()) instanceof PayDayTile) {
                money = (int) (money + career.getSalary());
            }

            if (!(path.get(this.getTile()) instanceof ChoiceTile)) {
                this.setTile(this.getTile() + 1);

            }
            new PopUp(path.get(this.getTile()).getMessage(), path.get(this.getTile()), this);
        } else {
            this.setTile(this.getTile() + 1);
        }
    }

    public int getDestination() {
        return destination;
    }

    public void setDestination(int destination) {
        this.destination = destination;
    }
}
