import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
@Component("fu")
public class Flipkart {
	@Autowired
	private Courier cor;
	private int orderId;
	public Courier getCor() {
		return cor;
	}

	public void setCor(Courier cor) {
		this.cor = cor;
	}
	public void purchase(String...name) {
		
		orderId=new Random().nextInt(1000000);
		
	}

	@Override
	public String toString() {
		return "Flipkart [cor=" + cor + ", orderId=" + orderId + "]";
	}
	
	
	
}
