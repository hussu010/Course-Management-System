package services.admin;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.Module;
import models.UserModule;
import services.common.Auth;
import models.User;

public class ManageModule {

	private static PreparedStatement createNewModule;
	private static PreparedStatement listCourseModules;
	private static PreparedStatement updateModule;
	private static PreparedStatement enrollModule;
	private static PreparedStatement unenrollModule;
	private static PreparedStatement getUserModule;
	private static PreparedStatement getModuleUsers;
	private static PreparedStatement getUserModules;
	private static PreparedStatement getAllUserOfRole;
	private static PreparedStatement checkUserModuleExists;
	private static PreparedStatement addUserGrade;
	private static PreparedStatement deletePreviousGrade;
	private static PreparedStatement getUserModuleGrade;
	private static PreparedStatement getUserSemesterModules;
	private static PreparedStatement getUserSemesterGrade;

	public ManageModule(services.common.ManageDatabase db) throws SQLException {
		createNewModule = db.getPreparedStatement("INSERT INTO module (course_id, title, semester_number) VALUES (?,?,?)");
		updateModule= db.getPreparedStatement("UPDATE module SET title=? WHERE id=?");
		listCourseModules = db.getPreparedStatement("SELECT * FROM module WHERE course_id=?");
		enrollModule = db.getPreparedStatement("INSERT INTO user_module (module_id, user_id) VALUES (?,?)");
		unenrollModule = db.getPreparedStatement("DELETE from user_module WHERE user_id=? and module_id=?");
		getUserModule = db.getPreparedStatement("SELECT * FROM user_module WHERE user_id=?");
		checkUserModuleExists = db.getPreparedStatement("SELECT * FROM user_module WHERE user_id=? and module_id=?");
		getModuleUsers = db.getPreparedStatement("SELECT * FROM user as u,"
				+ "user_module as um, "
				+ "module as m "
				+ "where u.id = um.user_id "
				+ "and um.module_id = m.id "
				+ "and um.module_id = ? "
				+ "and u.role=?"
			); 
		getUserModules = db.getPreparedStatement("SELECT * FROM user as u,"
				+ "user_module as um, "
				+ "module as m, "
				+ "course as c "
				+ "where u.id = um.user_id "
				+ "and um.module_id = m.id "
				+ "and m.course_id = c.id "
				+ "and um.user_id = ?"
			);
		getUserSemesterModules = db.getPreparedStatement("SELECT * FROM user as u,"
				+ "user_module as um, "
				+ "module as m "
				+ "where u.id = um.user_id "
				+ "and um.module_id = m.id "
				+ "and um.user_id = ? "
				+ "and m.semester_number=?"
			);
		getAllUserOfRole = db.getPreparedStatement("SELECT * FROM user WHERE role=?");
		addUserGrade = db.getPreparedStatement("INSERT INTO user_grade (user_id, module_id, grade) VALUES (?,?,?)");
		deletePreviousGrade = db.getPreparedStatement("DELETE FROM user_grade WHERE  user_id=? and module_id=?");
		getUserModuleGrade = db.getPreparedStatement("SELECT * from user_grade WHERE user_id=? and module_id=?");
		getUserSemesterGrade = db.getPreparedStatement("SELECT * from user_grade as ug,"
				+ "module as m "
				+ "where ug.module_id=m.id "
				+ "and ug.user_id=? "
				+ "and m.semester_number=?");
	}

	public static List<Module> listCourseModule(int course_id) throws Exception {
		try {
			listCourseModules.setInt(1, course_id);
			ResultSet results = listCourseModules.executeQuery();
			List<Module> moduleList = new ArrayList<Module>();

			while(results.next()){
				Module module = new Module(results.getInt("id"), results.getString("title"), results.getInt("course_id"), results.getInt("semester_number"));
				moduleList.add(module);
			}
			return moduleList;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception("Error occured while fetching modules");
		}
	}

