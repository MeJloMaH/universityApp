package ua.com.foxminded.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ua.com.foxminded.model.Lesson;

public interface LessonRepository extends JpaRepository<Lesson, Long>{
	
	@Query("select L from Lesson L where L.id = :id")
	Optional<Lesson> findById(@Param("id") Long id);
	
	@Query("select L from Lesson L where L.name = :name")
	List<Lesson> findByName(@Param("name") String name);
	
	@Query("select L from Lesson L where L.teacher.id = :id")
	List<Lesson> findByTeacherRef(@Param("id") Long id);
	
	@Query("select L from Lesson L where L.subject.id = :id")
	List<Lesson> findBySubjectRef(@Param("id") Long id);
	
	@Query("select L from Lesson L where L.room.id = :id")
	List<Lesson> findByRoomRef(@Param("id") Long id);
	
	@Query("select L from Lesson L where L.date = :date and L.numberPerDay = :numberPerDay")
	List<Lesson> findByDateAndNumberPerDay(@Param("date") LocalDate date, @Param("numberPerDay") Integer numberPerDay);
	
	@Query("select L from Lesson L where L.group.id = :id")
	List<Lesson> findByGroupRef (@Param("id") Long id);
	
	@Query("select L from Lesson L where L.date = :date and L.group.id = :id")
	List<Lesson> findByDateAndGroupRef (@Param("date") LocalDate date, @Param("id") Long id);
	
	@Query("select L from Lesson L where L.date = :date")
	List<Lesson> findByDate (@Param("date") LocalDate date);
	
	@Modifying
	@Query("delete from Lesson where id = :id")
	void deleteById(@Param("id") Long id);
	
}
