package ua.com.foxminded.task20.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.com.foxminded.task20.model.Subject;
import ua.com.foxminded.task20.repository.SubjectRepository;
import ua.com.foxminded.task20.service.SubjectService;

@Service
public class SubjectServiceImpl implements SubjectService {
	
	private final SubjectRepository repository;

	Logger log = LoggerFactory.getLogger(SubjectServiceImpl.class);
	
	public SubjectServiceImpl(SubjectRepository dao) {
		this.repository = dao;
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Subject> findById(Long id) {
		log.debug("Trying to find Subject by id = {}", id);
		try {
			Optional<Subject> entity = repository.findById(id);

			if (entity.isPresent()) {
				log.info("Success, Subject by id = {} was found", id);
				return entity;
			}

			return entity;

		} catch (EmptyResultDataAccessException e) {
			log.error("Unable find Subject by id = {} due: {}", id, e.getMessage(), e);
			return Optional.empty();
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Subject> findByName(String name) {
		log.debug("Trying to find Subject by name = {}", name);
		try {
			Optional<Subject> entity = repository.findByName(name);

			if (entity.isPresent()) {
				log.info("Success, Subject by name = {} was found", name);
				return entity;
			}

			return entity;

		} catch (EmptyResultDataAccessException e) {
			log.error("Unable find Subject by name = {} due: {} ", name, e.getMessage(), e);
			return Optional.empty();
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<Subject> findAll() {
		log.debug("Trying to find all Subjects");
		List<Subject> list;
		try {
			list = repository.findAll();

			log.info("Success, all Subject was found");
			return list;

		} catch (Exception e) {
			log.error("Unable find all Subjects due: {} ", e.getMessage(), e);
			return new ArrayList<>();
		}
	}

	@Override
	@Transactional
	@PreAuthorize("hasAuthority('delete')")
	public void deleteById(Long id) {
		log.debug("Trying to delete Subject by id = {}", id);

		try {
			repository.deleteById(id);
			log.info("Success, Subject by id = {} was deleted", id);

		} catch (Exception e) {
			log.error("Unable delete Subject by id = {} due: {}", id, e.getMessage(), e);
		}
	}

	@Override
	@Transactional
	@PreAuthorize("hasAuthority('write')")
	public Subject save(Subject entity) {
log.debug("Trying to save Subject by name = {} ", entity.getName());
		
		try {
			Subject newEntity = repository.save(entity);
			log.info("Success, Subject by name = {} was saved", newEntity.getName());
			return newEntity;

		} catch (Exception e) {
			log.error("Unable create Subject by name = {} due: {} ", entity.getName(), e.getMessage(), e);
			return entity;
		}
	}



}
