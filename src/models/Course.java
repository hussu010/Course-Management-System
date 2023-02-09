package models;

public class Course {
	
	private String title;
	private int id;
	private int is_active;
	
	public Course(int id, String title, int is_active) {
		this.id = id;
		this.title = title;
		this.is_active = is_active;
	}

	public String getTitle() {
		return title;
	}

	public int getId() {
		return id;
	}
	
	public String getStatus() {
		if (is_active == 0) {
			return "CANCELLED";
		} else {
			return "ACTIVE";
		}
	}
}
