package ua.com.foxminded.task20.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetailsService;

import ua.com.foxminded.task20.DTO.UserDTO;
import ua.com.foxminded.task20.model.User;

public interface UserService extends UserDetailsService {

	Optional<User> findById(Long id);

	List<User> findByName(String name);
	
	Optional<User> findByLogin(String name);

	List<User> findAll();

	UserDTO save(UserDTO entity);
	
	UserDTO update(UserDTO entity);

	void deleteById(Long id);

	UserDTO register(UserDTO entity);

	UserDTO changeUserType(UserDTO entity);

	void doWithSystemUser(Runnable action);
}
