import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class Vechial {
	@Autowired
	@Qualifier("ssss")
	private Engine eng;

	public Engine getEng() {
		return eng;
	}
	
	public void setEng(Engine eng) {
		this.eng = eng;
	}

	@Override
	public String toString() {
		return "Vechial [eng=" + eng + "]";
	}
	
}
