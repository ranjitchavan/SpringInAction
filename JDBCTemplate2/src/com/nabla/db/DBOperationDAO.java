package com.nabla.db;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
@Component
public class DBOperationDAO implements DBOperation{
	@Autowired(required=true)
	private JdbcTemplate jdbc;
	
	private final static String INSERT_QRY="INSERT INTO EMPLOYEE (EMP_NO,ENAME,JOB,SALARY) VALUES (?,?,?,?)";
	private final static String  GET_SAL_QRY="SELECT SALARY FROM EMPLOYEE WHERE EMP_NO= ?";
	private final static String EMP_DETAILS_QRY="SELECT EMP_NO,ENAME,JOB,SALARY FROM EMPLOYEE WHERE EMP_NO=?";
	private final static String ALL_EMP_DETAILS_QRY="SELECT EMP_NO,ENAME,JOB,SALARY FROM EMPLOYEE";
	private final static String UPDATE_EMP_DETAILS_QRY="UPDATE EMPLOYEE SET SALARY=? WHERE EMP_NO=?";
	private final static String DELETE_EMP_DETAILS_QRY="DELETE FROM EMPLOYEE WHERE EMP_NO=?";
	
	@Override
	public int insert(int no, String name, String job, long sal) {
		
		return jdbc.update(INSERT_QRY,no,name,job,sal);
	}

	@Override
	public int getSalary(int no) {
		// TODO Auto-generated method stub
		return jdbc.queryForObject(GET_SAL_QRY,new Object[] {no} ,Integer.class);
	}

	@Override
	public Map<String, Object> listEmpDetails(int no) {
		// TODO Auto-generated method stub
		return jdbc.queryForMap(EMP_DETAILS_QRY, no);
	}

	@Override
	public List<Map<String,Object>> listAllEmpDetails() {
		// TODO Auto-generated method stub
		return jdbc.queryForList(ALL_EMP_DETAILS_QRY);
	}

	@Override
	public int updateEmpSalary(int no,long salary) {
		// TODO Auto-generated method stub
		return jdbc.update(UPDATE_EMP_DETAILS_QRY,salary,no);
	}

	@Override
	public int deleteEmp(int no) {
		// TODO Auto-generated method stub
		return jdbc.update(DELETE_EMP_DETAILS_QRY,no);
	}

}
