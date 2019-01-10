import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
	public static void main(String[] args) {
		try (ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("app.xml")){
			
			Triangle tr=context.getBean(Triangle.class,"triangle");

			
			
			Triangle tr1=context.getBean(Triangle.class,"triangle");
			
		
			
			
			
		}
		System.out.println("Main End");
	}
}
