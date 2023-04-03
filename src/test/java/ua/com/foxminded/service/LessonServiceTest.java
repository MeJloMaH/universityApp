package ua.com.foxminded.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import ua.com.foxminded.security.enums.Role;
import ua.com.foxminded.security.enums.Status;
import ua.com.foxminded.service.impl.LessonServiceImpl;
import ua.com.foxminded.model.Group;
import ua.com.foxminded.model.Lesson;
import ua.com.foxminded.model.Room;
import ua.com.foxminded.model.Subject;
import ua.com.foxminded.model.Teacher;
import ua.com.foxminded.repository.LessonRepository;

@SpringBootTest(classes = { LessonServiceImpl.class })
public class LessonServiceTest {

	@MockBean
	LessonRepository lessonRepository;

	@Autowired
	LessonServiceImpl service;

	@Test
	void shouldFindByName() {
		Lesson expected = 
			new Lesson("expected", 				
				new Group(1L, "fk lesson"), 
				1, 
				LocalDate.of(2000, 1, 1),
				new Teacher("name", "login", "password", Role.TEACHER, Status.ACTIVE),
				new Subject(1L, "fk lesson"),
				new Room(1L, "fk lesson", "1")
			);

		when(lessonRepository.findByName(expected.getName())).thenReturn(List.of(expected));

		List<Lesson> actual = service.findByName(expected.getName());

		assertFalse(actual.isEmpty());
		assertEquals(expected, actual.get(0));
	}

	@Test
	void shouldFindById() {
		Lesson expected = 
				new Lesson("expected", 				
					new Group(1L, "fk lesson"), 
					1, 
					LocalDate.of(2000, 1, 1),
					new Teacher("name", "login", "password", Role.TEACHER, Status.ACTIVE),
					new Subject(1L, "fk lesson"),
					new Room(1L, "fk lesson", "1")
				);
		
		when(lessonRepository.findById(expected.getId())).thenReturn(Optional.of(expected));

		Optional<Lesson> actual = service.findById(expected.getId());

		assertTrue(actual.isPresent());
		assertEquals(expected, actual.get());
	}

	@Test
	void shouldFindByTeacherRef() {
		Lesson expected = 
				new Lesson("expected", 				
					new Group(1L, "fk lesson"), 
					1, 
					LocalDate.of(2000, 1, 1),
					new Teacher("name", "login", "password", Role.TEACHER, Status.ACTIVE),
					new Subject(1L, "fk lesson"),
					new Room(1L, "fk lesson", "1")
				);
		
		when(lessonRepository.findByTeacherRef(expected.getTeacher().getId())).thenReturn(List.of(expected));

		List<Lesson> actual = service.findByTeacherRef(expected.getTeacher().getId());

		assertFalse(actual.isEmpty());
		assertEquals(expected, actual.get(0));
	}

	@Test
	void shouldFindBySubjectRef() {
		Lesson expected = 
				new Lesson("expected", 				
					new Group(1L, "fk lesson"), 
					1, 
					LocalDate.of(2000, 1, 1),
					new Teacher("name", "login", "password", Role.TEACHER, Status.ACTIVE),
					new Subject(1L, "fk lesson"),
					new Room(1L, "fk lesson", "1")
				);
		
		when(lessonRepository.findBySubjectRef(expected.getSubject().getId())).thenReturn(List.of(expected));

		List<Lesson> actual = service.findBySubjectRef(expected.getSubject().getId());

		assertFalse(actual.isEmpty());
		assertEquals(expected, actual.get(0));
	}

	@Test
	void shouldFindByRoomRef() {
		Lesson expected = 
				new Lesson("expected", 				
					new Group(1L, "fk lesson"), 
					1, 
					LocalDate.of(2000, 1, 1),
					new Teacher("name", "login", "password", Role.TEACHER, Status.ACTIVE),
					new Subject(1L, "fk lesson"),
					new Room(1L, "fk lesson", "1")
				);
		
		when(lessonRepository.findByRoomRef(expected.getRoom().getId())).thenReturn(List.of(expected));

		List<Lesson> actual = service.findByRoomRef(expected.getRoom().getId());

		assertFalse(actual.isEmpty());
		assertEquals(expected, actual.get(0));
	}

	@Test
	void shouldFindAll() {
		List<Lesson> expected = List.of(			
						new Lesson("expected", 				
								new Group(1L, "fk lesson"), 
								1, 
								LocalDate.of(2000, 1, 1),
								new Teacher(1L, "name", "login", "password", Role.TEACHER, Status.ACTIVE),
								new Subject(1L, "fk lesson"),
								new Room(1L, "fk lesson", "1")
							),
				
						new Lesson("expected", 				
								new Group(2L, "fk lesson"), 
								1, 
								LocalDate.of(2222, 1, 1),
								new Teacher(2L, "name2", "login2", "password2", Role.TEACHER, Status.ACTIVE),
								new Subject(2L, "fk lesson"),
								new Room(2L, "fk lesson", "2")
							));
		when(lessonRepository.findAll()).thenReturn(expected);

		List<Lesson> students = service.findAll();

		assertFalse(students.isEmpty());
		assertEquals(expected, students);

	}

