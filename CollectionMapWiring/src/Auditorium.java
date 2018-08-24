import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Auditorium {

	public Auditorium() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try(ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("app.xml")){
			
			Ranjit r=(Ranjit)context.getBean("ranjit");

			r.perform();
			
			Danny d=(Danny)context.getBean("danny");
			d.perform();
		}
	}

}
