import java.util.ArrayList;

public class Player {

    private String name;
    private Career career;
    private ArrayList<Property> property;
    private int child;
    private int tile;
    private int money;
    private int destination;
    Player player;

    private boolean college;

	Player(String name, double balance, int x, int y) {
		this.name = name;
		tile = 0;
		property = new ArrayList<Property>();
		this.player = this;
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

		boolean specialPopup = false;
		for (int i = 0; i < spin; i++) {
			specialPopup = false;
			//choose career
			if (this.getTile() == 35) {
				Thread t = new Thread(new Runnable() {
					public void run() {

						new CareerPopUp(college, player);

					}
				});
				t.start();

				specialPopup = true;
				break;
			}

			if (this.getTile() == 2) {
				System.out.println("HOUSe");
				new HouseSelectionPopUp(this);
				specialPopup = true;
				break;
			}

			if (path.get(this.getTile()) instanceof PayDayTile) {
				money = (money + career.getSalary());
			}

			if (!(path.get(this.getTile()) instanceof ChoiceTile)) {
				this.setTile(this.getTile() + 1);

			}

			//			try {
			//				Thread.sleep(500);
			//			} catch (InterruptedException e) {
			//			}

			if (path.get(this.getTile()) instanceof ChoiceTile) {
				break;
			}

		}

		if ((path.get(this.getTile()) instanceof PayDayTile)) {
			career.setSalary((int) (career.getSalary() * 1.05));
		}

		if (!specialPopup) {
			new PopUp(path.get(this.getTile()).getMessage(), path.get(this.getTile()), this);
		}
	}

    public int getDestination() {
        return destination;
    }

    public void setDestination(int destination) {
        this.destination = destination;
    }
}
