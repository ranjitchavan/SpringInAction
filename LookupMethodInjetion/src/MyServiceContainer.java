import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class MyServiceContainer implements ApplicationContextAware{
	
	private ApplicationContext context;
	private RequestHandler req;

	public RequestHandler getReq() {
		return req;
	}

	public void setReq(RequestHandler req) {
		this.req = req;
	}

	public MyServiceContainer() {
		super();
		
	}

	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		// TODO Auto-generated method stub
		this.context=context;
	}
	
	public void processRequest(String data) {
		
		RequestHandler req=	context.getBean(RequestHandler.class,"request");
		req.handle(data);
	}
	
	
}
