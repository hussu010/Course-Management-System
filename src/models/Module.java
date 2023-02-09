package models;

public class Module {

	private String title;
	private int id;
	private int course_id;
	private String course_title;
	private int semester_number;

	public Module(int id, String title, int course_id, int semester_number, String course_title) {
		this.id = id;
		this.title = title;
		this.course_id = course_id;
		this.course_title = course_title;
		this.semester_number = semester_number;
	}

	public Module(int id, String title, int course_id, int semester_number) {
		this(id, title, course_id, semester_number, null);
	}

	public String getTitle() {
		return title;
	}

	public String getCourseTitle() {
		return course_title;
	}

	public int getId() {
		return id;
	}

	public int getCourseId() {
		return course_id;
	}
	
	public int getSemesterNumber() {
		return semester_number;
	}
}
