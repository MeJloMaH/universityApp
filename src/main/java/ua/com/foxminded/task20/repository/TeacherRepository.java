package ua.com.foxminded.task20.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ua.com.foxminded.task20.model.Teacher;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long>  {
	
	@Query("select T from Teacher T where T.name = :name")
	Optional<Teacher> findByName(@Param("name") String name);	
	
	@Query("select T from Teacher T where T.login = :login")
	Optional<Teacher> findByLogin(@Param("login") String login);
	
}
