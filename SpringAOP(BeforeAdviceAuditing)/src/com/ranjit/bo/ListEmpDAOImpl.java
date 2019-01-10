package com.ranjit.bo;


import java.util.HashMap;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Component;
@Component("emp")
public class ListEmpDAOImpl {
	@Autowired(required=true)
	private SimpleJdbcCall jdbc;
	
	
	
	public void setJdbc(SimpleJdbcCall jdbc) {
		this.jdbc = jdbc;
	}
	public Map<String,Object> getEmployeeNameAndSalaryById(int no) {
		jdbc.setProcedureName("getEmployeeSalaryById");
		HashMap<String,Object> INhash=new HashMap<String,Object>();
		INhash.put("eno", no);
		Map<String,Object> map=jdbc.execute(INhash);
		return map;
	}

}
