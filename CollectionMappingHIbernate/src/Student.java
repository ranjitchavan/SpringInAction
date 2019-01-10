import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="mystudent1")

public class Student {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	private String name;
	private int age;
	@OneToMany(mappedBy="hid")
	private Set<Hobbies> hob;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public Set<Hobbies> getHob() {
		return hob;
	}
	public void setHob(Set<Hobbies> hob) {
		this.hob = hob;
	}
	public Student(int id, String name, int age, Set<Hobbies> hob) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
		this.hob = hob;
	}
	public Student() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
