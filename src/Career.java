import javax.swing.ImageIcon;

public class Career {
	
	private String careerName;
	private int salary;
	private ImageIcon image;
	
	Career(String careerName, int salary, ImageIcon image){
		this.careerName = careerName;
		this.salary = salary;
		this.image = image;
	}
	
	public String getCareerName() {
		return careerName;
	}
	
	public void setCareerName(String careerName) {
		this.careerName = careerName;
	}
	
	public int getSalary() {
		return salary;
	}
	
	public void setSalary(int salary) {
		this.salary = salary;
	}

	public ImageIcon getImage() {
		return image;
	}

	public void setImage(ImageIcon image) {
		this.image = image;
	}
	
	
}
