import java.lang.reflect.Method;

import org.springframework.beans.factory.support.MethodReplacer;

public class IntresetReplacer implements MethodReplacer {

	@Override
	public Object reimplement(Object arg0, Method arg1, Object[] arg2) throws Throwable {
		// TODO Auto-generated method stub
		if(arg1.getName()=="interestCalculater") {
			double amount=(double)arg2[0];
			int time=(int)arg2[1];
			System.out.println("Doublicate inside");
			return (double)((amount*time*7)/100);
		
		}
		System.out.println("Doublicate outside");
		return 0;
	}

}
