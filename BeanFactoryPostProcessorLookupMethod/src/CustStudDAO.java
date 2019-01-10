
public class CustStudDAO {

	public CustStudDAO() {
		// TODO Auto-generated constructor stub
		System.out.println("DAO 0 Param construtor");
	}
	public void insertData(StudentBO bo) {
		System.out.println("Student Details");
		System.out.println(bo.getId()+ "|"+bo.getName()+" |"+bo.getRollNo()+" |"+bo.getDoj());
		
		
	}
	public void insertData(CustomerBo bo) {
		System.out.println("Customer Details");
		System.out.println(bo.getId()+ "|"+bo.getName()+" |"+bo.getCustBuss()+" |"+bo.getDoj());
		
		
	}
}
