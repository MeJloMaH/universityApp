package ua.com.foxminded.task20.service;

import java.util.List;
import java.util.Optional;

import ua.com.foxminded.task20.model.Subject;

public interface SubjectService {

	Optional<Subject> findById(Long id);

	Optional<Subject> findByName(String name);

	List<Subject> findAll();

	void deleteById(Long id);

	Subject save(Subject entity);

}
