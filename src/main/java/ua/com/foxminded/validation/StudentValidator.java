package ua.com.foxminded.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.stereotype.Component;

import ua.com.foxminded.DTO.UserDTO;
import ua.com.foxminded.service.StudentService;

@Component
public class StudentValidator {
	
	private Validator validator;
	private StudentService studentService;

	public StudentValidator(StudentService studentService, Validator validator) {
		this.studentService = studentService;
		this.validator = validator;
	}
			
	private List<String> constraints = new ArrayList<>();
		
	public boolean isValid(UserDTO student) {
		List<String> logins = studentService.findAll().stream().map(g -> g.getLogin()).collect(Collectors.toList());
		Set<ConstraintViolation<UserDTO>> violations = validator.validate(student);
		
		if(logins.contains(student.getLogin())) {
			constraints.add(String.format("Login = %s is already taken", student.getLogin()));
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
