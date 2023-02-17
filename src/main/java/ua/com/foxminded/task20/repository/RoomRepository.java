package ua.com.foxminded.task20.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ua.com.foxminded.task20.model.Room;

public interface RoomRepository extends JpaRepository<Room, Long> {
	
	@Query("select R from Room R where R.name = :name")
	Optional<Room> findByName(@Param("name") String name);
	
	@Query("select R from Room R where R.location = :location")
	Optional<Room> findByLocation(@Param("location") String location);
}
