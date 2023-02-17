package ua.com.foxminded.task20.controller.entities;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ua.com.foxminded.task20.DTO.UserDTO;
import ua.com.foxminded.task20.model.User;
import ua.com.foxminded.task20.security.enums.Role;
import ua.com.foxminded.task20.service.StudentService;
import ua.com.foxminded.task20.service.TeacherService;
import ua.com.foxminded.task20.service.UserService;
import ua.com.foxminded.task20.validation.UserValidator;

@Controller
@RequestMapping("/admin/users")
public class AdminController {

	private UserValidator validator;
    protected final UserService userService;
    protected final TeacherService teacherService;
    protected final StudentService studentService;

    public AdminController(UserService userService, TeacherService teacherService, 
    		StudentService studentService, UserValidator validator) {
		this.userService = userService;
		this.teacherService = teacherService;
		this.studentService = studentService;
		this.validator = validator;
	}

	@GetMapping
    public String showUsers(Model model) {
        model.addAttribute("users", userService.findAll());       
        return "admin/all_users";
    }
	
    
    @GetMapping("/new")
    public String createUser(Model model) {
        model.addAttribute("user", new UserDTO());
        model.addAttribute("roles", List.of(Role.ADMIN.name(), 
        		Role.STUDENT.name(), Role.TEACHER.name(), Role.USER.name()));
        return "admin/edit_user";
    }

    @PostMapping()
    public String saveUser(@ModelAttribute UserDTO user, Model model) {
    	if (validator.isValid(user)) {
    		user.setId(null);
            userService.save(user);
            
            return showUsers(model);
		} else {
			model.addAttribute("errorName", validator.getConstraint());
			return createUser(model);
		}
    }

    @GetMapping("/{id}")
    public String showUser(@PathVariable Long id, Model model) {
        Optional<User> user = userService.findById(id);
        if (user.isEmpty()) {
            model.addAttribute("error", String.format("User with id=%d not found", id));
            return showUsers(model);
        }
        model.addAttribute("user", user.get());
        
        return "admin/one_user";
    }

    @PostMapping("/{id}")
    public String updateUser(@PathVariable Long id, @ModelAttribute UserDTO user, Model model) {
        if (userService.findById(id).isEmpty()) {
            model.addAttribute("error", String.format("User with id=%d not found", id));
        } else {
			if (validator.isValid(user)) {
				user.setId(id);       	
	            userService.update(user);
	            
			} else {
				model.addAttribute("errorName", validator.getConstraint());
				return createUser(model);
			}
		}
        return showUsers(model);
    }

    @GetMapping("/{id}/edit")
    public String editUser(@PathVariable Long id, Model model) {
        Optional<User> user = userService.findById(id);
        if (user.isEmpty()) {
            model.addAttribute("error", String.format("User with id=%d not found", id));
            return showUsers(model);
        }
        
        model.addAttribute("user", user.get());
        return "admin/edit_user";
    }
    
    @PostMapping("/{id}/role")
    public String updateUserRole(@PathVariable Long id, @ModelAttribute UserDTO user, Model model) {
        if (userService.findById(id).isEmpty()) {
            model.addAttribute("error", String.format("User with id=%d not found", id));
        } else {
        	user.setId(id);            
            userService.changeUserType(user);
        }
        return showUsers(model);
    }
    
    @GetMapping("/{id}/edit/role")
    public String editUserRole(@PathVariable Long id, Model model) {
        Optional<User> user = userService.findById(id);
        if (user.isEmpty()) {
            model.addAttribute("error", String.format("User with id=%d not found", id));
            return showUsers(model);
        }       
        model.addAttribute("roles", List.of(Role.ADMIN.name(), 
        		Role.STUDENT.name(), Role.TEACHER.name(), Role.USER.name()));
        model.addAttribute("user", user.get());
        return "admin/edit_user_role";
    }

    @GetMapping("/{id}/delete")
    public String deleteUser(@PathVariable Long id, Model model) {
        Optional<User> user = userService.findById(id);
        if (user.isEmpty()) {
            model.addAttribute("error", String.format("User with id=%d not found", id));
            return showUsers(model);
        } else {
            userService.deleteById(id);
        }
        return showUsers(model);
    }
    
}


