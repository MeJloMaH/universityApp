package ua.com.foxminded.task20.model;

import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "rooms")
public class Room extends LongEntity {
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "room")
	private List<Lesson> lesson;
	
	@Column(name = "name")
	@NotBlank(message = "Name cannot be empty")
	private String name;
	
	@Column(name = "location", nullable = false, unique = true)
	@NotBlank(message = "Location cannot be empty")
	private String location;

	public Room(Long id, String name, String location) {
		super(id);
		this.name = name;
		this.location = location;		
	}

	public Room(String name, String location) {
		this(null, name, location);
	}
	
	public Room() {
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public List<Lesson> getLesson() {
		return lesson;
	}

	public void setLesson(List<Lesson> lesson) {
		this.lesson = lesson;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		if (!super.equals(o))
			return false;
		Room room = (Room) o;
		return Objects.equals(name, room.name) && Objects.equals(location, room.location);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), name, location);
	}
}
