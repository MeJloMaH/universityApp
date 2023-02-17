package ua.com.foxminded.task20.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.stereotype.Component;

import ua.com.foxminded.task20.model.Lesson;
import ua.com.foxminded.task20.service.LessonService;

@Component
public class LessonValidator {
	
	private Validator validator;
	private LessonService lessonService;	

	public LessonValidator(LessonService lessonService, Validator validator) {
		this.lessonService = lessonService;
		this.validator = validator;
	}
	
	private List<String> constraints = new ArrayList<>();
		
	public boolean isValid(Lesson lesson) {		
		
		List<Lesson> unique = lessonService.findAll().stream()
				.filter(e -> 
					e.getDate().equals(lesson.getDate()) && 
					e.getNumberPerDay() == lesson.getNumberPerDay() && 
					e.getGroup().equals(lesson.getGroup())).collect(Collectors.toList());
		
		Set<ConstraintViolation<Lesson>> violations = validator.validate(lesson);
		
		if (!unique.isEmpty()) {
			constraints.add(String.format("This lesson already exist"));
			return false;
		}
		
		if (!violations.isEmpty()) {
			for (ConstraintViolation<Lesson> violation : violations) {				
				constraints.add(violation.getMessage());
			}
			return false;
		}				
		return true;
	}
		
	public String getConstraint() {
		String cnstr = constraints.get(0);
		constraints.clear();
		return cnstr;
	}



}