	@Test
	void shouldDeleteById() {
		service.deleteById(12L);
		verify(lessonRepository).deleteById(12L);
	}

	@Test
	void shouldUpdate() {
		Lesson toUpdate = 
				new Lesson(1L, "expected", 				
					new Group(1L, "fk lesson"), 
					1, 
					LocalDate.of(2000, 1, 1),
					new Teacher(1L, "name", "login", "password", Role.TEACHER, Status.ACTIVE),
					new Subject(1L, "fk lesson"),
					new Room(1L, "fk lesson", "1")
				);
		when(lessonRepository.save(toUpdate)).thenReturn(
				new Lesson(1L, "updated",						
						new Group(20L, "fk lesson 20"), 
						22, 
						LocalDate.of(2222, 1, 1),
						new Teacher(20L, "name20", "login20", "password20", Role.TEACHER, Status.ACTIVE),
						new Subject(20L, "fk lesson 20"),
						new Room(20L, "fk lesson 20", "20")));
				
		Lesson updated = service.save(toUpdate);

		assertSame(toUpdate.getId(), updated.getId());
		
		assertNotSame(toUpdate.getName(), updated.getName());
		assertNotSame(toUpdate.getRoom(), updated.getRoom());
		assertNotSame(toUpdate.getSubject(), updated.getSubject());
		assertNotSame(toUpdate.getTeacher(), updated.getTeacher());
		assertNotSame(toUpdate.getGroup(), updated.getGroup());
		assertNotSame(toUpdate.getDate(), updated.getDate());
		assertNotSame(toUpdate.getNumberPerDay(), updated.getNumberPerDay());

	}

	@Test
	void shouldSave() {
		Lesson toSave = 
			new Lesson("expected",			
				new Group(1L, "fk lesson"), 1, LocalDate.of(2000, 1, 1),
				new Teacher("name", "login", "password", Role.TEACHER, Status.ACTIVE),
				new Subject(1L, "fk lesson"),
				new Room(1L, "fk lesson", "1"));
		when(lessonRepository.save(toSave)).thenReturn(toSave);

		Lesson actual = service.save(toSave);

		assertTrue(Optional.of(actual).isPresent());
		assertEquals(toSave, actual);
	}
	
	@Test
	void shouldFindByGroupRef() {
		Lesson expected = 
				new Lesson("expected", 				
					new Group(1L, "fk lesson"), 
					1, 
					LocalDate.of(2000, 1, 1),
					new Teacher("name", "login", "password", Role.TEACHER, Status.ACTIVE),
					new Subject(1L, "fk lesson"),
					new Room(1L, "fk lesson", "1")
				);
		when(lessonRepository.findByGroupRef(expected.getGroup().getId())).thenReturn(List.of(expected));

		List<Lesson> actual = service.findByGroupRef(expected.getGroup().getId());

		assertTrue(actual.contains(expected));
	}
	
	@Test
	void shouldFindByDateAndGroupRef() {
		Lesson expected = 
				new Lesson("expected", 				
					new Group(1L, "fk lesson"), 
					1, 
					LocalDate.of(2000, 1, 1),
					new Teacher("name", "login", "password", Role.TEACHER, Status.ACTIVE),
					new Subject(1L, "fk lesson"),
					new Room(1L, "fk lesson", "1")
				);
		
		when(lessonRepository.findByDateAndGroupRef(expected.getDate(), expected.getGroup().getId()))
			.thenReturn(List.of(expected));

		List<Lesson> actual = service.findByDateAndGroupRef(expected.getDate(), expected.getGroup().getId());

		assertTrue(actual.contains(expected));
	}
	
	@Test
	void shouldFindByDateAndLessonNumber() {
		Lesson expected = 
				new Lesson("expected", 				
					new Group(1L, "fk lesson"), 
					1, 
					LocalDate.of(2000, 1, 1),
					new Teacher("name", "login", "password", Role.TEACHER, Status.ACTIVE),
					new Subject(1L, "fk lesson"),
					new Room(1L, "fk lesson", "1")
				);
		
		when(lessonRepository.findByDateAndNumberPerDay(expected.getDate(), expected.getNumberPerDay())).thenReturn(List.of(expected));

		List<Lesson> actual = service.findByDateAndNumberPerDay(expected.getDate(), expected.getNumberPerDay());

		assertTrue(actual.contains(expected));
	}

}
