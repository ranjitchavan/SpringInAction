import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class Triangle /*implements InitializingBean,DisposableBean*/ {
	private Point pointA;
	private Point pointB;
	private Point pointC;
	
	public Point getPointA() {
		return pointA;
	}
	public void setPointA(Point pointA) {
		this.pointA = pointA;
	}
	public Point getPointB() {
		return pointB;
	}
	public void setPointB(Point pointB) {
		this.pointB = pointB;
	}
	public Point getPointC() {
		return pointC;
	}
	public void setPointC(Point pointC) {
		this.pointC = pointC;
	}
	public Triangle() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Triangle(Point pointA, Point pointB, Point pointC) {
		super();
		System.out.println("Triangle Construcotr");
		this.pointA = pointA;
		this.pointB = pointB;
		this.pointC = pointC;
		
	}
	
	@Override
	public String toString() {
		return "Triangle [pointA=" + pointA + ", pointB=" + pointB + ", pointC=" + pointC + "]";
	}
	//@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Bean initalized");
		System.out.println("point A"+pointA);
	}
	//@Override
	public void destroy() throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Destroyed bean by container");
	}
	
	
	
}
