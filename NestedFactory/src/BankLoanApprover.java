
public class BankLoanApprover {
	private LoanDetails load;

	public LoanDetails getLoad() {
		return load;
	}

	public void setLoad(LoanDetails load) {
		this.load = load;
	}

	public String toString() {
		return "BankLoanApprover [getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
	
	public String approveLoan() {
			if(load.getLoanType().equals("two")) 
				return "APPROVED";
			else
			return "REJECTED";		
		
	}
}
