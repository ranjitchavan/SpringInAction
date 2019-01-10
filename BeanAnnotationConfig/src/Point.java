import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;



public class Point {
	@Value(value="10")
	private int x;
	@Value(value="20")
	private int y;
	
	public int getX() {
		return x;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	@Override
	public String toString() {
		
		
		
		return "Point [x=" + x + ", y=" + y + "]";
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public Point(int x, int y) {
		super();

		this.x = x;
		this.y = y;
	}
	public Point() {
		super();
		// TODO Auto-generated constructor stub
	}
}
