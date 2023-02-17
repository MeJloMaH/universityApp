package ua.com.foxminded.task20.security.enums;

public enum Permission {
	READ("read"),
	WRITE("write"),
	DELETE("delete"),
	ACCESS_TO_USERS("access_to_users"),
	ACCESS_TO_TEACHERS("access_to_teachers");

	private final String value;

	Permission(String permission) {
		this.value = permission;
	}
	
	public String getPermission() {
		return value;
	}
}
