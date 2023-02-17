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
import ua.com.foxminded.task20.model.Subject;
import ua.com.foxminded.task20.repository.SubjectRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class SubjectRepositoryTest extends BaseDaoTest {

	@Autowired
	private SubjectRepository repository;


	@Test
	@Sql({ "/sql/clear_tables.sql", "/sql/subject/find_by_id_test.sql" })
	void shouldFindById() {
		Subject expected = new Subject(11000L, "find me!");
		Subject actual = repository.findById(expected.getId()).orElseThrow();
		assertEquals(actual, expected);
	}

	@Test
	@Sql({ "/sql/clear_tables.sql", "/sql/subject/find_by_id_test.sql" })
	void shouldFindByName() {
		Subject expected = new Subject(11000L, "find me!");
		Subject actual = repository.findByName(expected.getName()).orElseThrow();
		assertEquals(actual, expected);
	}

	@Test
	@Sql({ "/sql/clear_tables.sql" })
	void shouldNotFindById() {
		Optional<Subject> result = repository.findById(11000L);
		assertTrue(result.isEmpty());
	}

	@Test
	@Sql("/sql/clear_tables.sql")
	void shouldCreateOne() {
		Subject actual = repository.save(new Subject("test"));

		assertNotNull(actual.getId());
		assertEquals("test", actual.getName());

		Subject fromDB = repository.findById(actual.getId()).orElseThrow();
		assertEquals(actual, fromDB);
	}

	@Test
	@Sql({ "/sql/clear_tables.sql", "/sql/subject/update_test.sql" })
	void shouldUpdateOne() {
		String nameBefore = "change me!";
		String nameAfter = "changed!";

		Subject toUpdate = new Subject(10000L, nameAfter);
		Subject before = repository.findById(toUpdate.getId()).orElseThrow();
		assertEquals(nameBefore, before.getName());

		repository.save(toUpdate);
		Subject after = repository.findById(toUpdate.getId()).orElseThrow();
		assertEquals(nameAfter, after.getName());
	}

	@Test
	@Sql({ "/sql/clear_tables.sql", "/sql/subject/delete_test.sql" })
	void shouldRemoveById() {

		Long exist = 15000L;
		Optional<Subject> toRemove = repository.findById(exist);
		assertTrue(toRemove.isPresent());

		repository.deleteById(toRemove.get().getId());
	
		Optional<Subject> afterRemove = repository.findById(exist);
		assertTrue(afterRemove.isEmpty());

	}

}
