
public class RequestHandler {
	private static int count=0;
	public RequestHandler(int a) {
	count++;
	}
	
	public void handle(String data) {
		System.out.println("Handle Request "+count+" with data"+data);
		
	}
	
	
	
}
