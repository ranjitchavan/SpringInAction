
public class Student {
	private int rollno;
	private String name,address,gen;

	public int getRollno() {
		return rollno;
	}
	public void setRollno(int rollno) {
		this.rollno = rollno;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getGen() {
		return gen;
	}
	public void setGen(String gen) {
		this.gen = gen;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "{ID:"+this.rollno+",Name:"+this.name+", Address="+this.address+", Gender="+this.gen+"}";
	}
	
}
