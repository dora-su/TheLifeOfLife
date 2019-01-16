public class Career {
	
	private String careerName;
	private int salary;
	
	Career(String careerName, int salary){
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
	
	public void setSalary(int salary) {
		this.salary = salary;
	}
	
}
