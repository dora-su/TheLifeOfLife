public class BankAccount {
	private double balance;
	private double loan;
	
	BankAccount(double balance){
		this.balance = balance;
	}
		
	public double getBalance() {
		return balance;
	}
	
	public void setBalance(double balance) {
		this.balance = balance;
	}
	
	public double getLoan() {
		return loan;
	}
	
	public void setLoan(double loan) {
		this.loan = loan;
	}
}
