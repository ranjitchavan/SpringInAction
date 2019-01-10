import java.util.Date;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class BaseBoBeanPostProcessor implements BeanPostProcessor {

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		// TODO Auto-generated method stub
		System.out.println("Bean Post Processor After iniaLIZTION");
		
		
		if(bean instanceof BaseBo) {
			System.out.println("BaseBoBeanPostProcessor.postProcessAfterInitialization().instanceof BaseBo");
			((BaseBo)bean).setDoj(new Date());
		}
		return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		// TODO Auto-generated method stub
		System.out.println("Bean Post Processor before"
				+ "iniaLIZTION");
		
		return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
	}
	
}
