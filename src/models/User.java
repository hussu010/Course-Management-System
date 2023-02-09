package models;

public class User {
	private int id;
	private String name;
	private String role;
	private String email;

	public User(int id, String name, String email, String role) {
		this.id = id;
		this.name = name;
		this.role = role;
		this.email = email;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getRole() {
		return role;
	}	
}
