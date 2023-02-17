package ua.com.foxminded.task20.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import ua.com.foxminded.task20.BaseDaoTest;
import ua.com.foxminded.task20.model.Group;
import ua.com.foxminded.task20.model.Lesson;
import ua.com.foxminded.task20.model.Room;
import ua.com.foxminded.task20.model.Subject;
import ua.com.foxminded.task20.model.Teacher;
import ua.com.foxminded.task20.repository.LessonRepository;
import ua.com.foxminded.task20.security.enums.Role;
import ua.com.foxminded.task20.security.enums.Status;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class LessonRepositoryTest extends BaseDaoTest {

	@Autowired
	private LessonRepository repository;


	@Test
	@Sql({ "/sql/clear_tables.sql", "/sql/lesson/find_me_test.sql" })
	void shouldFindById() {
		
		Lesson expected = 
				new Lesson(11000L, "find me!",
						new Group(1L, "fk lesson"), 
						1, 
						LocalDate.of(2000, 1, 1),
						new Teacher(1L, "fk lesson", "login", "password", Role.TEACHER, Status.ACTIVE),
						new Subject		(1L, "fk lesson"),
						new Room		(1L, "fk lesson", "1")		
				);
		
		Lesson actual = repository.findById(expected.getId()).orElseThrow();
		assertEquals(actual, expected);
	}
	
	
	@Test
	@Sql({ "/sql/clear_tables.sql", "/sql/lesson/find_me_test.sql" })
	void shouldFindByTeacherRef() {
		Lesson expected = 
				new Lesson(11000L, "find me!",
						new Group(1L, "fk lesson"), 
						1, 
						LocalDate.of(2000, 1, 1),
						new Teacher		(1L, "fk lesson", "login", "password", Role.TEACHER, Status.ACTIVE),
						new Subject		(1L, "fk lesson"),
						new Room		(1L, "fk lesson", "1")
				);

		
		Lesson actual = 
				repository.findByTeacherRef(expected
						.getTeacher().getId()).get(0);
		
		assertEquals(actual, expected);
	}
	
	@Test
	@Sql({ "/sql/clear_tables.sql", "/sql/lesson/find_me_test.sql" })
	void shouldFindBySubjectRef() {
		Lesson expected = 
				new Lesson(11000L, "find me!",
						
						new Group(1L, "fk lesson"), 
						1, 
						LocalDate.of(2000, 1, 1),
						new Teacher		(1L, "fk lesson", "login", "password", Role.TEACHER, Status.ACTIVE),
						new Subject		(1L, "fk lesson"),
						new Room		(1L, "fk lesson", "1")
				);

		Lesson actual = 
				repository.findBySubjectRef(expected
						.getSubject().getId()).get(0);
		
		assertEquals(actual, expected);
	}
	
	@Test
	@Sql({ "/sql/clear_tables.sql", "/sql/lesson/find_me_test.sql" })
	void shouldFindByRoomRef() {
		Lesson expected = 
				new Lesson(11000L, "find me!",
						
						new Group(1L, "fk lesson"), 
						1, 
						LocalDate.of(2000, 1, 1),
						new Teacher		(1L, "fk lesson", "login", "password", Role.TEACHER, Status.ACTIVE),
						new Subject		(1L, "fk lesson"),
						new Room		(1L, "fk lesson", "1")
				);

		Lesson actual = repository
				.findByRoomRef(expected
						.getRoom().getId()).get(0);
		
		assertEquals(actual, expected);
	}

	@Test
	@Sql({ "/sql/clear_tables.sql", "/sql/lesson/find_all_test.sql" })
	void shouldFindAll() {
		
		Group group = new Group(1L, "fk lesson");
		Teacher teacher = new Teacher(1L, "fk lesson", "login", "password", Role.TEACHER, Status.ACTIVE);
		Subject subject = new Subject(1L, "fk lesson");
		Room room = new Room(1L, "fk lesson", "1");
		
		List<Lesson> expected = List.of(
				new Lesson(11000L, "find all 1!", 
						group, 
						1, 
						LocalDate.of(666, 1, 1),						
						teacher,
						subject,
						room
				),

				new Lesson(22000L, "find all 2!", 											
						group, 
						2, 
						LocalDate.of(777, 1, 1),						
						teacher,
						subject,
						room
				),

				new Lesson(33000L, "find all 3!", 
						group, 
						3, 
						LocalDate.of(888, 1, 1),
						teacher,
						subject,
						room
				),

				new Lesson(44000L, "find all 4!", 
						group, 
						4, 
						LocalDate.of(999, 1, 1),							
						teacher,
						subject,
						room
				));
		
		List<Lesson> actual = repository.findAll();
		assertEquals(actual, expected);
	}

	@Test
	@Sql({ "/sql/clear_tables.sql" })
	void shouldNotFindById() {
		Optional<Lesson> result = repository.findById(11000L);
		assertTrue(result.isEmpty());
	}

	@Test
	@Sql({ "/sql/clear_tables.sql", "/sql/lesson/create_test.sql" })
	void shouldCreateOne() {
		Lesson actual = repository.save(
				new Lesson("test", 
					
					new Group(1L, "fk lesson"), 
					1, 
					LocalDate.of(2000, 1, 1),
					new Teacher		(1L, "fk lesson", "login", "password", Role.TEACHER, Status.ACTIVE),
					new Subject		(1L, "fk lesson"),
					new Room		(1L, "fk lesson", "1"))
				);

		assertNotNull(actual.getId());
		assertEquals("test", actual.getName());

		Lesson fromDB = repository.findById(actual.getId()).orElseThrow();
		assertEquals(actual, fromDB);
	}

	@Test
	@Sql({ "/sql/clear_tables.sql", "/sql/lesson/update_test.sql" })
	void shouldUpdateOne() {
		
		
		String nameBefore = "change me!";
		Group groupBefore = new Group(1L, "fk lesson before");
		int numberPerDayBefore = 1;
		LocalDate dateBefore = LocalDate.of(2000, 1, 1);
		Teacher teacherBefore = new Teacher(1L, "fk lesson before", "login before", 
				"password before", Role.TEACHER, Status.ACTIVE);
		Subject subjectBefore = new Subject(1L, "fk lesson before");
		Room roomBefore = new Room(1L, "fk lesson before", "1");
		
		String nameAfter = "changed!";
		Group groupAfter = new Group(22L, "fk lesson after");
		int numberPerDayAfter = 2;
		LocalDate dateAfter = LocalDate.of(2002, 2, 2);
		Teacher teacherAfter = new Teacher(22L, "fk lesson after", "login after", 
				"password after", Role.TEACHER, Status.ACTIVE);
		Subject subjectAfter = new Subject(22L, "fk lesson after");
		Room roomAfter = new Room(22L, "fk lesson after", "2");
	

		Lesson toUpdate = new Lesson(1000L, nameAfter, groupAfter,
				numberPerDayAfter, dateAfter, teacherAfter, subjectAfter, roomAfter);

		Lesson before = repository.findById(toUpdate.getId()).orElseThrow();
				
		assertEquals(nameBefore, before.getName());
		assertEquals(groupBefore, before.getGroup());
		assertEquals(numberPerDayBefore, before.getNumberPerDay());
		assertEquals(dateBefore, before.getDate());
		assertEquals(teacherBefore, before.getTeacher());
		assertEquals(subjectBefore, before.getSubject());
		assertEquals(roomBefore, before.getRoom());

		repository.save(toUpdate);
		Lesson after = repository.findById(toUpdate.getId()).orElseThrow();
		assertEquals(nameAfter, after.getName());
		assertEquals(groupAfter, after.getGroup());
		assertEquals(numberPerDayAfter, after.getNumberPerDay());
		assertEquals(dateAfter, after.getDate());
		assertEquals(teacherAfter, after.getTeacher());
		assertEquals(subjectAfter, after.getSubject());
		assertEquals(roomAfter, after.getRoom());
	}

	@Test
	@Sql({ "/sql/clear_tables.sql", "/sql/lesson/delete_test.sql" })
	void shouldRemoveById() {

		Long exist = 15000L;
		Optional<Lesson> toRemove = repository.findById(exist);
		
		assertTrue(toRemove.isPresent());
		
		repository.deleteById(exist);

		Optional<Lesson> afterRemove = repository.findById(exist);
		
		assertTrue(afterRemove.isEmpty());
	}
	
	@Test
	@Sql({ "/sql/clear_tables.sql", "/sql/lesson/find_me_test.sql" })
	void shouldFindByDateAndNumberPerDay() {
		Lesson expected = new Lesson(11000L, "find me!", 			
				new Group(1L, "fk lesson"), 
				1, 
				LocalDate.of(2000, 1, 1),
				new Teacher		(1L, "fk lesson", "login", "password", Role.TEACHER, Status.ACTIVE),
				new Subject		(1L, "fk lesson"),
				new Room		(1L, "fk lesson", "1")
				);

		Lesson actual = repository.findByDateAndNumberPerDay(expected.getDate(), expected.getNumberPerDay())
				.get(0);

		assertEquals(actual, expected);
	}
	
	@Test
	@Sql({ "/sql/clear_tables.sql", "/sql/lesson/find_me_test.sql" })
	void shouldFindByDateAndGroupRef() {
		Lesson expected = new Lesson(11000L, "find me!", 			
				new Group(1L, "fk lesson"), 
				1, 
				LocalDate.of(2000, 1, 1),
				new Teacher		(1L, "fk lesson", "login", "password", Role.TEACHER, Status.ACTIVE),
				new Subject		(1L, "fk lesson"),
				new Room		(1L, "fk lesson", "1")
				);

		Lesson actual = 
				repository.findByDateAndGroupRef(expected.getDate(), expected.getGroup().getId()).get(0);

		assertEquals(actual, expected);
	}
	
	@Test
	@Sql({ "/sql/clear_tables.sql", "/sql/lesson/find_me_test.sql" })
	void shouldFindByDate() {
		Lesson expected = new Lesson(11000L, "find me!", 			
				new Group(1L, "fk lesson"), 
				1, 
				LocalDate.of(2000, 1, 1),
				new Teacher		(1L, "fk lesson", "login", "password", Role.TEACHER, Status.ACTIVE),
				new Subject		(1L, "fk lesson"),
				new Room		(1L, "fk lesson", "1")
				);
		
		Lesson actual = 
				repository.findByDate(expected.getDate()).get(0);

		assertEquals(actual, expected);
	}
	
	@Test
	@Sql({ "/sql/clear_tables.sql", "/sql/lesson/find_me_test.sql" })
	void shouldFindByGroupRef() {
		Lesson expected = new Lesson(11000L, "find me!", 			
				new Group(1L, "fk lesson"), 
				1, 
				LocalDate.of(2000, 1, 1),
				new Teacher		(1L, "fk lesson", "login", "password", Role.TEACHER, Status.ACTIVE),
				new Subject		(1L, "fk lesson"),
				new Room		(1L, "fk lesson", "1")
				);
		
		Lesson actual = repository.findByGroupRef(expected.getGroup().getId()).get(0);

		assertEquals(actual, expected);
	}

}
