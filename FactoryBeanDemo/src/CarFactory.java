import org.springframework.beans.factory.FactoryBean;


public class CarFactory implements FactoryBean<Car> {
	private int year;
	private String make;
	
	
	public void setYear(int year) {
		this.year = year;
	}

	public void setMake(String make) {
		this.make = make;
	}

	@Override
	public Car getObject() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<?> getObjectType() {
		// TODO Auto-generated method stub
		return null;
	}

	

}
