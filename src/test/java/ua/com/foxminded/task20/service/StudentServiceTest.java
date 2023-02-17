package ua.com.foxminded.task20.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import ua.com.foxminded.task20.DTO.UserDTO;
import ua.com.foxminded.task20.model.Group;
import ua.com.foxminded.task20.model.Student;
import ua.com.foxminded.task20.repository.StudentRepository;
import ua.com.foxminded.task20.security.enums.Role;
import ua.com.foxminded.task20.security.enums.Status;
import ua.com.foxminded.task20.service.impl.StudentServiceImpl;

@SpringBootTest(classes = { StudentServiceImpl.class })
public class StudentServiceTest {

	@MockBean
	StudentRepository repository;
	
	@MockBean
	PasswordEncoder passwordEncoder;

	@Autowired
	StudentServiceImpl service;

	@Test
	void shouldFindByName() {
		Student expected = new Student("name", "login", "password", Role.STUDENT, Status.ACTIVE, new Group(1L, "fk st"));
		Student expected2 = new Student("name", "login2", "password", Role.STUDENT, Status.ACTIVE, new Group(1L, "fk st"));
		when(repository.findByName(expected.getName())).thenReturn(List.of(expected, expected2));

		List<Student> actual = service.findByName(expected.getName());

		assertFalse(actual.isEmpty());
		assertTrue(actual.containsAll(List.of(expected, expected2)));
		
	}

	@Test
	void shouldFindById() {
		Student expected = new Student(1L, "name", "login", "password", Role.STUDENT, Status.ACTIVE, new Group(1L, "fk st"));
		when(repository.findById(expected.getId())).thenReturn(Optional.of(expected));

		Optional<Student> actual = service.findById(expected.getId());

		assertTrue(actual.isPresent());
		assertEquals(expected, actual.get());
	}
	
	@Test
	void shouldFindByGroupRef() {
		Student expected = new Student("name", "login", "password", Role.STUDENT, Status.ACTIVE, new Group(1L, "fk st"));
		Student expected2 = new Student("name", "login2", "password", Role.STUDENT, Status.ACTIVE, new Group(1L, "fk st"));
		when(repository.findByGroupRef(expected.getGroup().getId()))
			.thenReturn(List.of(expected, expected2));

		List<Student> actual = service.findByGroupRef(expected.getGroup().getId());

		assertFalse(actual.isEmpty());
		assertTrue(actual.containsAll(List.of(expected, expected2)));
	}

	@Test
	void shouldFindAll() {
		List<Student> expected = List.of(
				new Student("name", "login", "password", Role.STUDENT, Status.ACTIVE, new Group(1L, "fk st")), 
				new Student("name", "login2", "password", Role.STUDENT, Status.ACTIVE, new Group(1L, "fk st")));
		when(repository.findAll()).thenReturn(expected);

		List<Student> students = service.findAll();

		assertFalse(students.isEmpty());
		assertEquals(expected, students);

	}

	@Test
	void shouldDeleteById() {
		service.deleteById(12L);
		verify(repository).deleteById(12L);
	}

	@Test
	void shouldUpdate() {
		Student toUpdate = new Student(1L, "name", "login", "password", Role.STUDENT, Status.ACTIVE, new Group(1L, "fk st"));
		Student updated = new Student(1L, "updated", "updated", "updated", Role.STUDENT, Status.ACTIVE, new Group(2L, "updated"));
		
		when(repository.save(toUpdate)).thenReturn(updated);
		
		UserDTO DTO_ToUpdate = new UserDTO(
				toUpdate.getId(),
				toUpdate.getName(),
				toUpdate.getLogin(),
				toUpdate.getPassword(),
				toUpdate.getRole(),
				toUpdate.getGroup());

		UserDTO DTO_Updated = service.update(DTO_ToUpdate);

		assertSame(DTO_ToUpdate.getId(), DTO_Updated.getId());
		
		assertNotSame(DTO_ToUpdate.getName(), DTO_Updated.getName());
		assertNotSame(DTO_ToUpdate.getLogin(), DTO_Updated.getLogin());
		assertNotSame(DTO_ToUpdate.getPassword(), DTO_Updated.getPassword());
		assertNotSame(DTO_ToUpdate.getGroup(), DTO_Updated.getGroup());

	}

	@Test
	void shouldSave() {
		Student toSave = new Student("name", "login", "password", Role.STUDENT, Status.ACTIVE, new Group(1L, "fk st"));
		when(repository.save(toSave)).thenReturn(toSave);

		UserDTO DTOToSave = new UserDTO(
				toSave.getId(),
				toSave.getName(),
				toSave.getLogin(),
				toSave.getPassword(),
				toSave.getRole());

		UserDTO actual = service.save(DTOToSave);

		assertEquals(DTOToSave, actual);
	}

}
