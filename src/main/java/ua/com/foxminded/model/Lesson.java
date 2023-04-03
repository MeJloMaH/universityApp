package ua.com.foxminded.model;

import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "lessons", uniqueConstraints = 
@UniqueConstraint(columnNames={"date", "number_per_day", "group_ref"}))
public class Lesson extends LongEntity{

	
	@Column(name = "name", nullable = false)
	@NotBlank(message = "Name cannot be empty")
	private String name;
	
	@Column(name = "date", nullable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull(message = "Date cannot be empty")
	private LocalDate date;
	
	@Column(name = "number_per_day", nullable = false)
	@NotNull(message = "NumberPerDay cannot be empty")
	private int numberPerDay;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "group_ref", nullable = false)
	@NotNull(message = "Group cannot be empty")
	private Group group;
	
	@ManyToOne
	@JoinColumn(name = "teacher_ref", nullable = false)
	@NotNull(message = "Teacher cannot be empty")
	private Teacher teacher;
	
	@ManyToOne
	@JoinColumn(name = "subject_ref", nullable = false)
	@NotNull(message = "Subject cannot be empty")
	private Subject subject;
	
	@ManyToOne
	@JoinColumn(name = "room_ref", nullable = false)
	@NotNull(message = "Room cannot be empty")
	private Room room;
	
	
	public Lesson(Long id, String name, Group group, int numberPerDay, 
			LocalDate date, Teacher teacher, Subject subject, Room room) {
		super(id);
		this.name = name;
		this.group = group;
		this.numberPerDay = numberPerDay;
		this.date = date;
		this.teacher = teacher;
		this.subject = subject;
		this.room = room;		
	}
	
	public Lesson(String name, Group group, int numberPerDay, 
			LocalDate date, Teacher teacher , Subject subject, Room room) {
		this(null, name, group, numberPerDay, date, teacher, subject, room);
	}
	
	public Lesson() {
		
	}

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}
	
	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}
	
	public int getNumberPerDay() {
		return numberPerDay;
	}

	public void setNumberPerDay(int numberPerDay) {
		this.numberPerDay = numberPerDay;
	}
	
	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}
	

	public Teacher getTeacher() {
		return teacher;
	}


	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}


	public Subject getSubject() {
		return subject;
	}


	public void setSubject(Subject subject) {
		this.subject = subject;
	}


	public Room getRoom() {
		return room;
	}


	public void setRoom(Room room) {
		this.room = room;
	}


	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects
				.hash(date, name, numberPerDay, room, subject);
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
		Lesson other = (Lesson) obj;
		return Objects.equals(date, other.date) && group.getId() == other.group.getId()
				&& Objects.equals(name, other.name) && numberPerDay == other.numberPerDay
				&& Objects.equals(room, other.room) && Objects.equals(subject, other.subject)
				&& teacher.getId() == other.teacher.getId();
	}


}
