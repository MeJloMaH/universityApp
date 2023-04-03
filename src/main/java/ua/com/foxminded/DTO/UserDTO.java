package ua.com.foxminded.DTO;

import javax.validation.constraints.NotBlank;

import ua.com.foxminded.security.enums.Role;
import ua.com.foxminded.model.Group;


public class UserDTO {

	private Long id;
	
	@NotBlank(message = "Name cannot be empty")
	private String name;
	
	@NotBlank(message = "Login cannot be empty")
	private String login;
	
	@NotBlank(message = "Password cannot be empty")
	private String password;
	
	private String confirmPassword;
	
	private String isStudent = "false";
	
	private Role role = Role.USER;
	
	private Group group;
		
	public UserDTO() {
	}

	public UserDTO(String name, String login, String password) {
		this.name = name;
		this.login = login;
		this.password = password;
	}
	
	public UserDTO(Long id, String name, String login, String password, Role role) {
		this.id = id;
		this.name = name;
		this.login = login;
		this.password = password;
		this.role = role;
	}

	public UserDTO(Long id, String name, String login, String password, 
			String confirmPassword, String isStudent, Role role) {
		this.id = id;
		this.name = name;
		this.login = login;
		this.password = password;
		this.confirmPassword = confirmPassword;
		this.isStudent = isStudent;
		this.role = role;
	}
	

	public UserDTO(Long id, String name, String login, String password, Role role, Group group) {
		this.id = id;
		this.name = name;
		this.login = login;
		this.password = password;
		this.role = role;
		this.group = group;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;		
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getIsStudent() {
		return isStudent;
	}

	public void setIsStudent(String isStudent) {
		this.isStudent = isStudent;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}	
		
}
