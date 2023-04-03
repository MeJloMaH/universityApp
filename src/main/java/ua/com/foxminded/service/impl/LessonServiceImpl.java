package ua.com.foxminded.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.com.foxminded.model.Lesson;
import ua.com.foxminded.repository.LessonRepository;
import ua.com.foxminded.service.LessonService;

@Service
public class LessonServiceImpl implements LessonService {

	private final LessonRepository repository;

	Logger log = LoggerFactory.getLogger(LessonServiceImpl.class);

	public LessonServiceImpl(LessonRepository repository) {
		this.repository = repository;
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Lesson> findById(Long id) {
		log.debug("Trying to find Lesson by id = {}", id);
		try {
			Optional<Lesson> lesson = repository.findById(id);

			if (lesson.isPresent()) {
				log.info("Success, Lesson by id = {} was found", id);
				return lesson;
			}
			return lesson;

		} catch (EmptyResultDataAccessException e) {
			log.error("Unable find Lesson by id = {} due: {}", id, e.getMessage(), e);
			return Optional.empty();
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<Lesson> findByName(String name) {
		log.debug("Trying to find Lesson by name = {}", name);
		List<Lesson> lesson;
		try {
			lesson = repository.findByName(name);

			log.info("Success, Lessons by name = {} was found", name);
			return lesson;

		} catch (EmptyResultDataAccessException e) {
			log.error("Unable find Lesson by name = {} due: {} ", name, e.getMessage(), e);
			return new ArrayList<>();
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<Lesson> findAll() {
		log.debug("Trying to find all Lessons");
		List<Lesson> list;
		try {
			list = repository.findAll();

			log.info("Success, all Lessons was found");
			return list;

		} catch (Exception e) {
			log.error("Unable find all Lessons due: {} ", e.getMessage(), e);
			return new ArrayList<>();
		}
	}

	@Override
	@Transactional
	@PreAuthorize("hasAuthority('delete')")
	public void deleteById(Long id) {
		log.debug("Trying to delete Lesson by id = {}", id);

		try {
			repository.deleteById(id);
			log.info("Success, Lesson by id = {} was deleted", id);

		} catch (Exception e) {
			log.error("Unable delete Lesson by id = {} due: {}", id, e.getMessage(), e);
		}
	}

	@Override
	@Transactional
	@PreAuthorize("hasAuthority('write')")
	public Lesson save(Lesson entity) {
		log.debug("Trying to save Lesson by name = {} ", entity.getName());

		try {
			Lesson newEntity = repository.save(entity);
			log.info("Success, Lesson by name = {} was created", entity.getName());
			return newEntity;

		} catch (Exception e) {
			log.error("Unable create Lesson by name = {} due: {} ", entity.getName(), e.getMessage(), e);
			return entity;
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<Lesson> findByGroupRef(long groupRef) {
		log.debug("Trying to find Lessons by groupRef = {}", groupRef);
		List<Lesson> lesson;
		try {
			lesson = repository.findByGroupRef(groupRef);

			log.info("Success, Lessons by groupRef = {} was found", groupRef);
			return lesson;

		} catch (EmptyResultDataAccessException e) {
			log.error("Unable find Lessons by groupRef = {} due: {} ", groupRef, e.getMessage(), e);
			return new ArrayList<>();
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<Lesson> findByDateAndGroupRef(LocalDate date, long groupRef) {
		log.debug("Trying to find Lessons by lessonDate = {} and groupRef = {}", date, groupRef);
		List<Lesson> lesson;
		try {
			lesson = repository.findByDateAndGroupRef(date, groupRef);

			log.info("Success, Lessons by date = {} and groupRef = {} was found", date, groupRef);
			return lesson;

		} catch (EmptyResultDataAccessException e) {
			log.error("Unable find Lessons by date = {} and groupRef = {} due: {} ", date, groupRef,
					e.getMessage(), e);
			return new ArrayList<>();
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<Lesson> findByDateAndNumberPerDay(LocalDate date, Integer numberPerDay) {
		log.debug("Trying to find Lessons by lessonDate = {} and numberPerDay = {}", date, numberPerDay);
		List<Lesson> lesson;
		try {
			lesson = repository.findByDateAndNumberPerDay(date, numberPerDay);

			log.info("Success, Lessons by date = {} and numberPerDay = {} was found", date, numberPerDay);
			return lesson;

		} catch (EmptyResultDataAccessException e) {
			log.error("Unable find Lessons by date = {} and numberPerDay = {} due: {} ", date, numberPerDay,
					e.getMessage(), e);
			return new ArrayList<>();
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<Lesson> findByDate(LocalDate date) {
		log.debug("Trying to find Lessons by date = {}", date);
		List<Lesson> lesson;
		try {
			lesson = repository.findByDate(date);

			log.info("Success, Lessons by date = {} was found", date);
			return lesson;

		} catch (EmptyResultDataAccessException e) {
			log.error("Unable find Lessons by date = {} due: {} ", date, e.getMessage(), e);
			return new ArrayList<>();
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<Lesson> findByTeacherRef(Long id) {
		log.debug("Trying to find Lessons by teacherRef = {}", id);
		List<Lesson> lesson;
		try {
			lesson = repository.findByTeacherRef(id);

			log.info("Success, Lessons by teacherRef = {} was found", id);
			return lesson;

		} catch (EmptyResultDataAccessException e) {
			log.error("Unable find Lessons by teacherRef = {} due: {} ", id, e.getMessage(), e);
			return new ArrayList<>();
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<Lesson> findBySubjectRef(Long id) {
		log.debug("Trying to find Lessons by subjectRef = {}", id);
		List<Lesson> lesson;
		try {
			lesson = repository.findBySubjectRef(id);

			log.info("Success, Lessons by subjectRef = {} was found", id);
			return lesson;

		} catch (EmptyResultDataAccessException e) {
			log.error("Unable find Lessons by subjectRef = {} due: {} ", id, e.getMessage(), e);
			return new ArrayList<>();
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<Lesson> findByRoomRef(Long id) {
		log.debug("Trying to find Lessons by roomRef = {}", id);
		List<Lesson> lesson;
		try {
			lesson = repository.findByRoomRef(id);

			log.info("Success, Lessons by roomRef = {} was found", id);
			return lesson;

		} catch (EmptyResultDataAccessException e) {
			log.error("Unable find Lessons by roomRef = {} due: {} ", id, e.getMessage(), e);
			return new ArrayList<>();
		}
	}

}
