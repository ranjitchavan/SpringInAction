import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class Vehicle implements ApplicationContextAware {
	private ApplicationContext ctx;
	private Enginee enggId;
	public Enginee getEnggId() {
		return enggId;
	}
	public void setEnggId(Enginee enggId) {
		this.enggId = enggId;
	}
	@Override
	public void setApplicationContext(ApplicationContext ctx) throws BeansException {
		// TODO Auto-generated method stub
		System.out.println("Vehicle :setApplicationContex");
		this.ctx=ctx;
		
	}
	public Vehicle() {
		System.out.println("Vehicle: 0 params constructor");
	}
	public void setEnggld(String enggId) {
		System.out.println("Vechile :Set enggld");
	}
	
	public  void move() {
		Enginee engine =ctx.getBean(Enginee.class,"engg");
		engine.start();
	}

}
