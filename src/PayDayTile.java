
public class PayDayTile extends MoneyTile{
	private int payRaise;

	PayDayTile(int x, int y, String message, int money, int payRaise) {
		
		super(x, y, "message", money);
		this.setPayRaise(payRaise);
	}
	
	PayDayTile(int x, int y,  int money, int payRaise) {
		
		super(x, y, "Pay Day! Salaray Increase 5%!", money);
		this.setPayRaise(payRaise);
	}

	public int getPayRaise() {
		return payRaise;
	}

	public void setPayRaise(int payRaise) {
		this.payRaise = payRaise;
	}
	

}
