import javax.swing.ImageIcon;

public class Property {
	private String name;
	private int value;
	private ImageIcon image;
	
	Property(String name, int value, ImageIcon image){
		this.setName(name);
		this.setValue(value);
		this.setImage(image);
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ImageIcon getImage() {
		return image;
	}

	public void setImage(ImageIcon image) {
		this.image = image;
	}
}
