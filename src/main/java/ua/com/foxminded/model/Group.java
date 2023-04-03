package ua.com.foxminded.model;

import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "groups")
public class Group extends LongEntity{
	
	@Column(name = "name", nullable = false, unique = true)	
	@NotBlank(message = "Name cannot be empty")
	private String name;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "group", fetch = FetchType.EAGER)
	private List<Lesson> lessons;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "group")
	private List<Student> students;
	
	public Group() {	
	}
	
	public Group(String name) {
		this(null, name);
	}
	
	public Group(Long id, String name) {
		super(id);
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public List<Lesson> getLesson() {
		return lessons;
	}

	public void setLesson(List<Lesson> lessons) {
		this.lessons = lessons;
	}

	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(name);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Group other = (Group) obj;
		return Objects.equals(name, other.name);
	}

	
	
}
