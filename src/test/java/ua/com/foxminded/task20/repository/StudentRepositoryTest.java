package ua.com.foxminded.task20.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import ua.com.foxminded.task20.BaseDaoTest;
import ua.com.foxminded.task20.model.Group;
import ua.com.foxminded.task20.model.Student;
import ua.com.foxminded.task20.repository.StudentRepository;
import ua.com.foxminded.task20.security.enums.Role;
import ua.com.foxminded.task20.security.enums.Status;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class StudentRepositoryTest extends BaseDaoTest {

	@Autowired
	private StudentRepository repository;


	@Test
	@Sql({ "/sql/clear_tables.sql", "/sql/student/find_me_test.sql" })
	void shouldFindById() {
		Student expected = new Student(1L, "find me", "login", "password", Role.STUDENT, Status.ACTIVE,
				new Group(11000L, "fk student"));
		Student actual = repository.findById(expected.getId()).orElseThrow();
		assertEquals(actual, expected);
	}

	@Test
	@Sql({ "/sql/clear_tables.sql", "/sql/student/find_me_test.sql" })
	void shouldFindByGroupRef() {		
		
		List<Student> expected = 
				List.of(new Student(1L, "find me", "login", "password", Role.STUDENT, Status.ACTIVE,
					new Group(11000L, "fk student")),
				new Student(2L, "find me", "login2", "password", Role.STUDENT, Status.ACTIVE,
						new Group(11000L, "fk student")));
		
		List<Student> actual = repository.findByGroupRef(expected.get(0).getGroup().getId());
		
		assertEquals(actual, expected);
	}
	
	@Test
	@Sql({ "/sql/clear_tables.sql", "/sql/student/find_me_test.sql" })
	void shouldFindByName() {
		
		List<Student> expected = 
				List.of(new Student(1L, "find me", "login", "password", Role.STUDENT, Status.ACTIVE,
					new Group(11000L, "fk student")),
				new Student(2L, "find me", "login2", "password", Role.STUDENT, Status.ACTIVE,
						new Group(11000L, "fk student")));
		
		List<Student> actual = repository.findByName(expected.get(0).getName());
		
		assertEquals(actual, expected);
	}

	@Test
	@Sql({ "/sql/clear_tables.sql", "/sql/student/find_all_test.sql" })
	void shouldFindAll() {
		List<Student> expected = List.of(
				
				new Student(1L, "find me", "login", "password", Role.STUDENT, Status.ACTIVE,
					new Group(1L, "fk student 1")),
				
				new Student(2L, "find me", "login2", "password", Role.STUDENT, Status.ACTIVE,
					new Group(2L, "fk student 2")),
				
				new Student(3L, "find me3", "login3", "password", Role.STUDENT, Status.ACTIVE,
					new Group(3L, "fk student 3")));
		
		List<Student> actual = repository.findAll();
		assertEquals(actual, expected);
	}

	@Test
	@Sql({ "/sql/clear_tables.sql" })
	void shouldNotFindById() {
		Optional<Student> result = repository.findById(11000L);
		assertTrue(result.isEmpty());
	}

	@Test
	@Sql({ "/sql/clear_tables.sql", "/sql/student/create_test.sql" })
	void shouldCreateOne() {
		Student actual = repository.save(
				new Student(1L, "name", "login", "password", Role.STUDENT, Status.ACTIVE,
						new Group(222L, "fk student")));

		assertNotNull(actual.getId());
		assertEquals("name", actual.getName());
		assertEquals(222L, actual.getGroup().getId());

		Student fromDB = repository.findById(actual.getId()).orElseThrow();
		assertEquals(actual, fromDB);
	}

	@Test
	@Sql({ "/sql/clear_tables.sql", "/sql/student/update_test.sql" })
	void shouldUpdateOne() {
		String nameBefore = "change me!";
		String loginBefore = "change me!";
		String passwordBefore = "change me!";
		Group groupBefore = new Group(10000L, "fk student before");	
		
		String nameAfter = "changed!";
		String loginAfter = "changed!";
		String passwordAfter = "changed!";
		Group groupAfter = new Group(777L, "fk student after");
		
		Student toUpdate = new Student(1L, nameAfter, loginAfter, passwordAfter, Role.STUDENT, Status.ACTIVE,
				groupAfter);
				
		Student before = repository.findById(toUpdate.getId()).orElseThrow();
		
		assertEquals(nameBefore, before.getName());
		assertEquals(groupBefore, before.getGroup());
		assertEquals(loginBefore, before.getLogin());
		assertEquals(passwordBefore, before.getPassword());

		repository.saveAndFlush(toUpdate);
		Student after = repository.findById(toUpdate.getId()).orElseThrow();
		assertEquals(nameAfter, after.getName());
		assertEquals(loginAfter, after.getLogin());
		assertEquals(passwordAfter, after.getPassword());
		assertEquals(groupAfter, after.getGroup());
	}

	@Test
	@Sql({ "/sql/clear_tables.sql", "/sql/student/delete_test.sql" })
	void shouldRemoveById() {

		Long exist = 15000L;
		Optional<Student> toRemove = repository.findById(exist);
		assertTrue(toRemove.isPresent());

		repository.deleteById(toRemove.get().getId());
		
		Optional<Student> afterRemove = repository.findById(exist);
		assertTrue(afterRemove.isEmpty());

	}

}
