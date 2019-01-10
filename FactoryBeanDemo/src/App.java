
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try(ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("app.xml")){
		
			/*Enginee e=context.getBean("engg",Enginee.class);
			System.out.println(e);*/
			CarFactory v=context.getBean("v",CarFactory.class);
			System.out.println(v);
			
		}
	}

}
