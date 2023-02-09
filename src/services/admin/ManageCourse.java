package services.admin;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.Course;

public class ManageCourse {

	private static PreparedStatement createNewCourse;
	private static PreparedStatement listAllCourses;
	private static PreparedStatement updateCourse;
	private static PreparedStatement getCourse;
	private static PreparedStatement deleteCourse;

	public ManageCourse(services.common.ManageDatabase db) throws SQLException {
		createNewCourse = db.getPreparedStatement("INSERT INTO course (title, is_active) VALUES (?,?)");
		listAllCourses = db.getPreparedStatement("SELECT * from course");
		updateCourse = db.getPreparedStatement("UPDATE course SET title=?, is_active=? WHERE id=?");
		getCourse = db.getPreparedStatement("SELECT id,title,is_active FROM course WHERE id=?");
		deleteCourse = db.getPreparedStatement("DELETE FROM course WHERE id=?");
	}

	public static List<Course> listAllCourses() throws Exception {
		try {
			ResultSet results = listAllCourses.executeQuery();
			List<Course> courseList = new ArrayList<Course>();
			
			while(results.next()){
				Course course = new Course(results.getInt("id"), results.getString("title"), results.getInt("is_active"));
				courseList.add(course);
			}
			
			return courseList;
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception("Error occured while fetching courses");
		}
	}

	public static Course getCourse(int course_id) throws Exception {
		try {
			getCourse.setInt(1, course_id);
			ResultSet results = getCourse.executeQuery();
			if (!results.next()) {
				throw new Exception("404 Course Not Found.");
			} else {
				return new Course(results.getInt("id"), results.getString("title"), results.getInt("is_active"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception("Error occured while fetching the course");
		}
	}

	public static void createCourse(String title, boolean is_active) throws Exception {
		try {
			createNewCourse.setString(1, title);
			createNewCourse.setBoolean(2, is_active);
			createNewCourse.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void editCourse(int id, String title, boolean is_active) throws Exception {
		try {
			updateCourse.setString(1, title);
			updateCourse.setBoolean(2, is_active);
			updateCourse.setInt(3, id);
			updateCourse.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void deleteCourse(int id) throws Exception {
		try {
			deleteCourse.setInt(1, id);
			deleteCourse.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
