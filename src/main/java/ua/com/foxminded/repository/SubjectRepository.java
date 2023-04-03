package ua.com.foxminded.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ua.com.foxminded.model.Subject;

public interface SubjectRepository extends JpaRepository<Subject, Long>{
	
	@Query("select S from Subject S where S.name = :name")
	Optional<Subject> findByName(@Param("name") String name);
}
