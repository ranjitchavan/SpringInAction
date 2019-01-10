package com.ranjit.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
@Component("dao")
public class EmployeeInsertDAO {
		@Autowired(required=true)
		private JdbcTemplate jdbc;
		private static final String EMPLOYEE_INSERT_QRY="INSERT INTO EMPLOYEE VALUES(?,?,?,?)";
		
		public void setJdbc(JdbcTemplate jdbc) {
			this.jdbc = jdbc;
		}
		
		public  int[] insert(final List<EmployeeBO> listbo) {
			return jdbc.batchUpdate(EMPLOYEE_INSERT_QRY,new BatchProcessor(listbo));
		}
}
