package ua.com.foxminded.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import ua.com.foxminded.security.enums.Role;
import ua.com.foxminded.security.enums.Status;

@Entity
@DiscriminatorValue("Teacher")
public class Teacher extends User {

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "teacher")
	private List<Lesson> lessons;

	public Teacher() {

	}

	public Teacher(Long id, String name, String login, String password, Role role, Status status) {
		super(id, name, login, password, role, status);
	}
	
	public Teacher(String name, String login, String password, Role role, Status status) {
		super(null, name, login, password, role, status);
	}

	public Teacher(Long id) {
		super(id);
	}

	public List<Lesson> getLesson() {
		return lessons;
	}

	public void setLesson(List<Lesson> lessons) {
		this.lessons = lessons;
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
		
		return true;
	}

	


}
