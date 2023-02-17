package ua.com.foxminded.task20.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.stereotype.Component;

import ua.com.foxminded.task20.model.Group;
import ua.com.foxminded.task20.service.GroupService;

@Component
public class GroupValidator {
	
	private Validator validator;
	private GroupService groupService;	

	public GroupValidator(GroupService groupService, Validator validator) {
		this.groupService = groupService;
		this.validator = validator;
	}
			
	private List<String> constraints = new ArrayList<>();
		
	public boolean isValid(Group group) {		
		List<String> names = groupService.findAll().stream().map(g -> g.getName()).collect(Collectors.toList());
		Set<ConstraintViolation<Group>> violations = validator.validate(group);
		
		if(names.contains(group.getName())) {
			constraints.add(String.format("Group with name = %s already exist", group.getName()));
			return false;  		
    	}
		
		if (!violations.isEmpty()) {
			for (ConstraintViolation<Group> violation : violations) {				
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
