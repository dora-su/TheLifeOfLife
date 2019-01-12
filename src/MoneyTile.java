public class MoneyTile extends ActionTile {
	
	private int money;

	MoneyTile(int x, int y, String message, int money) {
		super(x, y, message);
		this.money = money;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

}
