package ua.com.foxminded.task20.service;

import java.util.List;
import java.util.Optional;

import ua.com.foxminded.task20.model.Group;

public interface GroupService {

	Optional<Group> findById(Long id);

	Optional<Group> findByName(String name);

	List<Group> findAll();

	Group save(Group entity);

	void deleteById(Long id);

}
