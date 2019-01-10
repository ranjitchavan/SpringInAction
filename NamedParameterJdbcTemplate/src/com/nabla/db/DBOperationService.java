package com.nabla.db;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component("dao")
public class DBOperationService {
	
	@Autowired
	private DBOperationDAO dao;
	
	public void setDao(DBOperationDAO dao) {
		this.dao = dao;
	}
	
	public boolean insertEmployee(int no,String name,String job,long sal) {
		System.out.println("DBOperationService.insertEmployee()");
		if(dao.insert(no, name, job, sal)>=0) {
			System.out.println("Insert SUC");
			return false;
		}else {
			System.out.println("Insert FAL");
			return true;
		}
	}
	
	public long getSalaryOfEmployee(int no) {
		
	System.out.println("DBOperationService.getSalaryOfEmployee()");
	return dao.getSalary(no);
	}
	
	public Employee getEmpDelatils(int no){
	
		return dao.listEmpDetails(no);
	}
	
	public List<Employee> getAllEmpDetails(){
		return dao.listAllEmpDetails();
		
		
	}
	public boolean updateSalary(int no,long salary) {
		System.err.println( dao.updateEmpSalary(no,salary));
		return dao.updateEmpSalary(no,salary)!=0;
	}
	public String fireEmployee(int no) {
		
		int no1=dao.deleteEmp(no);
		return no1==0?"Employee Not Found":"Employee Found";
	}
}
