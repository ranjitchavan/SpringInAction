import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {

	public App() {
		// TODO Auto-generated constructor stub
	}
	public static void main(String[] args) {
		 try(ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("app.xml")){
	
		 College c=(College)context.getBean("college");
		 	c.showStudent();
		 
		 
		 }
	}
}
