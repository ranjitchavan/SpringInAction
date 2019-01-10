package com.ranjit.bo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import javax.sql.DataSource;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.SqlUpdate;
import org.springframework.stereotype.Component;

@Component("emp")
public class ListEmpDAOImpl  {
	@Autowired(required=true)
	private DataSource dataSource;
	private static final String QUERY="UPDATE  EMPLOYEE SET JOB=? WHERE EMP_NO=?";
	
	public void setJdbc(DataSource jdbc) {
		this.dataSource = jdbc;
	}
		public int updateEmployee(int no,String address) {
			
			UpdateSql sql=new UpdateSql(dataSource,QUERY);
			return sql.updateDetails(no, address);
			
		}

		
		class UpdateSql extends SqlUpdate{
			
			public UpdateSql(DataSource ds,String query) {
				
				super(ds,query);
				super.declareParameter(new SqlParameter(Types.VARCHAR));
				super.declareParameter(new SqlParameter(Types.VARCHAR));
				super.compile();
			}
			public int updateDetails(int no,String newAddress) {
				
				return super.update(newAddress,no);
				
				
			}
			
		}
}

