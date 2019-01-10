import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;

public class StudentDAO {
	private JdbcTemplate jdbc;
	private DataSource ds;
	public void setDs(DataSource ds) {
		this.ds = ds;
		jdbc.setDataSource(ds);
	}
	
	@Value("${jdbc.query()}")
	public List<Student> getAllStudent(){
		return null;
		
	}
	
}
