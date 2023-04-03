package ua.com.foxminded.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ua.com.foxminded.model.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
	
	@Query("select S from Student S where S.name = :name")
	List<Student> findByName(@Param("name") String name);
	
	@Query("select S from Student S where S.group.id = :id")
	List<Student> findByGroupRef(@Param("id") Long id);
	
	@Query("select S from Student S where S.login = :login")
	Optional<Student> findByLogin(@Param("login") String login);
	
}
