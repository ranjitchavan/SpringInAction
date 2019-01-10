package com.ranjit.bo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import javax.sql.DataSource;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.stereotype.Component;

@Component("emp")
public class ListEmpDAOImpl  {
	@Autowired(required=true)
	private DataSource dataSource;
	private static final String QUERY="SELECT * FROM EMPLOYEE WHERE EMP_NO=?";
	
	public void setJdbc(DataSource jdbc) {
		this.dataSource = jdbc;
	}
	public List<EmployeeBO> selectStudentById(int no) {
		
		 InnerSQL i=new InnerSQL(dataSource,QUERY);
		 return i.findEmployeeById(no);
		
	}
	private class InnerSQL extends MappingSqlQuery<EmployeeBO>{
		private DataSource jdbc;
		
		public InnerSQL(DataSource jdbc,String Query) {
			super(jdbc,Query);
			this.jdbc = jdbc;
			super.declareParameter(new SqlParameter(Types.VARCHAR));
			super.compile();
			
		}

		@Override
		protected EmployeeBO mapRow(ResultSet rst, int arg1) throws SQLException {
			System.out.println("ListEmpDAOImpl.InnerSQL.mapRow()");
			return new EmployeeBO(rst.getInt(1),rst.getString(2),rst.getString(3),Long.parseLong(rst.getString(4)));
		}
		public List<EmployeeBO> findEmployeeById(int no){
			
			return super.execute(no);
		}
		
	}
}
