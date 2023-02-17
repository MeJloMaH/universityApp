package ua.com.foxminded.task20.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.stereotype.Component;

import ua.com.foxminded.task20.DTO.UserDTO;
import ua.com.foxminded.task20.model.User;
import ua.com.foxminded.task20.service.UserService;

@Component
public class UserValidator {
	
	private Validator validator;
	private UserService userService;	

	public UserValidator(UserService userService, Validator validator) {
		this.userService = userService;
		this.validator = validator;
	}
			
	private List<String> constraints = new ArrayList<>();
		
	public boolean isValid(UserDTO user) {		
		List<String> logins = userService.findAll().stream().map(User::getLogin).collect(Collectors.toList());
		Set<ConstraintViolation<UserDTO>> violations = validator.validate(user);
		
		if(logins.contains(user.getLogin())) {
			constraints.add(String.format("Login = %s is already taken", user.getLogin()));
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
