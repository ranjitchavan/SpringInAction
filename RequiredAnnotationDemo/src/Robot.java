import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;

public class Robot {
	private String name;
	private int modelNo;
	public String getName() {
		return name;
	}
	@Autowired(required=true)
	public void setName(String name) {
		this.name = name;
	}
	public int getModelNo() {
		return modelNo;
	}
	public void setModelNo(int modelNo) {
		this.modelNo = modelNo;
	}
	
}
