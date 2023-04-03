package ua.com.foxminded.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
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

import ua.com.foxminded.service.impl.GroupServiceImpl;
import ua.com.foxminded.model.Group;
import ua.com.foxminded.repository.GroupRepository;

@SpringBootTest(classes = { GroupServiceImpl.class })
public class GroupServiceTest {

	@MockBean
	GroupRepository repository;

	@Autowired
	GroupServiceImpl service;

	@Test
	void shouldFindByName() {
		Group expected = new Group("expected");
		when(repository.findByName(expected.getName())).thenReturn(Optional.of(expected));

		Optional<Group> actual = service.findByName(expected.getName());

		assertTrue(actual.isPresent());
		assertEquals(expected, actual.get());
	}
	
	@Test
	void shouldFindById() {
		Group expected = new Group(1L, "expected");
		when(repository.findById(expected.getId())).thenReturn(Optional.of(expected));

		Optional<Group> actual = service.findById(expected.getId());

		assertTrue(actual.isPresent());
		assertEquals(expected, actual.get());
	}

	@Test
	void shouldFindAll() {
		List<Group> expected = List.of(new Group("expectedFirst"), new Group("expectedSecond"));
		when(repository.findAll()).thenReturn(expected);

		List<Group> students = service.findAll();

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
		Group toUpdate = new Group(1L, "to update");
		when(repository.save(toUpdate)).thenReturn(new Group(1L, "updated"));
		Group expected = service.save(toUpdate);

		assertSame(toUpdate.getId(), expected.getId());
		assertNotSame(toUpdate.getName(), expected.getName());

	}	

	@Test
	void shouldSave() {
		Group toSave = new Group("expected");
		when(repository.save(toSave)).thenReturn(toSave);

		Group actual = service.save(toSave);

		assertTrue(Optional.of(actual).isPresent());
		assertEquals(toSave, actual);
	}

}
