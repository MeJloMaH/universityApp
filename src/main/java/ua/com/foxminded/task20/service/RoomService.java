package ua.com.foxminded.task20.service;

import java.util.List;
import java.util.Optional;

import ua.com.foxminded.task20.model.Room;

public interface RoomService {

	Optional<Room> findById(Long id);

	Optional<Room> findByName(String name);

	List<Room> findAll();

	Room save(Room entity);

	void deleteById(Long id);

	Optional<Room> findByLocation(String location);

}
