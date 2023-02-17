package ua.com.foxminded.task20.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import ua.com.foxminded.task20.BaseDaoTest;
import ua.com.foxminded.task20.model.Student;
import ua.com.foxminded.task20.model.User;
import ua.com.foxminded.task20.repository.StudentRepository;
import ua.com.foxminded.task20.repository.TeacherRepository;
import ua.com.foxminded.task20.repository.UserRepository;
import ua.com.foxminded.task20.security.enums.Role;
import ua.com.foxminded.task20.security.enums.Status;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest extends BaseDaoTest {

	@Autowired
	private UserRepository repository;
	
	@Autowired
	EntityManager entityManager;
	
	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private TeacherRepository teacherRepository;


	@Test
	@Sql({ "/sql/clear_tables.sql", "/sql/user/find_me_test.sql" })
	void shouldFindById() {
		User expected = new User(1L, "find me", "login", "password", Role.USER, Status.ACTIVE);
		User actual = repository.findById(expected.getId()).orElseThrow();
		assertEquals(actual, expected);
	}
	
	@Test
	@Sql({ "/sql/clear_tables.sql", "/sql/user/find_me_test.sql" })
	void shouldFindByName() {
		List<User> expected = List.of(
				new User(1L, "find me", "login", "password", Role.USER, Status.ACTIVE),
				new User(2L, "find me", "login2", "password", Role.USER, Status.ACTIVE));
		List<User> actual = repository.findByName(expected.get(0).getName());
		assertEquals(actual, expected);
	}

	@Test
	@Sql({ "/sql/clear_tables.sql" })
	void shouldNotFindById() {
		Optional<User> result = repository.findById(11000L);
		assertTrue(result.isEmpty());
	}

	@Test
	@Sql("/sql/clear_tables.sql")
	void shouldCreateOne() {
		User actual = repository.save(new User(1L, "test", "login", "password", Role.USER, Status.ACTIVE));

		assertNotNull(actual.getId());
		assertEquals("test", actual.getName());

		User fromDB = repository.findById(actual.getId()).orElseThrow();
		assertEquals(actual, fromDB);
	}

	@Test
	@Sql({ "/sql/clear_tables.sql", "/sql/user/update_test.sql" })
	void shouldUpdateOne() {
		String nameBefore = "change me!";
		String loginBefore = "change me!";
		String passwordBefore = "change me!";
		
		String nameAfter = "changed!";
		String loginAfter = "changed!";
		String passwordAfter = "changed!";

		User toUpdate = new User(1L, nameAfter, loginAfter, passwordAfter, Role.USER, Status.ACTIVE);
		
		User before = repository.findById(toUpdate.getId()).orElseThrow();
		
		assertEquals(nameBefore, before.getName());
		assertEquals(loginBefore, before.getLogin());
		assertEquals(passwordBefore, before.getPassword());

		repository.saveAndFlush(toUpdate);
		User after = repository.findById(toUpdate.getId()).orElseThrow();
		assertEquals(nameAfter, after.getName());
		assertEquals(loginAfter, after.getLogin());
		assertEquals(passwordAfter, after.getPassword());
	}

	@Test
	@Sql({ "/sql/clear_tables.sql", "/sql/user/delete_test.sql" })
	void shouldRemoveById() {

		Long exist = 15000L;
		Optional<User> toRemove = repository.findById(exist);
		assertTrue(toRemove.isPresent());

		repository.deleteById(toRemove.get().getId());
		Optional<User> afterRemove = repository.findById(exist);
		assertTrue(afterRemove.isEmpty());
	}
	
	@Test
	@Sql({ "/sql/clear_tables.sql", "/sql/user/change_user_type.sql" })
	void shouldChangeUserType() {
		
		long id = 1L;
		
		Role actualRole = Role.STUDENT;

		Student st = studentRepository.findById(id).get();
				
		assertEquals(actualRole, st.getRole());		
		
		repository.changeUserType("User", Role.ADMIN.name(), id);
		entityManager.flush();
		entityManager.clear();		
		
		Role expected = Role.ADMIN;
		
		User us = repository.findById(id).get();
		
		assertEquals(expected, us.getRole());
		
	}
}
