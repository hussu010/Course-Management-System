package services.common;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import models.User;

public class Auth {
	
	private static PreparedStatement getUserStatement;
	private static PreparedStatement checkEmailExistenceStatement;
	private static PreparedStatement addUserStatement;
	private static PreparedStatement checkUserWithIdExists;

	public Auth(services.common.ManageDatabase db) throws SQLException {
		checkEmailExistenceStatement = db.getPreparedStatement("SELECT email FROM user WHERE email=?");
		checkUserWithIdExists = db.getPreparedStatement("SELECT * FROM user WHERE id=?");
		addUserStatement = db.getPreparedStatement("INSERT INTO user (name, email, password, role) VALUES (?,?,?,?)");
		getUserStatement = db.getPreparedStatement("SELECT id,name,role,email,password FROM user WHERE email=? AND password=?");
	}

	public static void createUser(String name, String email, String password, String role) throws Exception {
		try {
			checkEmailExistenceStatement.setString(1, email);
			ResultSet results = checkEmailExistenceStatement.executeQuery();
			if (results.next()) {
				throw new Exception("Cannot create two users with same email.");
			} else {
				addUserStatement.setString(1, name);
				addUserStatement.setString(2, email);
				addUserStatement.setString(3, password);
				addUserStatement.setString(4, role);
				addUserStatement.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static User getUser(String email, String password) throws Exception {
		try {
			getUserStatement.setString(1, email);
			getUserStatement.setString(2, password);
			ResultSet results = getUserStatement.executeQuery();
			if (!results.next()) {
				throw new Exception("Invalid credentials.");
			} else {
				return new User(results.getInt("id"), results.getString("name"), results.getString("email"), results.getString("role"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception("Error occured while logging in");
		}
	}

	public static boolean checkUserWithIdExists(int user_id) throws Exception {
		try {
			checkUserWithIdExists.setInt(1, user_id);
			ResultSet results = checkUserWithIdExists.executeQuery();
			if (!results.next()) {
				throw new Exception("User with id does not exists.");
			}
			return true;
		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		}
	}
}
