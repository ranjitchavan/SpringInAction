import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
	public static void main(String[] args) {
		
		
		try (ClassPathXmlApplicationContext parent=new ClassPathXmlApplicationContext("parent.xml")){
			
			try (ClassPathXmlApplicationContext child=new ClassPathXmlApplicationContext(new String[]{"child.xml"},parent)){
				
				BankLoanApprover bank=child.getBean("bank",BankLoanApprover.class);
				System.out.println(bank.approveLoan());
				
				
			}	
			
		}
		System.out.println("Main End");
	}
}
