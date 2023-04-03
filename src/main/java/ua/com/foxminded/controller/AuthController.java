package ua.com.foxminded.controller;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ua.com.foxminded.DTO.UserDTO;
import ua.com.foxminded.model.User;
import ua.com.foxminded.service.StudentService;
import ua.com.foxminded.service.UserService;

@Controller
@RequestMapping("/auth")
public class AuthController {

	protected final UserService userService;
	protected final StudentService studentService;

	public AuthController(UserService userService, StudentService studentService) {
		this.userService = userService;
		this.studentService = studentService;
	}

	@GetMapping("/login")
	public String getLoginPage() {
		return "login";
	}

	@GetMapping("/registration")
	public String getRegistrationPage(Model model) {
		model.addAttribute("user", new UserDTO());
		return "registration";
	}

	@PostMapping("/registration")
	public String registration(@ModelAttribute("user") UserDTO user, Model model) {	
		
		Optional<User> exist = userService.findByLogin(user.getLogin());
		
		if(exist.isPresent()) {
			model.addAttribute("userExist", String.format( "User with login %s already exist", user.getLogin() ));
			return "registration";
		}
		
		if(!user.getPassword().equals(user.getConfirmPassword())) {
			model.addAttribute("passwordError", "Passwords don't match");
			return "registration";
		}	
		
		if(user.getIsStudent().equals("on")) {
			studentService.register(user);
			return "redirect:/home";
		}
		userService.register(user);		
		return "redirect:/home";
	}
}
