public class Career {
	
	private String careerName;
	private double salary;
	
	Career(String careerName, double salary){
		this.careerName = careerName;
		this.salary = salary;
	}
	
	public String getCareerName() {
		return careerName;
	}
	
	public void setCareerName(String careerName) {
		this.careerName = careerName;
	}
	
	public double getSalary() {
		return salary;
	}
	
	public void setSalary(double salary) {
		this.salary = salary;
	}
	
}
