package services.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class ManageDatabase {
	private Connection conn;
	private Statement stmt;

	public ManageDatabase(String path, String username, String password) throws SQLException {
		this.conn = DriverManager.getConnection(path, username, password);
		this.stmt = conn.createStatement();
		System.out.println("Connected to database");
		createDatabase("course_management_system");
		String query = "USE course_management_system";
		stmt.executeUpdate(query);
		createUserTable();
		createCourseTable();
		createModuleTable();
		createUserModuleTable();
		createUserGradeTable();
	}

	public void createDatabase(String dbName) throws SQLException {
		try {
			stmt.executeUpdate("CREATE DATABASE " + dbName);
			System.out.println("Database created!");
		} catch (SQLException e) {
			if (e.getErrorCode() == 1007)
				System.out.println("Database exists!");
			else
				throw e;
		}
	}

	private void createUserTable() throws SQLException {
		try {
			String createUserTableQuery = "CREATE TABLE user "
					+ "(id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(100), email VARCHAR(100), password VARCHAR(100), role VARCHAR(20))";
			stmt.executeUpdate(createUserTableQuery);
			System.out.println("User Table Created!");

		} catch (SQLException e) {
			if (e.getErrorCode() == 1050)
				System.out.println("user table exists already!");
			else
				throw e;
		}
	}

	private void createCourseTable() throws SQLException {
		try {
			String createCourseTableQuery = "CREATE TABLE course "
					+ "(id INT AUTO_INCREMENT PRIMARY KEY, title VARCHAR(100), is_active BIT(1))";
			stmt.executeUpdate(createCourseTableQuery);
			System.out.println("Course Table Created!");
		} catch (SQLException e) {
			if (e.getErrorCode() == 1050)
				System.out.println("course table exists already!");
			else
				throw e;
		}
	}
	
	private void createModuleTable() throws SQLException {
		try {
			String createModuleTableQuery = "CREATE TABLE module "
					+ "(id INT AUTO_INCREMENT PRIMARY KEY, course_id int NOT NULL, title VARCHAR(100), semester_number int NOT NULL, "
					+ "FOREIGN KEY (course_id) REFERENCES course(id))";
			stmt.executeUpdate(createModuleTableQuery);
			System.out.println("Module Table Created!");
		} catch (SQLException e) {
			if (e.getErrorCode() == 1050)
				System.out.println("module table exists already!");
			else
				throw e;
		}
	}

	private void createUserModuleTable() throws SQLException {
		try {
			String createUserModuleTableQuery = "CREATE TABLE user_module "
					+ "(id INT AUTO_INCREMENT PRIMARY KEY, user_id int NOT NULL, module_id int NOT NULL, "
					+ "FOREIGN KEY (user_id) REFERENCES user(id), FOREIGN KEY (module_id) REFERENCES module(id))";
			stmt.executeUpdate(createUserModuleTableQuery);
			System.out.println("User Module Table Created!");
		} catch (SQLException e) {
			if (e.getErrorCode() == 1050)
				System.out.println("user_module table exists already!");
			else
				throw e;
		}
	}

	private void createUserGradeTable() throws SQLException{
		try {
			String createUserGradeTableQuery = "CREATE TABLE user_grade "
					+ "(id INT AUTO_INCREMENT PRIMARY KEY, user_id int NOT NULL, module_id int NOT NULL, grade int NOT NULL, "
					+ "FOREIGN KEY (user_id) REFERENCES user(id), FOREIGN KEY (module_id) REFERENCES module(id))";
			stmt.executeUpdate(createUserGradeTableQuery);
			System.out.println("User Grade Table Created!");
		} catch (SQLException e) {
			if (e.getErrorCode() == 1050)
				System.out.println("user_grade table exists already!");
			else
				throw e;
		}
	}

	public Connection getConnection() {
		return conn;
	}

	public PreparedStatement getPreparedStatement(String sql) throws SQLException {
	    return conn.prepareStatement(sql);
	}
}
