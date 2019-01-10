import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Entity;
@Entity
@Table(name="HOB1")
public class Hobbies {
	private String nameHob;
	private String  Type;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
		private int hid;
	
	public String getNameHob() {
		return nameHob;
	}
	public void setNameHob(String nameHob) {
		this.nameHob = nameHob;
	}
	public String getType() {
		return Type;
	}
	public void setType(String type) {
		Type = type;
	}
	public int getId() {
		return hid;
	}
	public void setId(int id) {
		this.hid = id;
	}
	public Hobbies(String nameHob, String type, int id) {
		super();
		this.nameHob = nameHob;
		Type = type;
		this.hid = id;
	}
	public Hobbies() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
