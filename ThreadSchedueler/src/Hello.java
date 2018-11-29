import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Hello {
	public static void main(String a[]) {
		ExecutorService service=Executors.newFixedThreadPool(12);
		for(int i=0;i<41;i++)
			service.submit(Hello::job);
		
		service.shutdown();
		
	}
	
	
	public static void job() {
		
		for(int i=0;i<=1000000;i++) {
			System.out.println(Thread.currentThread().getName()+"");
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}
