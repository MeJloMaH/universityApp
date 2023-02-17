package ua.com.foxminded.task20.model;

import java.util.Objects;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import ua.com.foxminded.task20.security.enums.Role;
import ua.com.foxminded.task20.security.enums.Status;


@Entity
@DiscriminatorValue("Student")
public class Student extends User {

	@ManyToOne
	@JoinColumn(name = "group_ref")
	private Group group;

	public Student() {
	}

	public Student(Long id, String name, String login, String password, Role role, Status status, Group group) {
		super(id, name, login, password, role, status);
		this.group = group;
	}

	public Student(String name, String login, String password, Role role, Status status, Group group) {
		this(null, name, login, password, role, status, group);
	}
	

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Student other = (Student) obj;
		return Objects.equals(group.getId(), other.group.getId());
	}	
	
}
