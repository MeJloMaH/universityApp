package ua.com.foxminded.controller.entities;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ua.com.foxminded.DTO.UserDTO;
import ua.com.foxminded.model.Teacher;
import ua.com.foxminded.service.TeacherService;
import ua.com.foxminded.validation.TeacherValidator;

@Controller
@RequestMapping("/teachers")
public class TeacherController {

	private TeacherValidator validator;
	protected final TeacherService teacherService;

	public TeacherController(TeacherService teacherService, TeacherValidator validator) {
		this.teacherService = teacherService;
		this.validator = validator;
	}

	@GetMapping
	public String showTeachers(Model model) {
		model.addAttribute("teachers", teacherService.findAll());
		return "teacher/all_teachers";

	}

	@GetMapping("/new")
	public String createTeacher(Model model) {
		model.addAttribute("teacher", new Teacher());
		return "teacher/edit_teacher";
	}

	@PostMapping()
	public String saveTeacher(@ModelAttribute UserDTO teacher, Model model) {

		if (validator.isValid(teacher)) {
			teacher.setId(null);
			teacherService.save(teacher);

			return showTeachers(model);
		} else {
			model.addAttribute("errorName", validator.getConstraint());
			return createTeacher(model);
		}
	}

	@GetMapping("/{id}")
	public String showTeacher(@PathVariable Long id, Model model) {
		Optional<Teacher> teacher = teacherService.findById(id);
		if (teacher.isEmpty()) {
			model.addAttribute("error", String.format("Teacher with id=%d not found", id));
			return showTeachers(model);
		}

		model.addAttribute("teacher", teacher.get());
		return "teacher/one_teacher";
	}

	@PostMapping("/{id}")
	public String updateTeacher(@PathVariable Long id, @ModelAttribute UserDTO teacher, Model model) {
		if (teacherService.findById(id).isEmpty()) {
			model.addAttribute("error", String.format("Teacher with id=%d not found", id));
		} else {
			if (validator.isValid(teacher)) {
				teacher.setId(id);
				teacherService.update(teacher);
			} else {
				model.addAttribute("errorName", validator.getConstraint());
				return createTeacher(model);
			}
		}
		return showTeachers(model);
	}

	@GetMapping("/{id}/edit")
	public String editTeacher(@PathVariable Long id, Model model) {
		Optional<Teacher> teacher = teacherService.findById(id);
		if (teacher.isEmpty()) {
			model.addAttribute("error", String.format("Teacher with id=%d not found", id));
			return showTeachers(model);
		}

		model.addAttribute("teacher", teacher.get());
		return "teacher/edit_teacher";
	}

	@GetMapping("/{id}/delete")
	public String deleteTeacher(@PathVariable Long id, Model model) {
		Optional<Teacher> group = teacherService.findById(id);
		if (group.isEmpty()) {
			model.addAttribute("error", String.format("Teacher with id=%d not found", id));
		} else {
			teacherService.deleteById(id);
		}
		return showTeachers(model);
	}
}
