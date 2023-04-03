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

import ua.com.foxminded.model.Room;
import ua.com.foxminded.repository.RoomRepository;
import ua.com.foxminded.service.RoomService;

@Service
public class RoomServiceImpl implements RoomService {

	private final RoomRepository repository;

	Logger log = LoggerFactory.getLogger(RoomServiceImpl.class);

	public RoomServiceImpl(RoomRepository repository) {
		this.repository = repository;
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Room> findById(Long id) {
		log.debug("Trying to find Room by id = {}", id);
		try {
			Optional<Room> entity = repository.findById(id);

			if (entity.isPresent()) {
				log.info("Success, Room by id = {} was found", id);
				return entity;
			}

			return entity;

		} catch (EmptyResultDataAccessException e) {
			log.error("Unable find Room by id = {} due: {}", id, e.getMessage(), e);
			return Optional.empty();
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Room> findByName(String name) {
		log.debug("Trying to find Room by name = {}", name);
		try {
			Optional<Room> entity = repository.findByName(name);

			if (entity.isPresent()) {
				log.info("Success, Room by name = {} was found", name);
				return entity;
			}

			return entity;

		} catch (EmptyResultDataAccessException e) {
			log.error("Unable find Room by name = {} due: {} ", name, e.getMessage(), e);
			return Optional.empty();
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<Room> findAll() {
		log.debug("Trying to find all Rooms");
		List<Room> list;
		try {
			list = repository.findAll();

			log.info("Success, all Rooms was found");
			return list;

		} catch (Exception e) {
			log.error("Unable find all Rooms. due: {} ", e.getMessage(), e);
			return new ArrayList<>();
		}
	}

	@Override
	@Transactional
	@PreAuthorize("hasAuthority('delete')")
	public void deleteById(Long id) {
		log.debug("Trying to delete Room by id = {}", id);

		try {
			repository.deleteById(id);
			log.info("Success, Room by id = {} was deleted", id);

		} catch (Exception e) {
			log.error("Unable delete Room by id = {} due: {}", id, e.getMessage(), e);
		}
	}

	@Override
	@Transactional
	@PreAuthorize("hasAuthority('write')")
	public Room save(Room entity) {
		log.debug("Trying to save Room by name = {} ", entity.getName());

		try {
			Room newEntity = repository.save(entity);
			log.info("Success, Room by name = {} was saved", newEntity.getName());
			return newEntity;

		} catch (Exception e) {
			log.error("Unable create Room by name = {} due: {} ", entity.getName(), e.getMessage(), e);
			return entity;
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Room> findByLocation(String location) {
		log.debug("Trying to find Room by location = {}", location);
		try {
			Optional<Room> entity = repository.findByLocation(location);

			if (entity.isPresent()) {
				log.info("Success, Room by location = {} was found", location);
				return entity;
			}

			return entity;

		} catch (EmptyResultDataAccessException e) {
			log.error("Unable find Room by location = {} due: {} ", location, e.getMessage(), e);
			return Optional.empty();
		}
	}

}
