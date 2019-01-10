
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try(ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("app.xml")){
		MyServiceContainer container=context.getBean(MyServiceContainer.class,"my");
		RequestHandler req1=	context.getBean(RequestHandler.class,"request");
		RequestHandler req2=	context.getBean(RequestHandler.class,"request");
		RequestHandler req3=	context.getBean(RequestHandler.class,"request");
		/*container.processRequest("11");
		container.processRequest("1");
		
		container.processRequest("2");
		
		container.processRequest("3");
		*/
		
		
			
		}
	}

}
