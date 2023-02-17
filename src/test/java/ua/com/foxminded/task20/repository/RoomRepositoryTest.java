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
import ua.com.foxminded.task20.model.Room;
import ua.com.foxminded.task20.repository.RoomRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RoomRepositoryTest extends BaseDaoTest {

	@Autowired
	private RoomRepository repository;
	

	@Test
	@Sql({ "/sql/clear_tables.sql", "/sql/room/find_by_id_test.sql" })
	void shouldFindById() {
		Room expected = new Room(11000L, "find me!", "find me!");
		Room actual = repository.findById(expected.getId()).orElseThrow();
		assertEquals(actual, expected);
	}

	@Test
	@Sql({ "/sql/clear_tables.sql" })
	void shouldNotFindById() {
		Optional<Room> result = repository.findById(11000L);
		assertTrue(result.isEmpty());
	}

	@Test
	@Sql("/sql/clear_tables.sql")
	void shouldCreateOne() {
		Room actual = repository.save(new Room("test", "test"));

		assertNotNull(actual.getId());
		assertEquals("test", actual.getName());

		Room fromDB = repository.findById(actual.getId()).orElseThrow();
		assertEquals(actual, fromDB);
	}

	@Test
	@Sql({ "/sql/clear_tables.sql", "/sql/room/update_test.sql" })
	void shouldUpdateOne() {
		String nameBefore = "change me!";
		String nameAfter = "changed!";
		String locationBefore = "change me!";
		String locationAfter = "changed!";

		Room toUpdate = new Room(10000L, nameAfter, locationAfter);
		Room before = repository.findById(toUpdate.getId()).orElseThrow();
		assertEquals(nameBefore, before.getName());
		assertEquals(locationBefore, before.getLocation());

		repository.save(toUpdate);
		Room after = repository.findById(toUpdate.getId()).orElseThrow();
		assertEquals(nameAfter, after.getName());
		assertEquals(locationAfter, after.getLocation());
	}

	@Test
	@Sql({"/sql/clear_tables.sql", "/sql/room/delete_test.sql" })
	void shouldRemoveById() {	
		
		Long exist = 15000L;
		Optional<Room> toRemove = repository.findById(exist);		
		assertTrue(toRemove.isPresent());
		
		repository.deleteById(toRemove.get().getId());

		Optional<Room> afterRemove = repository.findById(exist);	
		assertTrue(afterRemove.isEmpty());	
		
	}
	
}
