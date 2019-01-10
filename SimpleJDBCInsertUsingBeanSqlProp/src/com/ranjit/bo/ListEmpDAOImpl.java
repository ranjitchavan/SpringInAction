package com.ranjit.bo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
@Component("emp")
public class ListEmpDAOImpl  {
	@Autowired(required=true)
	private SimpleJdbcInsert jdbc;
	
	
	public void setJdbc(SimpleJdbcInsert jdbc) {
		this.jdbc = jdbc;
	}
	public int insert(EmployeeBO bo) {	
		jdbc.setTableName("employee");
		BeanPropertySqlParameterSource map=new BeanPropertySqlParameterSource(bo);;
		
		return jdbc.execute(map);
	}
	
}
