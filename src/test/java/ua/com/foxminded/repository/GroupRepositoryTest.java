package ua.com.foxminded.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import ua.com.foxminded.BaseDaoTest;
import ua.com.foxminded.model.Group;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class GroupRepositoryTest extends BaseDaoTest {

	@Autowired
    private GroupRepository repository;

	
    @Test
    @Sql({"/sql/clear_tables.sql", "/sql/group/find_by_id_test.sql"})
    void shouldFindById() {
        Group expected = new Group(11000L, "find me!");
        Group actual = repository.findById(expected.getId()).orElseThrow();
        assertEquals(actual, expected);
    }
    
    @Test
    @Sql({"/sql/clear_tables.sql", "/sql/group/find_all_test.sql"})
    void shouldFindAll() {
        List<Group> expected = List.of(
        		new Group(11000L, "find me 1!"),
        		new Group(22000L, "find me 2!"),
        		new Group(33000L, "find me 3!"),
        		new Group(44000L, "find me 4!")
        		);
        List<Group> actual = repository.findAll();
        assertEquals(actual, expected);
    }

    @Test
    @Sql({"/sql/clear_tables.sql"})
    void shouldNotFindById() {
        Optional<Group> result = repository.findById(11000L);
        assertTrue(result.isEmpty());
    }

    @Test
    @Sql("/sql/clear_tables.sql")
    void shouldCreateOne() {
        Group actual = repository.save(new Group("test"));

        assertNotNull(actual.getId());
        assertEquals("test", actual.getName());

        Group fromDB = repository.findById(actual.getId()).orElseThrow();
        assertEquals(actual, fromDB);
    }
 

    @Test
    @Sql({"/sql/clear_tables.sql", "/sql/group/update_test.sql"})
    void shouldUpdateOne() {
        String nameBefore = "change me!";
        String nameAfter = "changed!";

        Group toUpdate = new Group(10000L, nameAfter);
        Group before = repository.findById(toUpdate.getId()).orElseThrow();
        assertEquals(nameBefore, before.getName());

        repository.save(toUpdate);
        Group after = repository.findById(toUpdate.getId()).orElseThrow();
        assertEquals(nameAfter, after.getName());
    }
    @Test
	@Sql({"/sql/clear_tables.sql", "/sql/group/delete_test.sql" })
	void shouldRemoveById() {	
		
		Long exist = 15000L;
		Optional<Group> toRemove = repository.findById(exist);		
		assertTrue(toRemove.isPresent());
		
		repository.deleteById(toRemove.get().getId());

		Optional<Group> afterRemove = repository.findById(exist);	
		assertTrue(afterRemove.isEmpty());			
	}
}