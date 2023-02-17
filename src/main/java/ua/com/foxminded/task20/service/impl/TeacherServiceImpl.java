package ua.com.foxminded.task20.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.com.foxminded.task20.DTO.UserDTO;
import ua.com.foxminded.task20.model.Teacher;
import ua.com.foxminded.task20.repository.TeacherRepository;
import ua.com.foxminded.task20.security.enums.Role;
import ua.com.foxminded.task20.security.enums.Status;
import ua.com.foxminded.task20.service.TeacherService;

@Service
public class TeacherServiceImpl implements TeacherService {

	private final TeacherRepository repository;
	private final PasswordEncoder passwordEncoder;

	Logger log = LoggerFactory.getLogger(TeacherServiceImpl.class);

	public TeacherServiceImpl(TeacherRepository repository, PasswordEncoder passwordEncoder) {
		this.repository = repository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Teacher> findById(Long id) {
		log.debug("Trying to find Teacher by id = {}", id);
		try {
			Optional<Teacher> entity = repository.findById(id);

			if (entity.isPresent()) {
				log.info("Success, Teacher by id = {} was found", id);
				return entity;
			}

			return entity;

		} catch (EmptyResultDataAccessException e) {
			log.error("Unable find Teacher by id = {} due: {}", id, e.getMessage(), e);
			return Optional.empty();
		}
	}
	
	@Override
	@Transactional(readOnly = true)
	public Optional<Teacher> findByLogin(String login) {
		log.debug("Trying to find Teacher by login = {}", login);
		try {
			Optional<Teacher> entity = repository.findByLogin(login);

			if (entity.isPresent()) {
				log.info("Success, Teacher by login = {} was found", login);
				return entity;
			}

			return entity;

		} catch (EmptyResultDataAccessException e) {
			log.error("Unable find Teacher by login = {} due: {}", login, e.getMessage(), e);
			return Optional.empty();
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Teacher> findByName(String name) {
		log.debug("Trying to find Teacher by name = {}", name);
		try {
			Optional<Teacher> entity = repository.findByName(name);

			if (entity.isPresent()) {
				log.info("Success, Teacher by name = {} was found", name);
				return entity;
			}

			return entity;

		} catch (EmptyResultDataAccessException e) {
			log.error("Unable find Teacher by name = {} due: {} ", name, e.getMessage(), e);
			return Optional.empty();
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<Teacher> findAll() {
		log.debug("Trying to find all Teachers");
		List<Teacher> list;
		try {
			list = repository.findAll();

			log.info("Success, all Teachers was found");
			return list;

		} catch (Exception e) {
			log.error("Unable find all Teachers due: {} ", e.getMessage(), e);
			return new ArrayList<>();
		}
	}

	@Override
	@Transactional
	@PreAuthorize("hasAuthority('delete')")
	public void deleteById(Long id) {
		log.debug("Trying to delete Teacher by id = {}", id);

		try {
			repository.deleteById(id);
			log.info("Success, Teacher by id = {} was deleted", id);

		} catch (Exception e) {
			log.error("Unable delete Teacher by id = {} due: {}", id, e.getMessage(), e);
		}
	}

	@Override
	@Transactional
	@PreAuthorize("hasAuthority('access_to_teachers')")
	public UserDTO save(UserDTO entity) {
		log.debug("Trying to save Teacher by name = {} ", entity.getName());

		try {			
			Teacher teacher = new Teacher();
			teacher.setName(entity.getName());
			teacher.setLogin(entity.getLogin());		
			teacher.setPassword(passwordEncoder.encode(entity.getPassword()));
			teacher.setRole(Role.TEACHER);
			teacher.setStatus(Status.ACTIVE);
			Teacher newTeacher = repository.save(teacher);			
			
			log.info("Success, Teacher by name = {} was created", entity.getName());
			return new UserDTO(
					newTeacher.getId(),
					newTeacher.getName(),
					newTeacher.getLogin(),
					newTeacher.getPassword(),
					newTeacher.getRole());

		} catch (Exception e) {
			log.error("Unable create Teacher by name = {} due: {} ", entity.getName(), e.getMessage(), e);
			return entity;
		}
	}
	
	@Override
	@Transactional
	@PreAuthorize("#entity.login == authentication.principal.username or hasAuthority('access_to_teachers')")
	public UserDTO update(UserDTO entity) {
		log.debug("Trying to update Teacher by name = {} ", entity.getName());

		try {			
			Teacher teacher = new Teacher();
			teacher.setId(entity.getId());
			teacher.setName(entity.getName());
			teacher.setLogin(entity.getLogin());		
			teacher.setPassword(entity.getPassword());
			teacher.setRole(Role.TEACHER);
			teacher.setStatus(Status.ACTIVE);
			
			Teacher newTeacher = repository.save(teacher);				
			
			log.info("Success, Teacher by name = {} was updated", entity.getName());
			return new UserDTO(
					newTeacher.getId(),
					newTeacher.getName(),
					newTeacher.getLogin(),
					newTeacher.getPassword(),
					newTeacher.getRole());

		} catch (Exception e) {
			log.error("Unable update Teacher by name = {} due: {} ", entity.getName(), e.getMessage(), e);
			return entity;
		}
	}

}
