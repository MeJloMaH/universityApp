package ua.com.foxminded.service.impl;

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

import ua.com.foxminded.security.enums.Role;
import ua.com.foxminded.security.enums.Status;
import ua.com.foxminded.DTO.UserDTO;
import ua.com.foxminded.model.Student;
import ua.com.foxminded.repository.StudentRepository;
import ua.com.foxminded.service.StudentService;

@Service
public class StudentServiceImpl implements StudentService {

	
	private final StudentRepository repository;
	private final PasswordEncoder passwordEncoder;

	Logger log = LoggerFactory.getLogger(StudentServiceImpl.class);

	public StudentServiceImpl(StudentRepository repository, PasswordEncoder passwordEncoder) {
		this.repository = repository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Student> findById(Long id) {
		log.debug("Trying to find Student by id = {}", id);
		try {
			Optional<Student> entity = repository.findById(id);

			if (entity.isPresent()) {
				log.info("Success, Student by id = {} was found", id);
				return entity;
			}

			return entity;

		} catch (EmptyResultDataAccessException e) {
			log.error("Unable find Student by id = {} due: {}", id, e.getMessage(), e);
			return Optional.empty();
		}
	}
	
	@Override
	@Transactional(readOnly = true)
	public Optional<Student> findByLogin(String login) {
		log.debug("Trying to find Student by login = {}", login);
		try {
			Optional<Student> entity = repository.findByLogin(login);

			if (entity.isPresent()) {
				log.info("Success, Student by login = {} was found", login);
				return entity;
			}

			return entity;

		} catch (EmptyResultDataAccessException e) {
			log.error("Unable find Student by login = {} due: {}", login, e.getMessage(), e);
			return Optional.empty();
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<Student> findByGroupRef(Long id) {
		log.debug("Trying to find Students by groupRef = {}", id);
		List<Student> student;
		try {
			student = repository.findByGroupRef(id);

			log.info("Success, Students by groupRef = {} was found", id);
			return student;

		} catch (EmptyResultDataAccessException e) {
			log.error("Unable find Students by groupRef = {} due: {} ", id, e.getMessage(), e);
			return new ArrayList<>();
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<Student> findByName(String name) {
		log.debug("Trying to find Student by name = {}", name);
		try {
			List<Student> entity = repository.findByName(name);

			log.info("Success, Student by name = {} was found", name);
			return entity;

		} catch (EmptyResultDataAccessException e) {
			log.error("Unable find Student by name = {} due: {} ", name, e.getMessage(), e);
			return new ArrayList<>();
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<Student> findAll() {
		log.debug("Trying to find all Students");
		List<Student> list;
		try {
			list = repository.findAll();

			if(!list.isEmpty()) {log.info("Success, all Students was found");}
			
			return list;

		} catch (Exception e) {
			log.error("Unable find all Students due: {} ", e.getMessage(), e);
			return new ArrayList<>();
		}
	}

	@Override
	@Transactional
	@PreAuthorize("hasAuthority('delete')")
	public void deleteById(Long id) {
		log.debug("Trying to delete Student by id = {}", id);

		try {
			repository.deleteById(id);
			log.info("Success, Student by id = {} was deleted", id);

		} catch (Exception e) {
			log.error("Unable delete Student by id = {} due: {}", id, e.getMessage(), e);
		}
	}

	@Override
	@Transactional
    @PreAuthorize("hasAuthority('write')")
	public UserDTO save(UserDTO entity) {
		log.debug("Trying to save Student by name = {} ", entity.getName());

		try {
			Student student = new Student();
			student.setName(entity.getName());
			student.setLogin(entity.getLogin());
			student.setPassword(passwordEncoder.encode(entity.getPassword()));
			student.setRole(Role.STUDENT);
			student.setStatus(Status.ACTIVE);
			student.setGroup(entity.getGroup());
			Student newStudent = repository.save(student);
			
			log.info("Success, Student by name = {} was created", entity.getName());
			return new UserDTO(
					newStudent.getId(),
					newStudent.getName(),
					newStudent.getLogin(),
					newStudent.getPassword(),
					newStudent.getRole(),
					newStudent.getGroup());

		} catch (Exception e) {
			log.error("Unable create Student by name = {} due: {} ", entity.getName(), e.getMessage(), e);
			return entity;
		}
	}
	
	@Override
	@Transactional
	public UserDTO register(UserDTO entity) {
		log.debug("Trying to register Student by name = {} ", entity.getName());

		try {
			Student student = new Student();
			student.setName(entity.getName());
			student.setLogin(entity.getLogin());
			student.setPassword(passwordEncoder.encode(entity.getPassword()));
			student.setRole(Role.STUDENT);
			student.setStatus(Status.ACTIVE);
			Student newStudent = repository.save(student);
			
			log.info("Success, Student by name = {} was registered", entity.getName());
			return new UserDTO(
					newStudent.getId(),
					newStudent.getName(),
					newStudent.getLogin(),
					newStudent.getPassword(),
					newStudent.getRole(),
					newStudent.getGroup());

		} catch (Exception e) {
			log.error("Unable register Student by name = {} due: {} ", entity.getName(), e.getMessage(), e);
			return entity;
		}
	}
	
	@Override
	@Transactional
	@PreAuthorize("#entity.login == authentication.principal.username or hasAuthority('access_to_users')")
	public UserDTO update(UserDTO entity) {
		log.debug("Trying to update Student by name = {} ", entity.getName());

		try {
			Student student = new Student();
			student.setId(entity.getId());
			student.setName(entity.getName());
			student.setLogin(entity.getLogin());
			student.setPassword(entity.getPassword());
			student.setRole(Role.STUDENT);
			student.setStatus(Status.ACTIVE);
			student.setGroup(entity.getGroup());
			
			Student newStudent = repository.save(student);
			
			
			log.info("Success, Student by name = {} was updated", entity.getName());
			return new UserDTO(
					newStudent.getId(),
					newStudent.getName(),
					newStudent.getLogin(),
					newStudent.getPassword(),
					newStudent.getRole(),
					newStudent.getGroup());

		} catch (Exception e) {
			log.error("Unable update Student by name = {} due: {} ", entity.getName(), e.getMessage(), e);
			return entity;
		}
	}

}
