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

import ua.com.foxminded.service.impl.SubjectServiceImpl;
import ua.com.foxminded.model.Subject;
import ua.com.foxminded.repository.SubjectRepository;

@SpringBootTest(classes = { SubjectServiceImpl.class })
public class SubjectServiceTest {

	@MockBean
	SubjectRepository subjectRepository;

	@Autowired
	SubjectServiceImpl service;

	@Test
	void shouldFindByName() {
		Subject expected = new Subject("expected");
		when(subjectRepository.findByName(expected.getName())).thenReturn(Optional.of(expected));

		Optional<Subject> actual = service.findByName(expected.getName());

		assertTrue(actual.isPresent());
		assertEquals(expected, actual.get());
	}

	@Test
	void shouldFindById() {
		Subject expected = new Subject(1L, "expected");
		when(subjectRepository.findById(expected.getId())).thenReturn(Optional.of(expected));

		Optional<Subject> actual = service.findById(expected.getId());

		assertTrue(actual.isPresent());
		assertEquals(expected, actual.get());
	}

	@Test
	void shouldFindAll() {
		List<Subject> expected = List.of(new Subject("expectedFirst"), new Subject("expectedSecond"));
		when(subjectRepository.findAll()).thenReturn(expected);

		List<Subject> students = service.findAll();

		assertFalse(students.isEmpty());
		assertEquals(expected, students);

	}

	@Test
	void shouldDeleteById() {
		service.deleteById(12L);
		verify(subjectRepository).deleteById(12L);
	}

	@Test
	void shouldUpdate() {
		Subject toUpdate = new Subject(1L, "expected");
		when(subjectRepository.save(toUpdate)).thenReturn(new Subject(1L, "updated"));
		Subject updated = service.save(toUpdate);

		assertSame(toUpdate.getId(), updated.getId());
		assertNotSame(toUpdate.getName(), updated.getName());

	}

	@Test
	void shouldSave() {
		Subject toSave = new Subject("expected");
		when(subjectRepository.save(toSave)).thenReturn(toSave);

		Subject actual = service.save(toSave);

		assertTrue(Optional.of(actual).isPresent());
		assertEquals(toSave, actual);
	}
}
