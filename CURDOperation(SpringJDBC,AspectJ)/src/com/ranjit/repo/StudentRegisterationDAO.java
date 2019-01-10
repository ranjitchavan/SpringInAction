package com.ranjit.repo;

import org.springframework.stereotype.Repository;

import com.ranjit.bo.StudentBO;

@Repository("studentDAO")
public class StudentRegisterationDAO {
	
	public boolean registerStudent(StudentBO s) {
			System.out.println("StudentRegisterationDAO.registerStudent()");
			return true;
	}
}
