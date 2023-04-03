package ua.com.foxminded.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import ua.com.foxminded.model.Lesson;

public interface LessonService {

	Optional<Lesson> findById(Long id);

	List<Lesson> findByName(String name);

	List<Lesson> findAll();

	Lesson save(Lesson entity);

	void deleteById(Long id);

	List<Lesson> findByTeacherRef(Long id);

	List<Lesson> findBySubjectRef(Long id);

	List<Lesson> findByRoomRef(Long id);
	
	List<Lesson> findByGroupRef(long groupRef);
	
	List<Lesson> findByDateAndGroupRef (LocalDate date, long groupRef);
	
	List<Lesson> findByDateAndNumberPerDay (LocalDate date, Integer numberPerDay);
	
	List<Lesson> findByDate (LocalDate date);

}
