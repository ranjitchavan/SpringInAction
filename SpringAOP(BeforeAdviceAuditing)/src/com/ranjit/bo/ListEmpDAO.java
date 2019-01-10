package com.ranjit.bo;

import java.util.List;

public interface ListEmpDAO {
	public EmployeeBO getEmpDetailsByNo(int no);
	public List<EmployeeBO> getAllEmpDetails();
}
