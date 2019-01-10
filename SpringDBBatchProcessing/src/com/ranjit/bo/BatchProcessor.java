package com.ranjit.bo;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;

public class BatchProcessor implements BatchPreparedStatementSetter {
	private List<EmployeeBO> listbo;
	
	public BatchProcessor(List<EmployeeBO> listbo) {
		super();
		this.listbo = listbo;
	}

	@Override
	public int getBatchSize() {
		// TODO Auto-generated method stub
		return listbo.size();
	}

	@Override
	public void setValues(PreparedStatement pr, int index) throws SQLException {
		// TODO Auto-generated method stub
			pr.setInt(1, listbo.get(index).getEmp_no());
			pr.setString(2, listbo.get(index).getName());
			pr.setString(3, listbo.get(index).getAddress());
			pr.setString(4,""+listbo.get(index).getSalary());
			
	}

}