	public static void createModule(int course_id, String title, int semester_number) throws Exception {
		try {
			createNewModule.setInt(1, course_id);
			createNewModule.setString(2, title);
			createNewModule.setInt(3, semester_number);
			createNewModule.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void editModule(int id, String title) throws Exception {
		try {
			updateModule.setString(1, title);
			updateModule.setInt(2, id);
			updateModule.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void enrollModule(int user_id, int module_id) throws Exception {
		try {
			Auth.checkUserWithIdExists(user_id);
			checkUserModuleExists.setInt(1, user_id);
			checkUserModuleExists.setInt(2, module_id);
			ResultSet results = checkUserModuleExists.executeQuery();
			if (results.next()) {
				throw new Exception("User already associated with module.");
			} else {
				enrollModule.setInt(1, module_id);
				enrollModule.setInt(2, user_id);
				enrollModule.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
	}

	public static void unenrollModule(int user_id, int module_id) throws Exception {
		try {
			unenrollModule.setInt(1, user_id);
			unenrollModule.setInt(2, module_id);
			unenrollModule.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static List<UserModule> getUserModules(int user_id) throws Exception{
		try {
			getUserModule.setInt(1, user_id);
			ResultSet results = getUserModule.executeQuery();
			List<UserModule> userModuleList = new ArrayList<UserModule>();

			while(results.next()){
				UserModule userModule = new UserModule(results.getInt("id"), results.getInt("user_id"), results.getInt("module_id"));
				userModuleList.add(userModule);
			}
			return userModuleList;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception("Error occured while fetching user modules");
		}
	}

	public static boolean isUserEnrolledToModule(List<UserModule> userModuleList, int user_id, int module_id ) {
		return userModuleList.stream()
				.filter(o -> o.getUserId() == user_id)
				.filter(o -> o.getModuleId() == module_id)
				.findFirst()
				.isPresent();
	}

	public static List<User> getModuleUsers(int module_id, String role) throws Exception {
		try {
			getModuleUsers.setInt(1, module_id);
			getModuleUsers.setString(2, role);
			ResultSet results = getModuleUsers.executeQuery();
			List<User> userList = new ArrayList<User>();

			while(results.next()){
				User user = new User(results.getInt("user_id"), results.getString("name"), results.getString("email"), results.getString("role"));
				userList.add(user);
			}
			return userList;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception("Error occured while fetching user modules");
		}
	}

	public static List<Module> getUserSemesterModules(int user_id, int semester) throws Exception {
		try {
			getUserSemesterModules.setInt(1, user_id);
			getUserSemesterModules.setInt(2, semester);
			ResultSet results = getUserSemesterModules.executeQuery();
			List<Module> moduleList = new ArrayList<Module>();

			while(results.next()){
				int module_id = results.getInt("module_id");
				String module_title = results.getString("title");
				int course_id = results.getInt("course_id");
				int semester_number = results.getInt("semester_number");

				Module module = new Module(module_id, module_title, course_id, semester_number);
				moduleList.add(module);
			}
			return moduleList;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception("Error occured while fetching user modules");
		}
	}
	
	public static List<Module> getUserAllModules(int user_id) throws Exception {
		try {
			getUserModules.setInt(1, user_id);
			ResultSet results = getUserModules.executeQuery();
			List<Module> moduleList = new ArrayList<Module>();

			while(results.next()){
				int module_id = results.getInt("module_id");
				String module_title = results.getString("title");
				int course_id = results.getInt("course_id");
				int semester_number = results.getInt("semester_number");
				String course_title = results.getString(14);

				Module module = new Module(module_id, module_title, course_id, semester_number, course_title);
				moduleList.add(module);
			}
			return moduleList;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception("Error occured while fetching user modules");
		}
	}


	public static List<User> getAllUserOfRole(String role) throws Exception {
		try {
			getAllUserOfRole.setString(1, role);
			ResultSet results = getAllUserOfRole.executeQuery();
			List<User> userList = new ArrayList<User>();

			while(results.next()){
				int user_id = results.getInt("id");
				String user_name = results.getString("name");
				String user_email = results.getString("email");
				String user_role = results.getString("role");

				User user = new User(user_id, user_name, user_email, user_role);
				userList.add(user);
			}
			return userList;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception("Error occured while fetching user modules");
		}
	}

	public static void addUserGrade(int user_id, int module_id, int grade) throws Exception {
		try {
			deletePreviousGrade.setInt(1, user_id);
			deletePreviousGrade.setInt(2, module_id);
			deletePreviousGrade.executeUpdate();

			addUserGrade.setInt(1, user_id);
			addUserGrade.setInt(2, module_id);
			addUserGrade.setInt(3, grade);
			addUserGrade.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
	}

	public static int getUserModuleGrade(int user_id, int module_id) throws Exception {
		try {
			getUserModuleGrade.setInt(1, user_id);
			getUserModuleGrade.setInt(2, module_id);
			ResultSet results = getUserModuleGrade.executeQuery();
			if (results.next()) {
				return results.getInt("grade");
			}
			return 0;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
	}

	public static int getUserSemesterGrade(int user_id, int semester_id) throws Exception {
		
		int final_grade = 0;

		try {
			getUserSemesterGrade.setInt(1, user_id);
			getUserSemesterGrade.setInt(2, semester_id);
			ResultSet results = getUserSemesterGrade.executeQuery();

			while(results.next()){
				final_grade += results.getInt("grade");
			}
			return final_grade;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
	}
}
