import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {

	public static void main(String[] args) {
		
		
		try(ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("app.xml")){
			
			
			Student s=(Student)context.getBean("student");
			System.out.println(s);
			
			
			
		}
		

	}

}
