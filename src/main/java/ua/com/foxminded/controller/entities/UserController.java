package ua.com.foxminded.controller.entities;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ua.com.foxminded.service.UserService;

public class UserController {

	protected final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping
    public String myProfile(Model model) {
        model.addAttribute("users", userService.findAll());       
        return "admin/all_users";
    }
}
