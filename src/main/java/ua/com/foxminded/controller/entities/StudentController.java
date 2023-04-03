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
import ua.com.foxminded.model.Group;
import ua.com.foxminded.model.Student;
import ua.com.foxminded.service.GroupService;
import ua.com.foxminded.service.StudentService;
import ua.com.foxminded.validation.StudentValidator;

@Controller
@RequestMapping("/students")
public class StudentController {

	private StudentValidator validator;
	protected final StudentService studentService;
	protected final GroupService groupService;

	public StudentController(StudentService studentService, GroupService groupService, StudentValidator validator) {
		this.studentService = studentService;
		this.groupService = groupService;
		this.validator = validator;
	}

	@GetMapping
	public String showStudents(Model model) {
		model.addAttribute("students", studentService.findAll());
		return "student/all_students";
	}

	@GetMapping("/new")
	public String createStudent(Model model) {
		model.addAttribute("student", new Student());
		model.addAttribute("groups", groupService.findAll());
		return "student/edit_student";
	}

	@PostMapping()
	public String saveStudent(@ModelAttribute UserDTO student, Model model) {

		if (validator.isValid(student)) {
			student.setId(null);
			studentService.save(student);

			return showStudents(model);
		} else {
			model.addAttribute("errorName", validator.getConstraint());
			return createStudent(model);
		}

	}

	@GetMapping("/{id}")
	public String showStudent(@PathVariable Long id, Model model) {
		Optional<Student> student = studentService.findById(id);
		if (student.isEmpty()) {
			model.addAttribute("error", String.format("Student with id=%d not found", id));
			return showStudents(model);
		}
		model.addAttribute("student", student.get());

		return "student/one_student";
	}

	@PostMapping("/{id}")
	public String updateStudent(@PathVariable Long id, @ModelAttribute UserDTO student, Model model) {
		if (studentService.findById(id).isEmpty()) {
			model.addAttribute("error", String.format("Student with id=%d not found", id));
		} else {
			if (validator.isValid(student)) {
				student.setId(id);
				studentService.update(student);
			} else {
				model.addAttribute("errorName", validator.getConstraint());
				return createStudent(model);
			}

		}
		return showStudents(model);
	}

	@GetMapping("/{id}/edit")
	public String editStudent(@PathVariable Long id, Model model) {
		Optional<Student> student = studentService.findById(id);
		if (student.isEmpty()) {
			model.addAttribute("error", String.format("Student with id=%d not found", id));
			return showStudents(model);
		}

		model.addAttribute("student", student.get());
		model.addAttribute("groups", groupService.findAll());
		return "student/edit_student";
	}

	@GetMapping("/{id}/delete")
	public String deleteStudent(@PathVariable Long id, Model model) {
		Optional<Student> student = studentService.findById(id);
		if (student.isEmpty()) {
			model.addAttribute("error", String.format("Student with id=%d not found", id));
			return showStudents(model);
		} else {
			studentService.deleteById(id);
		}
		return showStudents(model);
	}

	@PostMapping("/group/{id}")
	public String showStudentsByGroup(@ModelAttribute Group group, Model model) {

		model.addAttribute("students", studentService.findByGroupRef(group.getId()));

		return "student/students_by_group";
	}
}
