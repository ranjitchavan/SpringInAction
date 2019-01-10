
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try(ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("app.xml")){
		Bank b=context.getBean(Bank.class,"bank");
		System.out.println(b.interestCalculater(5000, 1));
		b.doNo();
			
		}
	}

}
