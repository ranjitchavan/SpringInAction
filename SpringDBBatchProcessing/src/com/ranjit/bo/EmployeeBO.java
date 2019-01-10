package com.ranjit.bo;

public class EmployeeBO {
	private int emp_no;
	private String name;
	private String address;
	private long salary;
	
	
	public EmployeeBO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public EmployeeBO(int emp_no, String name, String address, long salary) {
		super();
		this.emp_no = emp_no;
		this.name = name;
		this.address = address;
		this.salary = salary;
	}
	public int getEmp_no() {
		return emp_no;
	}
	public void setEmp_no(int emp_no) {
		this.emp_no = emp_no;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public long getSalary() {
		return salary;
	}
	public void setSalary(long salary) {
		this.salary = salary;
	}
	
	@Override
	public String toString() {
		return "EmployeeBO [emp_no=" + emp_no + ", name=" + name + ", address=" + address + ", salary=" + salary + "]";
	}
	
}
