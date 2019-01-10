package com.nabla.db;


import java.util.List;
import java.util.Map;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
	public static void main(String[] args) {
		try(ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("app.xml")){
			
			DBOperationService dp=context.getBean(DBOperationService.class,"dao");
			//dp.insertEmployee(1, "Ranjit", "CSE", 25000);
		/*	dp.insertEmployee(2, "Ranjit2", "CSE", 25000);
			dp.insertEmployee(3, "Ranjit3", "CSE", 25000);
			dp.insertEmployee(4, "Ranjit4", "CSE", 25000);
			dp.insertEmployee(5, "Ranjit5", "CSE", 25000);
			dp.insertEmployee(6, "Ranjit6", "CSE", 25000);
			dp.insertEmployee(7, "Ranjit7", "CSE", 25000);
			
			*/
			Map <String,Object> e=dp.getEmpDelatils(7);
			System.out.println(e);
			
			List<Map<String,Object>> listAllEmp=dp.getAllEmpDetails();
			System.out.println(listAllEmp);
			
			listAllEmp.stream()
								.filter(App::EmployeegreaterThan3)
								.forEach(System.out::println);
			dp.updateSalary(7, 3000000);
			System.out.println(dp.getSalaryOfEmployee(1));
			dp.fireEmployee(5);
			listAllEmp.stream()
			.filter(App::EmployeegreaterThan3)
			.forEach(System.out::println);
		}
	}
	public static boolean EmployeegreaterThan3(Map<String,Object> map) {
			return ((Integer)map.get("EMP_NO"))>3;
	}
}
