package com.ranjit.bo;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
@Component("dao")
public class AuthenticationDAO {
	@Autowired(required=true)
	private NamedParameterJdbcTemplate jdbc;
	private final static String AUTH_LOGIN="SELECT * FROM STUDENT WHERE USERNAME=:username AND PASSWORD=:password";
	private final static String GET_STUDENT_CLASS="SELECT STUDENT_CLASS FROM STUDENT WHERE USERNAME=:username AND PASSWORD=:password";
	
	public void setJdbc(NamedParameterJdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}
	public boolean authenticateUser(UserBO u) {
			BeanPropertySqlParameterSource sql=new BeanPropertySqlParameterSource(u);
			System.out.println(sql);
			Map<String,Object> obj=jdbc.queryForMap(AUTH_LOGIN,sql);
			System.out.println(obj);
			return !obj.isEmpty();
	}
	
	public String getClassNameStudent(UserBO u) {
		BeanPropertySqlParameterSource bean=new BeanPropertySqlParameterSource(u);
		Map<String,Object> obj=jdbc.queryForMap(GET_STUDENT_CLASS,bean);
		return (String)obj.get("student_class");
	}
	
}
//8179106586