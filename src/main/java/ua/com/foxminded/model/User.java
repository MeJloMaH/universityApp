package ua.com.foxminded.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import ua.com.foxminded.security.enums.Role;
import ua.com.foxminded.security.enums.Status;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING)
public class User {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
		
	@Column(name = "name")
	@NotBlank(message = "Name cannot be empty")
	protected String name;
	
	@Column(name = "login", unique = true)
	@NotBlank(message = "Login cannot be empty")
	protected String login;
	
	@Column(name = "password")
	@NotBlank(message = "Password cannot be empty")
	protected String password;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "role")
	protected Role role;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "status")
	protected Status status;
	
	
	
	public User(Long id) {
		this.id = id;
	}
	
	public User() {
	}

	public User(Long id, String name, String login, String password, Role role, Status status) {
		this.id = id;
		this.name = name;
		this.login = login;
		this.password = password;
		this.role = role;
		this.status = status;
	}
	
	public User(String name, String login, String password, Role role, Status status) {
		this(null, name, login, password, role, status);
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

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, login, name, password, role, status);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(id, other.id) && Objects.equals(login, other.login) && Objects.equals(name, other.name)
				&& Objects.equals(password, other.password) && role == other.role && status == other.status;
	}


	
}
