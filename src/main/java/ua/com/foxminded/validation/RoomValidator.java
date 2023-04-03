package ua.com.foxminded.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.stereotype.Component;

import ua.com.foxminded.model.Room;
import ua.com.foxminded.service.RoomService;

@Component
public class RoomValidator {
	
	private Validator validator;
	private RoomService roomService;

	public RoomValidator(RoomService roomService, Validator validator) {
		this.roomService = roomService;
		this.validator = validator;
	}
			
	private List<String> constraints = new ArrayList<>();
		
	public boolean isValid(Room room) {
		List<String> locationsNames = roomService.findAll().stream().map(g -> g.getLocation()).collect(Collectors.toList());
		Set<ConstraintViolation<Room>> violations = validator.validate(room);
		
		if(locationsNames.contains(room.getLocation())) {
			constraints.add(String.format("Room with location = %s already exist", room.getLocation()));
			return false;  		
    	}
		
		if (!violations.isEmpty()) {
			for (ConstraintViolation<Room> violation : violations) {				
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
