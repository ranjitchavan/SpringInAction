import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
	public static void main(String[] args) {
		try (AbstractApplicationContext context=new ClassPathXmlApplicationContext("app.xml")){
			context.registerShutdownHook();
			Triangle tr=context.getBean(Triangle.class,"triangle");

			
			System.out.println("Point A"+tr.getPointA().hashCode());
			System.out.println("Point B"+tr.getPointB().hashCode());
			System.out.println("Point C"+tr.getPointC().hashCode());
			System.out.println("HashCode"+tr.hashCode());
			Triangle tr1=context.getBean(Triangle.class,"triangle");
			
			System.out.println("Point A"+tr1.getPointA().hashCode());
			System.out.println("Point B"+tr1.getPointB().hashCode());
			System.out.println("Point C"+tr1.getPointC().hashCode());
			System.out.println("HashCode"+tr1.hashCode());
			
			
			Point pa1=context.getBean("pointA",Point.class);
			Point pa2=context.getBean("pointA",Point.class);
			System.out.println(pa1.hashCode());
			System.out.println(pa2.hashCode());
		}
	}
}
