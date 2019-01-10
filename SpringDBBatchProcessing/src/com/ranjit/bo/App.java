package com.ranjit.bo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
	public static void main(String[] args) {
		try(ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("app.xml")){
			List<EmployeeDTO> empDTO=new ArrayList<EmployeeDTO>();
			empDTO.add(new EmployeeDTO(61,"Jack1","Sangli",5652552));
			empDTO.add(new EmployeeDTO(7,"Jack2","Sangli",5652552));
			empDTO.add(new EmployeeDTO(8,"Jack3","Sangli",5652552));
			empDTO.add(new EmployeeDTO(9,"Jack4","Sangli",5652552));
			empDTO.add(new EmployeeDTO(10,"Jac5k","Sangli",5652552));
			empDTO.add(new EmployeeDTO(11,"Jack6","Sangli",5652552));
			
			EmployeeBatchInsertService  service=context.getBean(EmployeeBatchInsertService.class,"empService");
			System.out.println(service.registerBatchOfEmployee(empDTO));
			
			
		}
	}
}
