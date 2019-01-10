import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.FactoryBean;

public class ICCScoreCompServiceLocator implements FactoryBean<ExternalICCScoreCompService> {
	private Map<String,ExternalICCScoreCompService> cache=new HashMap <String,ExternalICCScoreCompService>();
	private String jndi;
	public ICCScoreCompServiceLocator(String jndi) {
		this.jndi=jndi;
		
	}
	
	
	@Override 
	public ExternalICCScoreCompService getObject() throws Exception {
		ExternalICCScoreCompService service=null;
		if(!cache.containsKey(jndi)) {
			service=new ExternalICCScoreCompServiceImpl();
			cache.put(jndi, service);
		}
		// TODO Auto-generated method stub
		return cache.get(jndi);
	}

	@Override
	public Class<?> getObjectType() {
		// TODO Auto-generated method stub
		return ExternalICCScoreCompService.class;
	}
	@Override
	public boolean isSingleton() {
		return true;
	}
}
