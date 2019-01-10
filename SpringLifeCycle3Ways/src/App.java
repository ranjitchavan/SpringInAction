import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try(ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("app.xml")){
			
			CheckVoting check=context.getBean(CheckVoting.class,"voting");
			
			System.out.println(check.voterIsValid());
			
		
		}
	}

}
