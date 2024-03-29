package ua.com.foxminded.service;

import java.util.List;
import java.util.Optional;

import ua.com.foxminded.DTO.UserDTO;
import ua.com.foxminded.model.Teacher;

public interface TeacherService {

	Optional<Teacher> findById(Long id);

	Optional<Teacher> findByName(String name);
	
	Optional<Teacher> findByLogin(String name);

	List<Teacher> findAll();

	void deleteById(Long id);

	UserDTO save(UserDTO entity);

	UserDTO update(UserDTO entity);
	

}
