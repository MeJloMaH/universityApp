package ua.com.foxminded.model;

import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "subjects")
public class Subject extends LongEntity{
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "subject")
	private List<Lesson> lesson;
	
	@Column(name = "name")
	@NotBlank(message = "Name cannot be empty")
    private String name;

    public Subject(Long id, String name) {
        super(id);
        this.name = name;
    }

    public Subject(String name) {
        this(null, name);
    }
    
    public Subject() {
    	
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        Subject subject = (Subject) o;
        return Objects.equals(name, subject.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name);
    }
}
