import java.time.Duration;
import java.time.LocalTime;
public class ApplicationStartTime {
	LocalTime lt1,lt2;
	public void startTime(){
		System.out.println("Application Start");
		lt1=LocalTime.now();
		
	}
	public void endTime(){
		lt2=LocalTime.now();
		Duration l=Duration.between(lt1, lt2);
		System.out.println("Application End");
		System.out.println("Application Execution time ::"+l.getNano());
	}
}
