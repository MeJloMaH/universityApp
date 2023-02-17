package ua.com.foxminded.task20.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.stereotype.Component;

import ua.com.foxminded.task20.DTO.UserDTO;
import ua.com.foxminded.task20.service.TeacherService;

@Component
public class TeacherValidator {
	
	private Validator validator;
	private TeacherService teacherService;	

	public TeacherValidator(TeacherService teacherService, Validator validator) {
		this.teacherService = teacherService;
		this.validator = validator;
	}
			
	private List<String> constraints = new ArrayList<>();
		
	public boolean isValid(UserDTO teacher) {		
		List<String> names = teacherService.findAll().stream().map(g -> g.getLogin()).collect(Collectors.toList());
		Set<ConstraintViolation<UserDTO>> violations = validator.validate(teacher);
		
		if(names.contains(teacher.getName())) {
			constraints.add(String.format("Login = %s is already taken", teacher.getLogin()));
			return false;  		
    	}
		
		if (!violations.isEmpty()) {
			for (ConstraintViolation<UserDTO> violation : violations) {				
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
