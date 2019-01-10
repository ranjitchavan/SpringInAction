import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
	public static void main(String[] args) {
		try (ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("app.xml")){
			
			Robot r=context.getBean(Robot.class,"robot");
				System.out.println(r.getName()+" "+r.getModelNo());
		}	
		System.out.println("Main End");
	}
}
