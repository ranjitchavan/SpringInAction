import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class CheckVoting implements InitializingBean,DisposableBean{
	private int age;
	private String name;
	
	public CheckVoting() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CheckVoting(int age, String name) {
		super();
		this.age = age;
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void check() {
		System.out.println("Xml based Init");
	if(age<18||name==null)
		throw new IllegalArgumentException("name age must be set with values");
	}
	
	public void destroy1() {
		
		System.out.println("Destroy Method called");
		age=0;
		name=null;
		System.gc();
	}
	public boolean voterIsValid() {
		
		if(age>=18)
			return true;
		else
			return false;
		
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Interface Init");
	}

	public void destroy() {
		
	System.out.println("Interface Destroy");
	}
	@PostConstruct
	public void postConstruct() {
		System.out.println("Annotation Based init");
		
	}
	@PreDestroy
	public void preDestory() {
		System.out.println("Annotation Based Destroy");
		
	}
}
