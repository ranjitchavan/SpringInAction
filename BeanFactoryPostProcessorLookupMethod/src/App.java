import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
	public static void main(String[] args) {
		try(ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("app.xml")){
			
			CustomerBo c=context.getBean(CustomerBo.class,"cusBo");
			StudentBO s=context.getBean(StudentBO.class,"stuBo");
			
			CustStudDAO cu=new CustStudDAO();
			
			cu.insertData(c);
			cu.insertData(s);
			
			
			
		}
	}
}
