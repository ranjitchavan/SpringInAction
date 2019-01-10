
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try(ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("app.xml")){
			
			CrickBuzzServiceImpl cric=context.getBean(CrickBuzzServiceImpl.class,"cricBuzz");
			
			System.out.println(cric.findScore(3303));
		
			
		}
	}

}
