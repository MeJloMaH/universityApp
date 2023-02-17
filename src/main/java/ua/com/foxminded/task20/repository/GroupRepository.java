package ua.com.foxminded.task20.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ua.com.foxminded.task20.model.Group;

public interface GroupRepository extends JpaRepository<Group, Long>{
	
	@Query("select G from Group G where G.name = :name")
	Optional<Group> findByName(@Param("name") String name);

}
