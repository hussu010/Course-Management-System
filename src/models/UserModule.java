package models;

public class UserModule {
	private int id;
	private int user_id;
	private int module_id;
	
	public UserModule(int id, int user_id, int module_id) {
		this.id = id;
		this.user_id = user_id;
		this.module_id = module_id;
	}
	
	public int getId() {
		return id;
	}

	public int getUserId() {
		return user_id;
	}

	public int getModuleId() {
		return module_id;
	}
}
