package ua.com.foxminded.service;

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

import ua.com.foxminded.repository.UserRepository;
import ua.com.foxminded.security.enums.Role;
import ua.com.foxminded.security.enums.Status;
import ua.com.foxminded.service.impl.UserServiceImpl;
import ua.com.foxminded.DTO.UserDTO;
import ua.com.foxminded.model.User;

@SpringBootTest(classes = { UserServiceImpl.class })
public class UserServiceTest {

	@MockBean
    UserRepository repository;
	
	@MockBean
	PasswordEncoder passwordEncoder;

	@Autowired
	UserServiceImpl service;
	
	@Test
	void shouldFindByName() {
		List<User> expected = List.of(
				new User("name", "login", "password", Role.USER, Status.ACTIVE));
		
		when(repository.findByName(expected.get(0).getName())).thenReturn(expected);

		List<User> actual = service.findByName(expected.get(0).getName());

		assertEquals(expected, actual);
	}

	@Test
	void shouldFindById() {
		User expected = new User(1L, "name", "login", "password", Role.USER, Status.ACTIVE);
		when(repository.findById(expected.getId())).thenReturn(Optional.of(expected));

		Optional<User> actual = service.findById(expected.getId());

		assertTrue(actual.isPresent());
		assertEquals(expected, actual.get());
	}

	@Test
	void shouldFindAll() {
		List<User> expected = List.of(
				new User("name1", "login1", "password", Role.USER, Status.ACTIVE),
				new User("name2", "login2", "password", Role.USER, Status.ACTIVE));
		when(repository.findAll()).thenReturn(expected);

		List<User> students = service.findAll();

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
		User toUpdate = new User(1L, "name", "login", "password", Role.USER, Status.ACTIVE);
		User updated = new User(1L, "updated", "updated", "updated", Role.ADMIN, Status.ACTIVE);

		when(repository.save(toUpdate)).thenReturn(updated);

		UserDTO DTO_ToUpdate = new UserDTO(
				toUpdate.getId(),
				toUpdate.getName(),
				toUpdate.getLogin(),
				toUpdate.getPassword(),
				toUpdate.getRole());

		UserDTO DTO_Updated = service.update(DTO_ToUpdate);

		assertSame(DTO_ToUpdate.getId(), DTO_Updated.getId());
		
		assertNotSame(DTO_ToUpdate.getName(), DTO_Updated.getName());
		assertNotSame(DTO_ToUpdate.getLogin(), DTO_Updated.getLogin());
		assertNotSame(DTO_ToUpdate.getPassword(), DTO_Updated.getPassword());
		assertNotSame(DTO_ToUpdate.getRole(), DTO_Updated.getRole());

	}

	@Test
	void shouldSave() {
		User toSave = new User("name", "login", "password", Role.USER, Status.ACTIVE);
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
