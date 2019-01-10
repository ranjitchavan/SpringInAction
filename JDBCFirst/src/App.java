import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.nabla.db.DBProp;

public class App {
	public static void main(String[] args) {
		try(ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("app.xml")){
			
			DBProp d=context.getBean(DBProp.class);
			System.out.println(d);
			
		}
	}
}
