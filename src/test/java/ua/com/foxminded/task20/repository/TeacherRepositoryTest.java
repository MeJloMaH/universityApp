package ua.com.foxminded.task20.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import ua.com.foxminded.task20.BaseDaoTest;
import ua.com.foxminded.task20.model.Teacher;
import ua.com.foxminded.task20.repository.TeacherRepository;
import ua.com.foxminded.task20.security.enums.Role;
import ua.com.foxminded.task20.security.enums.Status;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TeacherRepositoryTest extends BaseDaoTest {

	@Autowired
	private TeacherRepository repository;


	@Test
	@Sql({ "/sql/clear_tables.sql", "/sql/teacher/find_me_test.sql" })
	void shouldFindById() {
		Teacher expected = new Teacher(1L, "find me", "login", "password", Role.TEACHER, Status.ACTIVE);
		Teacher actual = repository.findById(expected.getId()).orElseThrow();
		assertEquals(actual, expected);
	}
	
	@Test
	@Sql({ "/sql/clear_tables.sql", "/sql/teacher/find_me_test.sql" })
	void shouldFindByName() {
		Teacher expected = new Teacher(1L, "find me", "login", "password", Role.TEACHER, Status.ACTIVE);
		Teacher actual = repository.findByName(expected.getName()).orElseThrow();
		assertEquals(actual, expected);
	}

	@Test
	@Sql({ "/sql/clear_tables.sql" })
	void shouldNotFindById() {
		Optional<Teacher> result = repository.findById(11000L);
		assertTrue(result.isEmpty());
	}

	@Test
	@Sql("/sql/clear_tables.sql")
	void shouldCreateOne() {
		Teacher actual = repository.save(new Teacher(1L, "test", "login", "password", Role.STUDENT, Status.ACTIVE));

		assertNotNull(actual.getId());
		assertEquals("test", actual.getName());

		Teacher fromDB = repository.findById(actual.getId()).orElseThrow();
		assertEquals(actual, fromDB);
	}

	@Test
	@Sql({ "/sql/clear_tables.sql", "/sql/teacher/update_test.sql" })
	void shouldUpdateOne() {
		String nameBefore = "change me!";
		String loginBefore = "change me!";
		String passwordBefore = "change me!";
		
		String nameAfter = "changed!";
		String loginAfter = "changed!";
		String passwordAfter = "changed!";

		Teacher toUpdate = new Teacher(1L, nameAfter, loginAfter, passwordAfter, Role.TEACHER, Status.ACTIVE);
		
		Teacher before = repository.findById(toUpdate.getId()).orElseThrow();
		
		assertEquals(nameBefore, before.getName());
		assertEquals(loginBefore, before.getLogin());
		assertEquals(passwordBefore, before.getPassword());

		repository.saveAndFlush(toUpdate);
		Teacher after = repository.findById(toUpdate.getId()).orElseThrow();
		assertEquals(nameAfter, after.getName());
		assertEquals(loginAfter, after.getLogin());
		assertEquals(passwordAfter, after.getPassword());
	}

	@Test
	@Sql({ "/sql/clear_tables.sql", "/sql/teacher/delete_test.sql" })
	void shouldRemoveById() {

		Long exist = 15000L;
		Optional<Teacher> toRemove = repository.findById(exist);
		assertTrue(toRemove.isPresent());

		repository.deleteById(toRemove.get().getId());
		Optional<Teacher> afterRemove = repository.findById(exist);
		assertTrue(afterRemove.isEmpty());
	}
}
