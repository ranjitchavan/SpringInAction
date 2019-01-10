package com.nabla.db;
import java.util.*;
public interface DBOperation {
	public int insert(int no,String name,String job,long sal);
	public int getSalary(int no);
	public Map<String,Object>listEmpDetails(int no);
	public List<Map<String,Object>> listAllEmpDetails();
	public int updateEmpSalary(int no,long salary);
	public int deleteEmp(int no);
}
