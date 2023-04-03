package ua.com.foxminded.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.com.foxminded.model.Group;
import ua.com.foxminded.repository.GroupRepository;
import ua.com.foxminded.service.GroupService;

@Service
public class GroupServiceImpl implements GroupService {

	private final GroupRepository repository;

	Logger log = LoggerFactory.getLogger(GroupServiceImpl.class);

	public GroupServiceImpl(GroupRepository repository) {
		this.repository = repository;
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Group> findById(Long id) {
		log.debug("Trying to find Group by id = {}", id);
		try {
			Optional<Group> entity = repository.findById(id);

			if (entity.isPresent()) {
				log.info("Success, Group by id = {} was found", id);
				return entity;
			}

			return entity;

		} catch (EmptyResultDataAccessException e) {
			log.error("Unable find Group by id = {} due: {}", id, e.getMessage(), e);
			return Optional.empty();
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Group> findByName(String name) {
		log.debug("Trying to find Group by name = {}", name);
		try {
			Optional<Group> entity = repository.findByName(name);

			if (entity.isPresent()) {
				log.info("Success, Group by name = {} was found", name);
				return entity;
			}

			return entity;

		} catch (EmptyResultDataAccessException e) {
			log.error("Unable find Group by name = {} due: {} ", name, e.getMessage(), e);
			return Optional.empty();
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<Group> findAll() {
		log.debug("Trying to find all Groups");
		List<Group> list;
		try {
			list = repository.findAll();

			log.info("Success, all Groups was found");
			return list;

		} catch (Exception e) {
			log.error("Unable find all Groups due: {} ", e.getMessage(), e);
			return new ArrayList<>();
		}

	}

	@Override
	@Transactional
	@PreAuthorize("hasAuthority('delete')")
	public void deleteById(Long id) {
		log.debug("Trying to delete Group by id = {}", id);

		try {
			repository.deleteById(id);
			log.info("Success, Group by id = {} was deleted", id);

		} catch (Exception e) {
			log.error("Unable delete Group by id = {} due: {}", id, e.getMessage(), e);
		}
	}

	@Override
	@Transactional
	@PreAuthorize("hasAuthority('write')")
	public Group save(Group entity) {
		log.debug("Trying to save Group by name = {} ", entity.getName());
		
		try {
			Group saved = repository.save(entity);
			log.info("Success, Group by name = {} was saved", saved.getName());
			return saved;
		} catch (Exception e) {
			log.error("Unable create Group by name = {} due: {} ", entity.getName(), e.getMessage(), e);
			return entity;
		}
	}

}
