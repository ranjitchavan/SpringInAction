package com.nabla.db;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
@Component
public class DBOperationDAO implements DBOperation{
	@Autowired(required=true)
	private NamedParameterJdbcTemplate jdbc;
	
	private final static String INSERT_QRY="INSERT INTO EMPLOYEE (EMP_NO,ENAME,JOB,SALARY) VALUES (:no,:name,:job,:sal)";
	private final static String  GET_SAL_QRY="SELECT SALARY FROM EMPLOYEE WHERE EMP_NO= :no";
	private final static String EMP_DETAILS_QRY="SELECT EMP_NO,ENAME,JOB,SALARY FROM EMPLOYEE WHERE EMP_NO=:no";
	private final static String ALL_EMP_DETAILS_QRY="SELECT EMP_NO,ENAME,JOB,SALARY FROM EMPLOYEE";
	private final static String UPDATE_EMP_DETAILS_QRY="UPDATE EMPLOYEE SET SALARY=:salary WHERE EMP_NO=:emp_no";
	private final static String DELETE_EMP_DETAILS_QRY="DELETE FROM EMPLOYEE WHERE EMP_NO=?";
	@Override
	public int insert(int no, String name, String job, long sal) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("no", no);
		map.put("name", "Ranjit");
		map.put("job", "Sangli");
		map.put("sal","111");
		return jdbc.update(INSERT_QRY,map);
	}

	@Override
	public int getSalary(int no) {
		// TODO Auto-generated method stub
		MapSqlParameterSource map=new MapSqlParameterSource();
		map.addValue("no", 1);
		return jdbc.queryForObject(GET_SAL_QRY,map,Integer.class);
	}

	@Override
	public Employee listEmpDetails(int no) {
		// TODO Auto-generated method stub
		MapSqlParameterSource map=new MapSqlParameterSource();
		map.addValue("no",no);
		return jdbc.queryForObject(EMP_DETAILS_QRY,map,DBOperationDAO::mapRow);
	}
	
	
	@Override
	public List<Employee> listAllEmpDetails() {
		// TODO Auto-generated method stub
		return jdbc.query(ALL_EMP_DETAILS_QRY,DBOperationDAO::getAllEmployeeFromResultSet);
	}

	@Override
	public int updateEmpSalary(int no,long salary) {
		// TODO Auto-generated method stub
		Employee e=new Employee();
		e.setNo(1);
		e.setSal(252222);;
		BeanPropertySqlParameterSource bean=new BeanPropertySqlParameterSource(e);
		return jdbc.update(UPDATE_EMP_DETAILS_QRY,bean);
	}

	@Override
	public int deleteEmp(int no) {
		// TODO Auto-generated method stub
		MapSqlParameterSource map=new MapSqlParameterSource();
		map.addValue("no",no);
		return jdbc.update(DELETE_EMP_DETAILS_QRY,map);
	}
	
	
	
	private static final  Employee mapRow(ResultSet rst, int no) throws SQLException {
		Employee e=new Employee(rst.getInt(1),rst.getString(2),rst.getString(3),Long.parseLong(rst.getString(4)));
		
		return e;
	}
		
	private static List<Employee> getAllEmployeeFromResultSet(ResultSet rst) throws NumberFormatException, SQLException{
			List<Employee> emp=new ArrayList<Employee>();
			while(rst.next()) {
				emp.add(new Employee(rst.getInt(1), rst.getString(2), rst.getString(3), Long.valueOf(rst.getString(4))));
				
			}
			return emp;
	}
}
