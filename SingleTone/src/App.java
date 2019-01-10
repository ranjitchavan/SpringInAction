import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class App {
	public static void main(String[] args) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException {
		Single single=Single.getInstance();
		System.out.println(single);
		
		Class<Single> cl=Single.class;
		Constructor<Single>singlCons=cl.getDeclaredConstructor();
		
		singlCons.setAccessible(true);
		
		Single single1=(Single) singlCons.newInstance();
				System.out.println(single1);
	}
}
   