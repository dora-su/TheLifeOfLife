public class ChoiceTile extends ActionTile{
	
	private int index;
	
	ChoiceTile(int x, int y, String message,int index) {
		super(x, y, message);
		this.index = index;
		// TODO Auto-generated constructor stub
	}
	
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

}
