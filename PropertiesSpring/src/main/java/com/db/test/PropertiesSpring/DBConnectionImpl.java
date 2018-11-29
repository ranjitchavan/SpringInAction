package com.db.test.PropertiesSpring;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class DBConnectionImpl {
private static Connection con;
private static DriverManagerDataSource ds;

	public static DriverManagerDataSource getDs() {
	return ds;
}

public static void setDs(DriverManagerDataSource ds) {
	DBConnectionImpl.ds = ds;
}

	@SuppressWarnings("finally")
	public static Connection getConnection() {
			
		try {
			if(con==null) {
				con=ds.getConnection();
			}
		
		}catch(SQLException  e) {
			e.printStackTrace();
		}finally {
			return con;
		}
	}
}
