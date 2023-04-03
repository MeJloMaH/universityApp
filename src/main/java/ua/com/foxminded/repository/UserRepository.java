package ua.com.foxminded.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ua.com.foxminded.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	@Query("select U from User U where U.name = :name")
	List<User> findByName(@Param("name") String name);
	
	@Query("select U from User U where U.login = :login")
	Optional<User> findByLogin(@Param("login") String login);
	
	@Modifying
	@Query(value = "UPDATE users SET user_type = :user_type, role = :role WHERE id = :id", nativeQuery = true)
	void changeUserType(@Param("user_type") String userType, @Param("role") String role, @Param("id") Long id);	
	
}
