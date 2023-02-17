package ua.com.foxminded.task20.service;

import java.util.List;
import java.util.Optional;

import ua.com.foxminded.task20.DTO.UserDTO;
import ua.com.foxminded.task20.model.Student;

public interface StudentService {

	Optional<Student> findById(Long id);

	List<Student> findByName(String name);
	
	Optional<Student> findByLogin(String name);

	List<Student> findAll();

	UserDTO save(UserDTO entity);

	void deleteById(Long id);

	List<Student> findByGroupRef(Long id);

	UserDTO update(UserDTO entity);

	UserDTO register(UserDTO entity);

}
