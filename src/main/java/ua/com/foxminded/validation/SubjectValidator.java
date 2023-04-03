package ua.com.foxminded.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.stereotype.Component;

import ua.com.foxminded.model.Subject;
import ua.com.foxminded.service.SubjectService;

@Component
public class SubjectValidator {
	
	private Validator validator;
	private SubjectService subjectService;

	public SubjectValidator(SubjectService subjectService, Validator validator) {
		this.subjectService = subjectService;
		this.validator = validator;
	}
			
	private List<String> constraints = new ArrayList<>();
		
	public boolean isValid(Subject subject) {
		List<String> names = subjectService.findAll().stream().map(g -> g.getName()).collect(Collectors.toList());
		Set<ConstraintViolation<Subject>> violations = validator.validate(subject);
		
		if(names.contains(subject.getName())) {
			constraints.add(String.format("Subject with name = %s already exist", subject.getName()));
			return false;  		
    	}
		
		if (!violations.isEmpty()) {
			for (ConstraintViolation<Subject> violation : violations) {				
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
