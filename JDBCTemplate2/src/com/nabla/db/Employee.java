package com.nabla.db;

public class Employee {
	private int emp_no;
	private String ename;
	private String job;
	private long salary;
	
	public int getNo() {
		return emp_no;
	}
	public void setNo(int no) {
		this.emp_no = no;
	}
	public String getName() {
		return ename;
	}
	public void setName(String name) {
		this.ename = name;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public long getSal() {
		return salary;
	}
	public void setSal(long sal) {
		this.salary = sal;
	}
	@Override
	public String toString() {
		return "Employee [no=" + emp_no + ", name=" + ename + ", job=" + job + ", sal=" + salary + "]";
	}
	
}
