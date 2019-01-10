package com.ranjit.bo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component("empService")
public class EmployeeBatchInsertService {
	@Autowired(required=true)	
	private EmployeeInsertDAO dao;

	public void setDao(EmployeeInsertDAO dao) {
		this.dao = dao;
	}
	
	public String registerBatchOfEmployee(List<EmployeeDTO> emp) {
		
			List<EmployeeBO> empNo=new ArrayList<EmployeeBO>();
				for(EmployeeDTO dto:emp) {
					empNo.add(new EmployeeBO(dto.getEmp_no(), dto.getName(), dto.getAddress(), dto.getSalary()));
					
				}
				int result[]=dao.insert(empNo);
				
				return result!=null?"Batch Updated Successfully":"Batch Update failed";
	}
	

}
