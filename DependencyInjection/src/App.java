
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try(ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("app.xml")){
		
			Class flip=context.getBean("class",Class.class);
			System.out.println(flip);
			
		}
	}

}
