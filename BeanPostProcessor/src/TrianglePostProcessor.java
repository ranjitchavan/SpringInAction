import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class TrianglePostProcessor implements BeanPostProcessor{

	
	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		// TODO Auto-generated method stub
		System.out.println("After Initalized");
		return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
	}
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		System.out.println("Before Initalized "+bean.getClass().getName() + "\t Bean Name :"+beanName);
		
		return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
	}
	
	
}
