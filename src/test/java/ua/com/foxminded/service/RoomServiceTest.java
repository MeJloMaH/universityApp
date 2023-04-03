package ua.com.foxminded.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import ua.com.foxminded.service.impl.RoomServiceImpl;
import ua.com.foxminded.model.Room;
import ua.com.foxminded.repository.RoomRepository;

@SpringBootTest(classes = { RoomServiceImpl.class })
public class RoomServiceTest {

	@MockBean
	RoomRepository roomRepository;

	@Autowired
	RoomServiceImpl service;


	@Test
	void shouldFindByName() {
		Room expected = new Room("expected", "location");
		when(roomRepository.findByName(expected.getName())).thenReturn(Optional.of(expected));

		Optional<Room> actual = service.findByName(expected.getName());

		assertTrue(actual.isPresent());
		assertEquals(expected, actual.get());
	}
	
	@Test
	void shouldFindByLocation() {
		Room expected = new Room("expected", "expectedLocation");
		when(roomRepository.findByLocation(expected.getLocation())).thenReturn(Optional.of(expected));

		Optional<Room> actual = service.findByLocation(expected.getLocation());

		assertTrue(actual.isPresent());
		assertEquals(expected, actual.get());
	}


	@Test
	void shouldFindById() {
		Room expected = new Room(1L, "expected", "location");
		when(roomRepository.findById(expected.getId())).thenReturn(Optional.of(expected));

		Optional<Room> actual = service.findById(expected.getId());

		assertTrue(actual.isPresent());
		assertEquals(expected, actual.get());
	}

	@Test
	void shouldFindAll() {
		List<Room> expected = List.of(new Room("expectedFirst", "location"), new Room("expectedSecond", "location"));
		when(roomRepository.findAll()).thenReturn(expected);

		List<Room> students = service.findAll();

		assertFalse(students.isEmpty());
		assertEquals(expected, students);

	}

	@Test
	void shouldDeleteById() {
		service.deleteById(12L);
		verify(roomRepository).deleteById(12L);
	}

	@Test
	void shouldUpdate() {
		Room toUpdate = new Room(1L, "expected", "location");
		when(roomRepository.save(toUpdate)).thenReturn(new Room(1L, "updated", "newLocation"));
		Room updated = service.save(toUpdate);

		assertSame(toUpdate.getId(), updated.getId());
		assertNotSame(toUpdate.getName(), updated.getName());
		assertNotSame(toUpdate.getLocation(), updated.getLocation());

	}

	@Test
	void shouldSave() {
		Room toSave = new Room("expected", "location");
		when(roomRepository.save(toSave)).thenReturn(toSave);

		Room actual = service.save(toSave);

		assertTrue(Optional.of(actual).isPresent());
		assertEquals(toSave, actual);
	}
}
